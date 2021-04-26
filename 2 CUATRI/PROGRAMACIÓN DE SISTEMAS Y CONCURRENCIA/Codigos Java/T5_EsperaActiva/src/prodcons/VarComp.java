package prodcons;

public class VarComp 
{
	private int var;
	private volatile boolean hayDato = false; //hay un nuevo dato en VarComp
	
	public void escribir(int nvar)								//utilizado por productor
	{
		
		while (hayDato)
		{
			Thread.yield();										//espera activa. Preprotocolo
		};
		
		var = nvar;
		System.out.println("Productor ha producido "+var);
		hayDato = true; 										// Postprotocolo
		
	}

	public int leer()											//utilizado por consumidor
	{
		while (!hayDato)
		{
			Thread.yield();										//espera activa. Preprotocolo
		}; 
		
		int lvar = var;
		System.out.println("					Consumidor ha leido "+lvar);
		hayDato = false; 										//Postprotocolo
		return lvar;
	}
}

//CS-Prod: No puedo escribir un dato hasta que no se ha leído el anterior
//CS-Cons: No puedo leer un dato hasta que no se ha almacenado uno nuevo
//prod(0)->cons(0)->prod(1)->cons(0)->....