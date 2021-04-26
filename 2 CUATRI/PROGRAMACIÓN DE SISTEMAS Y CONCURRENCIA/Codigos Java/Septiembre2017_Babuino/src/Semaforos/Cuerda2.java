package Semaforos;

import java.util.concurrent.Semaphore;

public class Cuerda2 {
	
	private Semaphore mutex = new Semaphore(1);
	private Semaphore NS = new Semaphore(1);
	private Semaphore SN = new Semaphore(1);
	
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
		NS.acquire();
		mutex.acquire();
		
		n_babuinos++;
		if(n_babuinos == 1) {
			SN.acquire();
		}
		System.out.println("Entra babuino NS, hay " + n_babuinos + " en la cuerda");
		
		mutex.release();
		if(n_babuinos < 3) {
			NS.release();
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
		SN.acquire();
		mutex.acquire();
		n_babuinos++;
		if(n_babuinos == 1) {
			NS.acquire();
		}
		System.out.println("Entra babuino SN, hay " + n_babuinos + " en la cuerda");
		
		mutex.release();
		if(n_babuinos < 3) {
			SN.release();
		}
	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionNS(int id) throws InterruptedException{
		mutex.acquire();
		
		n_babuinos--;
		System.out.println("Sale babuino NS con id:" + id + " y quedan " + n_babuinos);
		if(n_babuinos == 0) {
			NS.release();
			SN.release();
			
		}else {
			NS.release();
		}
		mutex.release();
	}
	
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionSN(int id) throws InterruptedException{
		mutex.acquire();
		
		n_babuinos--;
		System.out.println("Sale babuino SN con id:" + id + " y quedan " + n_babuinos);
		if(n_babuinos == 0) {
			NS.release();
			SN.release();
			
		}else {
			SN.release();
		}
		mutex.release();
	}	
		
}
