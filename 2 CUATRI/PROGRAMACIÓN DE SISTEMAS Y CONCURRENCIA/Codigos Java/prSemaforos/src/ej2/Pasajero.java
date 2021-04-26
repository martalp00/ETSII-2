package ej2;
import java.util.*;

public class Pasajero extends Thread
{
	private static Random r = new Random();
	private Coche coche;
	private int id;
	
	public Pasajero(Coche coche,int id)
	{
		this.coche = coche;
		this.id = id;
	}
	
	public void run()
	{
		while (true)
		{
			try 
			{
				Thread.sleep(r.nextInt(500));
				coche.nuevoViaje(id);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
