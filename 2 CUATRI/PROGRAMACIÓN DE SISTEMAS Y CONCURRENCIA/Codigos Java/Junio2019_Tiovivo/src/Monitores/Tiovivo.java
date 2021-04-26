package Monitores;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tiovivo {
	Lock l = new ReentrantLock();
	private boolean estaLleno = false;
	private Condition CestaLleno = l.newCondition();
	
	private boolean estaLleno2 = false;
	private Condition CestaLleno2 = l.newCondition();	
	
	private boolean aBajar = false;
	private Condition CaBajar = l.newCondition();
	
	private boolean finViaje = false;
	private Condition CfinViaje = l.newCondition();
	
	private int n = 0;
	
	public void subir(int id) throws InterruptedException 
	{	
		l.lock();
		try {
			while(estaLleno) {
				CestaLleno.await();
			}
			
			n++;
			System.out.println("Un pasajero subido con id: " + id);
			if(n == 5) {
				System.out.println("Los 5 pasajeros estan subidos");
				estaLleno = true;
				estaLleno2 = true;
				CestaLleno2.signal();
			}
			
		}finally {
			l.unlock();
		}
	}
	
	public void bajar(int id) throws InterruptedException 
	{
		
		l.lock();
		try {
			while(!aBajar) {
				CaBajar.await();
			}
			n--;
			System.out.println("Un pasajero bajado con id: " + id);
			if(n == 0) {
				System.out.println("Pasajeros bajados y suben los siguientes");
				aBajar = false;
				estaLleno = false;
				CestaLleno.signalAll();
			}
		}finally {
			l.unlock();
		}
	}
	
	public void esperaLleno () throws InterruptedException 
	{
		l.lock();
		try {
			while(!estaLleno2) {
				CestaLleno2.await();
			}
			estaLleno2 = false;
			System.out.println("Tiovivo lleno y se produce el viaje");
			finViaje = true;
			CfinViaje.signal();
		}finally {
			l.unlock();
		}
		
	}
	
	public void finViaje () throws InterruptedException 
	{
		l.lock();
		try {
			while(!finViaje) {
				CfinViaje.await();
			}
			finViaje = false;
			System.out.println("El operario empieza a bajar a los pasajeros");
			aBajar = true;
			CaBajar.signalAll();
		}finally {
			l.unlock();
		}
	}
}
