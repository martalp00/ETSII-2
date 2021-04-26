package prodcons;

public class Consumidor extends Thread
{
	private VarComp v;
	
	public Consumidor(VarComp v)
	{
		this.v = v;
	}

	public void run()
	{
		for (int i = 0; i<10; i++)
		{
			v.leer();
			
		}
	}
}
