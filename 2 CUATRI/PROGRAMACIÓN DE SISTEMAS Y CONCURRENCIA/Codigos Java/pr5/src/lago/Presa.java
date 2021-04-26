package lago;

public class Presa extends Thread
{
	private int id;
	private Lago l;
	
	public Presa(int id,Lago l)
	{
		this.id = id;
		this.l = l;
	}

	public void run()
	{
		for (int i=0; i<1000;i++)
			if (id==0) l.decPresa0();
			else l.decPresa1();
	}
}
