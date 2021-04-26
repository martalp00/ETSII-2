package prodconsumidor;

public class Principal 
{
	public static void main(String[] args) 
	{
		Buffer b = new Buffer(5);
		Consumidor c = new Consumidor(b);
		Productor p = new Productor(b);
		
		c.start();
		p.start();

	}
}
