package prodcons;

public class Productor extends Thread
{
	private VarComp v;
	
	public Productor(VarComp v)
	{
		this.v = v;
	}

	public void run()
	{
		for (int i = 0; i<10; i++)
		{		
			v.escribir(i);
		}
	}
}