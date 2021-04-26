package lago;

public class Rio extends Thread
{
	private int id;
	private Lago l;
	
	public Rio(int id,Lago l)
	{
		this.id = id;
		this.l = l;
	}

	public void run()
	{
		for (int i=0; i<1000;i++)
		{
			l.incRio();
		}
	}
}
