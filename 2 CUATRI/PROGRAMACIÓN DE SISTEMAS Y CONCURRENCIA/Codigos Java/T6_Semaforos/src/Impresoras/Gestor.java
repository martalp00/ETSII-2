package Impresoras;

import java.util.concurrent.Semaphore;

public class Gestor {
	
	private int numLibres;
	private Semaphore mutex = new Semaphore(1); //protege a numLibres
	private Semaphore hayLibres = new Semaphore(1);//CS-cliente
	//hayLibres =0 sii numLibres=0
	
	public Gestor(int N) {
		this.numLibres=N;
	}
	
	public void qimpresora(int id) throws InterruptedException {
		hayLibres.acquire();
		//hayLibres=0;
		mutex.acquire();
		numLibres--;
		System.out.println("Cliente "+id+" coge impresora. Quedan: "+numLibres);
		if(numLibres > 0) {
			hayLibres.release();		
		}
		mutex.release();
	}	
	
	public void dimpresora(int id) throws InterruptedException {
		mutex.acquire();
		numLibres++;
		System.out.println("Cliente "+id+" devuelve impresora. Quedan: "+numLibres);
		if(numLibres==1) {
			hayLibres.release();
		}	
		mutex.release();
	}

}
