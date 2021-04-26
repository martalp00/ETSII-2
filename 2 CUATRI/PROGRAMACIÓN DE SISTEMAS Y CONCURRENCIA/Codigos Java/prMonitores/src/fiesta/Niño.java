package fiesta;

import java.util.Random;

public class Niño extends Thread
{
	private Random r = new Random();
	private Bandeja p;
	private int id;
	
	public Niño(Bandeja p,int id)
	{
		this.p = p;
		this.id = id;
	}
	
	public void run()
	{
		while (true)
		{
			try
			{	
				p.quieroRacion(id);
				Thread.sleep(r.nextInt(200));
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
