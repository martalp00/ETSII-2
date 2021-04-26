package Monitores;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cuerda2 {
	
	Lock l = new ReentrantLock();
	
	private boolean NS = true;
	private Condition CNS = l.newCondition();
	
	private boolean SN = true;
	private Condition CSN = l.newCondition();
	
	private int n_babuinos = 0;
	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionNS(int id) throws InterruptedException{
		l.lock();
		try {
			while(!NS) {
				CNS.await();
			}
			SN = false;
			
			n_babuinos++;
			
			
			System.out.println("Entra babuino NS, hay " + n_babuinos + " en la cuerda");

			if(n_babuinos == 3) {
				NS = false;
			}
			
			
		}finally {
			l.unlock();
		}
	}
	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón  colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionSN(int id) throws InterruptedException{
		l.lock();
		try {
			while(!SN) {
				CSN.await();
			}
			NS = false;
			
			
			n_babuinos++;
		
			
			System.out.println("Entra babuino SN, hay " + n_babuinos + " en la cuerda");

			if(n_babuinos == 3) {
				SN = false;
			}
			
		}finally {
			l.unlock();
		}
	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionNS(int id) throws InterruptedException{
		l.lock();
		try {
			n_babuinos--;
			System.out.println("Sale babuino NS con id:" + id + " y quedan " + n_babuinos);
			if(n_babuinos == 0) {
				NS = true;
				CNS.signalAll();
				SN = true;
				CSN.signalAll();
			} else {
				NS = true;
				CNS.signalAll();
			}
		}finally {
			l.unlock();
		}
	}
	
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionSN(int id) throws InterruptedException{
		l.lock();
		try {
			
			n_babuinos--;
			System.out.println("Sale babuino SN con id:" + id + " y quedan " + n_babuinos);
			if(n_babuinos == 0) {
				SN = true;
				CSN.signalAll();
				NS = true;
				CNS.signalAll();
			}else {
				SN = true;
				CSN.signalAll();
			}
		}finally {
			l.unlock();
		}
	}	
		
}
