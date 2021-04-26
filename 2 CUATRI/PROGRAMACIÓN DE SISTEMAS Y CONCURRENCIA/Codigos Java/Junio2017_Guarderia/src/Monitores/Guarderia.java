package Monitores;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Guarderia {
	
	private Lock l = new ReentrantLock();
	private int adultos = 0;
	private int bebes = 0;
	

	private Condition Cesperabebe = l.newCondition();
	
	private Condition Cesperaadulto = l.newCondition();

	
	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void entraBebe(int id) throws InterruptedException{
		l.lock();
		try {
			while(bebes > 3*adultos) {
				Cesperabebe.await();
			}
			bebes++;
			System.out.println("El bebe con id:" + id + " entra a la guarderia");
		}finally {
			l.unlock();
		}
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public void saleBebe(int id) throws InterruptedException{
		l.lock();
		try {
			bebes--;
			Cesperaadulto.signalAll();
			System.out.println("El bebe con id:" + id + " sale de la guarderia y quedan " + bebes + " bebes");	
		}finally {
			l.unlock();
		}
	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public void entraAdulto(int id) throws InterruptedException{
		l.lock();
		try {
			adultos++;
			Cesperabebe.signalAll();
			System.out.println("El adulto con id:" + id + " entra a la guarderia");
		}finally {
			l.unlock();
		}
	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void saleAdulto(int id) throws InterruptedException{
		l.lock();
		try {
			while(bebes > (3*adultos-1)) {
				Cesperaadulto.await();
			}
			adultos--;
			System.out.println("El adulto con id:" + id + " sale de la guarderia y quedan " + adultos + " adultos");
		}finally {
			l.unlock();
		}
		
	}

}
