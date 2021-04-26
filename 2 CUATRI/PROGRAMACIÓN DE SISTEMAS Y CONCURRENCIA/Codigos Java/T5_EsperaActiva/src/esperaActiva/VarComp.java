package esperaActiva;

// No sé conseguir que del paso 2 vuelva al 1 y el poder ejecutar las restricciones

public class VarComp 
{
	private volatile int var;
	private volatile int buf=7;
	
	private volatile boolean esc = false, lee = false;	//hay un nuevo dato en VarComp
	private volatile int turno=0;	//0-lee, 1-escribe
	
	private volatile boolean esc1 = false, esc2 = false;	//hay un nuevo dato en VarComp
	private volatile int turno2=0, tam=0;
	
	public void escribir1(int i)								//utilizado por productor
	{
		esc1 = true;
		turno2 = 1;
		
		while (esc2 && turno2 == 1) Thread.yield();
		while (tam == buf) Thread.yield();
		
		esc=true;
		turno=0;
		
		while (lee && turno == 1)  Thread.yield();	//espera activa. Preprotocolo
		
		tam++;
		var = i;
		System.out.println("Productor 1 ha producido " + var);		// Postprotocolo
		
		esc1 = false;
		esc=false;
	}
	
	public void escribir2(int i)								//utilizado por productor
	{
		esc2 = true;
		turno2 = 0;
		
		while (esc1 && turno2 == 1) Thread.yield();
		while (tam == buf) Thread.yield();
		
		esc=true;
		turno=0;
		
		while (lee && turno == 1)  Thread.yield();	//espera activa. Preprotocolo
		
		tam++;
		var = i;
		System.out.println("Productor 2 ha producido " + var);		// Postprotocolo
		
		esc1 = false;
		esc=false;
	}

	public void leer(int i)											//utilizado por consumidor
	{
		lee = true;
		turno = 0;
		
		while (esc && turno == 0) Thread.yield();		//espera activa. Preprotocolo
		
		tam--;
		System.out.println("				Consumidor ha leido " + i);		
	
		lee = false; 										//Postprotocolo
	}
}

//CS-Prod: No puedo escribir un dato hasta que no se ha leído todo lo que habia
//CS-Cons: No puedo leer un dato hasta que no se ha llenado todo el almacenamiento
//prod(0,1,2,3)->cons(0,1,2,3)->prod(0,1,2,3)->cons(0,1,2,3)->....