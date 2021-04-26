package barcoSEM;

import java.util.*;

public class Veleros extends Thread
{
	private int id;
	private Meta meta;
	private static Random r = new Random();
	
	public Veleros(int id,Meta meta)
	{
		this.id = id;
		this.meta = meta;
	}
	
	public void run()
	{
		int i=0;
		while (i<6)
		{
			try 
			{		
				meta.esperaSalida(id);
				Thread.sleep(r.nextInt(1000));
				meta.finalCarrera(id);
				i++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
