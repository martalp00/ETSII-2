package Monitores;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Parada {
	
	private Lock l = new ReentrantLock();
	private int cont1 = 0;
	private int cont2 = 0;
	
	private boolean entrando = false;
	private Condition Centrando = l.newCondition();
	
	private boolean llegagente = true;
	private Condition Cllegagente = l.newCondition();
	
	private boolean hallegadoBus = false;
	
	public Parada(){
		
	}
	/**
	 * El pasajero id llama a este metodo cuando llega a la parada.
	 * Siempre tiene que esperar el siguiente autobus en uno de los
	 * dos grupos de personas que hay en la parada
	 * El metodo devuelve el grupo en el que esta esperando el pasajero
	 * 
	 */
	public int esperoBus(int id) throws InterruptedException{
		l.lock();
		try {
			if(hallegadoBus) {
				cont2++;
				System.out.println("id:" + id + " llega a la parada secundaria y hay " + cont2 + " persona/s");
				return 2;
			} else{
				cont1++;
				System.out.println("id:" + id + " llega a la parada y hay " + cont1 + " persona/s");
				return 1;
			}
			
		}finally {
			l.unlock();
		}
	}
	/**
	 * Una vez que ha llegado el autobús, el pasajero id que estaba
	 * esperando en el grupo i se sube al autobus
	 * @throws InterruptedException 
	 *
	 */
	public void subeAutobus(int id,int i) throws InterruptedException{
		l.lock();
		try {
			while(!entrando) {
				Centrando.await();
			}
			cont1--;
			System.out.println("id:" + id + " se sube al autobus y quedan " + cont1 + " en la parada");
			if(cont1 == 0) {
				System.out.println("Se han subido todos");
				cont1 = cont2;
				cont2 = 0;
				hallegadoBus = false;
				entrando = false;
				
				
			}
			
		}finally {
			l.unlock();
		}
	}
	/**
	 * El autobus llama a este metodo cuando llega a la parada
	 * Espera a que se suban todos los viajeros que han llegado antes
	 * que el, y se va
	 * 
	 */
	public void llegoParada() throws InterruptedException{
		l.lock();
		try {
			System.out.println("Llega el autobus");
			hallegadoBus = true;
			entrando = true;
			Centrando.signalAll();
			
		}finally {
			l.unlock();
		}
		
		
	}
}
