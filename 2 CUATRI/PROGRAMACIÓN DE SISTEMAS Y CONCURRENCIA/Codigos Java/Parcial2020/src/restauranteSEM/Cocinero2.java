package restauranteSEM;

import java.util.Random;

public class Cocinero2 extends Thread
{
	private Random r = new Random();
	private Restaurante b;
	public Cocinero2(Restaurante b)
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
				b.esperaEcharArroz();
				Thread.sleep(r.nextInt(200)); //Juan está poniendo una semilla
				b.finEcharArroz(i);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
}