package esperaActiva;

public class Productor extends Thread
{
	private VarComp v;
	private int id;
	
	public Productor(VarComp v, int id)
	{
		this.v = v;
		this.id = id;
	}

	public void run()
	{
		for (int i = 0; i<5; i++)
		{	
			if(id == 1)	v.escribir1(i);
			else v.escribir2(i);
		}
		
	}
}