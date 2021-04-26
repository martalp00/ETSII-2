package esperaActiva;

public class Principal 
{
	public static void main(String[] args) 
	{
		VarComp v = new VarComp();
		Productor p1 = new Productor(v,1);
		Productor p2 = new Productor(v,2);
		Consumidor c = new Consumidor(v);
		
		p1.start();
		p2.start();
		c.start();
		try 
		{
			p1.join();
			p2.join();
			c.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
}
