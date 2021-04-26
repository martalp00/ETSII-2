package FilosofosTenedores;

import java.util.concurrent.Semaphore;

public class Sillas {

	private int sillasLibres = 4; 
	private Semaphore mutex = new Semaphore(1); //exclusión mutua
	private Semaphore hayLibres = new Semaphore(1); //condición de sincronización
	
	//hayLibres == 1 sii sillasLibres > 0
	//hayLibres == 0 sii sillasLibres == 0
	public void cojoSilla(int id) throws InterruptedException{
		hayLibres.acquire();
		mutex.acquire();
		sillasLibres--;
		System.out.println("El Filosofo " + id + " coge una silla. Quedan " + sillasLibres + " sillas libres");
		if (sillasLibres > 0) hayLibres.release();
		mutex.release();
	}
	
	public void devuelvoSilla(int id) throws InterruptedException{
		mutex.acquire();
		sillasLibres++;
		System.out.println("El Filosofo " + id + " suelta una silla. Quedan " + sillasLibres + " sillas libres");
		if (sillasLibres == 1) hayLibres.release();
		mutex.release();
	}
}
