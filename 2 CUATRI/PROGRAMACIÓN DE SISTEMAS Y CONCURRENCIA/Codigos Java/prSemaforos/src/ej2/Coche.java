package ej2;

import java.util.concurrent.Semaphore;

public class Coche extends Thread
{
	private int C;
	private int pasajeros = 0;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore viaje = new Semaphore(1);
	private Semaphore lleno = new Semaphore(0);
	
	public Coche(int cap)
	{
		C = cap;
	}
	
	public void nuevoViaje(int id) throws InterruptedException
	{
		viaje.acquire();
		mutex.acquire();
		pasajeros++;
		
		System.out.println("Sube el pasajero " + id + ", hay " + pasajeros);
		
		if(pasajeros == 5) 
		{
			lleno.release();
		}
		if(pasajeros < 5) 
		{
			viaje.release();
		}
		mutex.release();
	}
	
	public void esperaLleno() throws InterruptedException
	{
		lleno.acquire();
		mutex.acquire();
		
		System.out.println("COCHE LLENO");
		
		mutex.release();
	}
	
	public void finViaje()
	{
		pasajeros -= C;
		System.out.println("Los pasajeros se bajan del coche");
		viaje.release();	
	}

	public void run()
	{
		while (true)
		{
			try
			{
				this.esperaLleno();
				Thread.sleep(200);
				this.finViaje();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
