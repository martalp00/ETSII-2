package huertoSEM;

import java.util.concurrent.*;
public class Huerto { //Recurso

	private int distancia;
	private int numHoyos=0;
	private int numSemillas=0;
	
	private Semaphore mutex = new Semaphore(1);
	private Semaphore tierra = new Semaphore(0);
	private Semaphore semillas = new Semaphore(0);
	private Semaphore hoyos = new Semaphore(1);
	private Semaphore hayPala = new Semaphore(1);
	
	public Huerto(int tam)
	{		
		distancia = tam;
	}
	
	public void esperaHacerHoyo() throws InterruptedException 
	{
		hoyos.acquire();
		hayPala.acquire();
	}
	
	public void finHacerHoyo(int num) throws InterruptedException
	{
		mutex.acquire();
		System.out.println("1.David ha cavado el hoyo "+num);
		distancia--;
		numHoyos++;
		
		if (distancia>0) hoyos.release();
		if (numHoyos==1) semillas.release();
		
		hayPala.release();
		mutex.release();
		
	}
	
	public void esperaPonerSemilla() throws InterruptedException
	{
		semillas.acquire();
	}
	
	public void finPonerSemilla(int num) throws InterruptedException
	{
		mutex.acquire();
		System.out.println("2.Juan ha puesto una semilla en el hoyo "+num);
		numSemillas++;
		numHoyos--;
		
		if (numSemillas==1) tierra.release();	
		if (numHoyos>0) semillas.release();
		
		mutex.release();
		
	}
	
	public void esperaEcharTierra() throws InterruptedException
	{
		tierra.acquire();
		hayPala.acquire();
	}
	
	public void finEcharTierra(int num) throws InterruptedException
	{	
		mutex.acquire();	
		System.out.println("3.Fran ha tapado el hoyo "+num);
		distancia++;
		numSemillas--;
		
		if (distancia==1) hoyos.release();	
		if (numSemillas>0) tierra.release();
		
		hayPala.release();
		mutex.release();	
	}
	

}
