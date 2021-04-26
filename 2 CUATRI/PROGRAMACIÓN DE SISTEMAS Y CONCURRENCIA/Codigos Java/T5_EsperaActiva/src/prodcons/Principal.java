package prodcons;

public class Principal 
{
	public static void main(String[] args) 
	{
		VarComp v = new VarComp();
		Productor p = new Productor(v);
		Consumidor c = new Consumidor(v);
		p.start();
		c.start();
	}
}
