package huertoSEM;

import java.util.Random;

public class Juan  extends Thread
{
	private Random r = new Random();
	private Huerto b;
	
	public Juan(Huerto b)
	{
		this.b = b;
	}
	
	public void run()
	{
		for (int i=0 ; i<25; i++)
		{
			try 
			{
				Thread.sleep(r.nextInt(10));
				b.esperaPonerSemilla();
				Thread.sleep(r.nextInt(200)); //Juan está poniendo una semilla
				b.finPonerSemilla(i);	
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
