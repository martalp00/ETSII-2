package Semaforos;

import java.util.concurrent.Semaphore;

public class Aseo2 {
	private int mujeres = 0;
	private int hombres = 0;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore Mujer = new Semaphore(1);
	private Semaphore Hombre = new Semaphore(1);
	
	/**
	 * El hombre id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay alguna mujer en ese
	 * momento en el aseo
	 */
	public void llegaHombre(int id) throws InterruptedException{	
			Hombre.acquire();
			mutex.acquire();
			hombres++;
			System.out.println("Llega hombre con id:" + id + " y hay " + hombres);
			
			Hombre.release();
			mutex.release();
		
		
	}
	/**
	 * La mujer id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay algun hombre en ese
	 * momento en el aseo
	 */
	public void llegaMujer(int id) throws InterruptedException{
			Mujer.acquire();
			mutex.acquire();
			mujeres++;
			System.out.println("Llega mujer con id:" + id + " y hay " + mujeres);
			
			Mujer.release();
			mutex.release();
		
		
	}
	/**
	 * El hombre id, que estaba en el aseo, sale
	 */
	public void saleHombre(int id)throws InterruptedException{
		Hombre.acquire();
		mutex.acquire();
		
		hombres--;
		System.out.println("Sale hombre con id:" + id + " y quedan " + hombres);
		if(hombres == 0) {
			Hombre.release();
			Mujer.release();
			
		}else {
			Hombre.release();
		}
		mutex.release();
		
	}
	
	public void saleMujer(int id)throws InterruptedException{
		Mujer.acquire();
		mutex.acquire();
		
		mujeres--;
		System.out.println("Sale mujer con id:" + id + " y quedan " + mujeres);
		if(mujeres == 0) {
			Mujer.release();
			Hombre.release();
			
		}else {
			Mujer.release();
		}
		mutex.release();
	}
}
