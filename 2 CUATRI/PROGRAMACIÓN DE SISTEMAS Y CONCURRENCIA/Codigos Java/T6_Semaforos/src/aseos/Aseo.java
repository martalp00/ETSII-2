package aseos;

import java.util.concurrent.Semaphore;

public class Aseo {

	private Semaphore mutex = new Semaphore(1);
	private Semaphore hombre = new Semaphore(1);
	private Semaphore mujer = new Semaphore(1);
	private int numH =0;
	private int numM = 0;
	private boolean esperaM = false;
	private boolean esperaH = false;
			
	/**
	 * El hombre id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay alguna mujer en ese
	 * momento en el aseo
	 */
	public void llegaHombre(int id) throws InterruptedException{
		hombre.acquire();
		mutex.acquire();
		
		while(numM>0) {
			esperaM = true;
			mutex.release();
			hombre.acquire();
			mutex.acquire();			
		}
		
		numH++;
		System.out.println("Entra Hombre "+id+". Hay "+numH);
		
		hombre.release();
		mutex.release();
	}
	/**
	 * La mujer id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay algun hombre en ese
	 * momento en el aseo
	 */
	public void llegaMujer(int id) throws InterruptedException{
		mujer.acquire();
		mutex.acquire();
		
		while(numH>0) {
			esperaH = true;
			mutex.release();
			mujer.acquire();
			mutex.acquire();			
		}
		
		numM++;
		System.out.println("Entra Mujer "+id+". Hay "+numM);
		
		mujer.release();
		mutex.release();
	}
	/**
	 * El hombre id, que estaba en el aseo, sale
	 */
	public void saleHombre(int id)throws InterruptedException{
		hombre.acquire();
		mutex.acquire();
		
		numH--;
		System.out.println("Sale Hombre " + id + ". Hay " + numH);
		
		if(esperaH) {
			esperaH = false;
			mujer.release();
		}
		
		hombre.release();
		mutex.release();
	}
	
	public void saleMujer(int id)throws InterruptedException{
		mujer.acquire();
		mutex.acquire();
		
		numM--;
		System.out.println("Sale Mujer " + id + ". Hay " + numM);
		
		if(esperaM) {
			esperaM = false;
			hombre.release();
		}
		
		mujer.release();
		mutex.release();
	}
}
