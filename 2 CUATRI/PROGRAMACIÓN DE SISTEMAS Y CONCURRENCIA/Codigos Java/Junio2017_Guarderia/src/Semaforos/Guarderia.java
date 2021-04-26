package Semaforos;

import java.util.concurrent.Semaphore;

public class Guarderia {
	
	private Semaphore mutex = new Semaphore (1);
	private int adultos = 0;
	private int bebes = 0;
	private Semaphore entrabebe = new Semaphore(0);
	private Semaphore saleadulto = new Semaphore(0);
	
	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void entraBebe(int id) throws InterruptedException{
		
		mutex.acquire();
		while(!(bebes+1 <= 3*adultos)) {
			mutex.release();
			entrabebe.acquire();
			mutex.acquire();
		}
		
		bebes++;
		if(bebes <= 3*(adultos-1)) {
			saleadulto.release();
		}
		System.out.println("El bebe con id:" + id + " entra a la guarderia");
		mutex.release();
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public void saleBebe(int id) throws InterruptedException{
		mutex.acquire();
		bebes--;
		System.out.println("El bebe con id:" + id + " sale de la guarderia y quedan " + bebes + " bebes");
		
		mutex.release();	
	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public void entraAdulto(int id) throws InterruptedException{
		mutex.acquire();
		
		adultos++;
		if(bebes+1 <= 3*adultos) {
			entrabebe.release();
		}
		System.out.println("El adulto con id:" + id + " entra a la guarderia");
		
		mutex.release();
		
	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void saleAdulto(int id) throws InterruptedException{
		mutex.acquire();
		while(!(bebes <= 3*(adultos-1))) {
			mutex.release();
			saleadulto.acquire();
			mutex.acquire();
		}
		adultos--;
		System.out.println("El adulto con id:" + id + " sale de la guarderia y quedan " + adultos + " adultos");
		mutex.release();
		
	}

}
