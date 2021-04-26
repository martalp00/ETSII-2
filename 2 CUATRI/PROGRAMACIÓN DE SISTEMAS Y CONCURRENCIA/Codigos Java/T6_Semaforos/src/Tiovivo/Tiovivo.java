package Tiovivo;

import java.util.concurrent.Semaphore;

public class Tiovivo {
		
	private int numSitios;
	private int pasajeros=0;
	
	private Semaphore mutex = new Semaphore(1);
	private Semaphore subir = new Semaphore(1);
	private Semaphore bajar = new Semaphore(0);
	private Semaphore arrancar = new Semaphore(0);
	
	public Tiovivo(int i) {
		this.numSitios=i;
	}

	public void subir(int id) throws InterruptedException{
		subir.acquire();
		mutex.acquire();
		
		pasajeros++;
		
		System.out.println("Pasajero " + id + "ha subido. Numero de pasajeros en el tiovivo " + pasajeros);
		if (pasajeros < numSitios) {//Si no es el ultimo pasajero que entra
			subir .release();
		}else {
			arrancar.release();
		}
		mutex.release();	
	}
	
	public void bajar(int id) throws InterruptedException{
		bajar.acquire();
		mutex.acquire();
		
		System.out.println("Pasajero " + id + "ha bajado. Numero de pasajeros en el tiovivo " + pasajeros);
		pasajeros--;	
		
		if(pasajeros==0) { //Si es el ultimo pasajero en bajar
			subir.release();//Avisa a los que estaban esperando en la cola
			System.out.println("Ya se puede subir");
		}else {
			bajar.release();//Sigo dejando bajar al resto que quedan en el tiovivo
		}
		mutex.release();		
	}
	
	public void esperaLleno () throws InterruptedException{
		arrancar.acquire();	
		System.out.println("Arrancando el tiovivo");
	}
	
	public void finViaje (){
		System.out.println("Fin del viaje chicos");
		bajar.release();//Se les dice a los que estaban montados que ya se pueden bajar
	}
}
