package autobusLocks;

import java.util.concurrent.locks.*;
public class Parada {
	private Lock l = new ReentrantLock(true);
	private Condition esperaCS = l.newCondition();
	private Condition esperaCP = l.newCondition();
	private Condition clleno = l.newCondition();
	private Condition cciclo = l.newCondition();
	private boolean hayBus = false;
	private int numS = 0;
	private int numP = 0;
	private boolean lleno = false;
	public Parada(){

	}
	/**
	 * El pasajero id llama a este metodo cuando llega a la parada.
	 * Siempre tiene que esperar el siguiente autobus en uno de los
	 * dos grupos de personas que hay en la parada
	 * El metodo devuelve el grupo en el que esta esperando el pasajero
	 * 
	 */
	public  int esperoBus(int id) throws InterruptedException{
		l.lock();
		try{
			if (hayBus){
				System.out.println("Hay bus. Pasajero "+id+ " espera en la cola secundaria");
				numS++;
				while (hayBus) esperaCS.await();
				numS--;
				if (numS==0) cciclo.signal();
			}
			System.out.println("No hay bus. Pasajero "+id+ " espera en la cola principal");
			numP++;
			while (!hayBus) esperaCP.await();
			return 0;
			//comentar esta línea
		}finally{
			l.unlock();
		}
	}
	/**
	 * Una vez que ha llegado el autobús, el pasajero id que estaba
	 * esperando en el grupo i se sube al autobus
	 *
	 */
	public synchronized void subeAutobus(int id,int i){
		l.lock();
		try{
			numP--;
			System.out.println("Pasajero "+id+ " se sube al autobús "+numP);
			if (numP==0){
				lleno = true;	
				clleno.signal();
			}
		}finally{
			l.unlock();
		}
	}
	/**
	 * El autobus llama a este metodo cuando llega a la parada
	 * Espera a que se suban todos los viajeros que han llegado antes
	 * que el, y se va
	 * 
	 */
	public  void llegoParada() throws InterruptedException{
		l.lock();
		try{
			System.out.println("LLEGA AUTOBUS");
			hayBus = true;
			esperaCP.signalAll();
			if (numP!=0) while (!lleno) clleno.await();
			lleno = false;
			System.out.println("SALE AUTOBUS");
			hayBus = false;
			esperaCS.signalAll();
			while (numS!=0) cciclo.await();
		}finally{
			l.unlock();
		}
	}
}
