package prodconsumidor;

import java.util.*;

public class Consumidor extends Thread
{
	private Random r = new Random();
	private Buffer b;
	
	public Consumidor(Buffer b)
	{
		this.b = b;
	}
	
	public void run()
	{
		for (int i=0 ; i<25; i++)
		{
			b.extraer();
			try 
			{
				Thread.sleep(r.nextInt(100));
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
