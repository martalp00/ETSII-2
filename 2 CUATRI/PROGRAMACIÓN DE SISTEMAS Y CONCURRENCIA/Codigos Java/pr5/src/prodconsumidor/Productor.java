package prodconsumidor;

import java.util.*;

public class Productor extends Thread
{
	private Random r = new Random();
	private Buffer b;
	
	public Productor(Buffer b)
	{
		this.b = b;
	}
	
	public void run()
	{
		for (int i=0 ; i<25; i++)
		{
			try 
			{
				Thread.sleep(r.nextInt(100));
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			b.almacenar(i);
		}
	}

}
