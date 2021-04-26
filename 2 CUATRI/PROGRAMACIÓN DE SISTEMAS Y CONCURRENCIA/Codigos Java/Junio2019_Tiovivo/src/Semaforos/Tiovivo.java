package Semaforos;

import java.util.concurrent.Semaphore;

public class Tiovivo {
	private Semaphore estaLleno = new Semaphore(0);
	private Semaphore mutexPasaj = new Semaphore(1);
	private Semaphore mutexBajad = new Semaphore(0);
	private Semaphore mutex = new Semaphore(1);
	
	private int n = 0;
	
	public void subir(int id) throws InterruptedException 
	{	
	//TODO
		mutexPasaj.acquire();
		mutex.acquire();
		
		n++;
		System.out.println("Un pasajero subido con id: " + id);
		if(n == 5) {
			System.out.println("Los 5 pasajeros estan subidos");
			estaLleno.release();
		}
		if(n < 5) {
			mutexPasaj.release();
		}
		
		mutex.release();
	}
	
	public void bajar(int id) throws InterruptedException 
	{
		mutexBajad.acquire();
		mutex.acquire();
		
		
		n--;
		System.out.println("Un pasajero bajado");
		if(n == 0) {
			System.out.println("Pasajeros bajados y suben los siguientes");
			mutexPasaj.release();
		}
		if(n > 0) {
			mutexBajad.release();
		}
		
		mutex.release();
	}
	
	public void esperaLleno () throws InterruptedException 
	{
		//TODO			
		estaLleno.acquire();
		mutex.acquire();
		System.out.println("Tiovivo lleno y se produce el viaje");
		mutex.release();
		
	}
	
	public void finViaje () throws InterruptedException 
	{
		System.out.println("El operario empieza a bajar a los pasajeros");
		mutexBajad.release();
	}
}
