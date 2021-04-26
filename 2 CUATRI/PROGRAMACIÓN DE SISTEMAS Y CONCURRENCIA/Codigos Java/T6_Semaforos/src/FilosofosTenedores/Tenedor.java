package FilosofosTenedores;

import java.util.concurrent.Semaphore;

public class Tenedor {
	
	private int id;
	private Semaphore estaLibre = new Semaphore(1); //mutex
	
	public Tenedor(int id){
		this.id = id;
	}
	
	public void cojoTenedor(int id) throws InterruptedException{
		//Tenemos que coger el tenedor en exclusión mutua
		estaLibre.acquire();
		System.out.println("El Filosofo " + id + " coge el tenedor " + this.id);
	}
	
	public void devuelvoTenedor(int id){
		estaLibre.release();
		System.out.println("El Filosofo " + id + " coge el tenedor " + this.id);
	}
}
