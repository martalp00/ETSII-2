package fiesta;

import java.util.*;

public class Pastelero extends Thread
{
	private Random r = new Random();
	private Bandeja p;
	
	public Pastelero(Bandeja p)
	{
		this.p = p;
	}
	
	public void run()
	{
		while (true)
		{
			try 
			{
				Thread.sleep(r.nextInt(1000));
				p.tarta();
				
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
