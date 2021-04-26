package lago;

public class Principal 
{
	public static void main(String[] args) 
	{
		Lago l = new Lago();
		Presa p0 = new Presa(0,l);
		Presa p1 = new Presa(1,l);
		Rio r0 = new Rio(0,l);
		
		p0.start();
		r0.start();
		
		try 
		{
			p0.join();
			r0.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		System.out.println("El nivel es "+l.nivel());
	}
}
