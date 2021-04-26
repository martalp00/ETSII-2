package limpiezaMON;

public class Principal
{
	public static void main(String[] args) 
	{
		Vestuario b = new Vestuario(5);
		Antonio antonio = new Antonio(b);
		Rosa rosa = new Rosa(b);
		David david = new David(b);
		
		antonio.start();
		rosa.start();
		david.start();
	}
}
