package huertoSEM;


public class Principal 
{

	public static void main(String[] args) 
	{
		Huerto b	= 	new Huerto(5);
		David c 		=	new David(b);
		Juan s 	=	new Juan(b);
		Fran t 		= 	new Fran(b);
		
		c.start();
		s.start();
		t.start();
	}
}