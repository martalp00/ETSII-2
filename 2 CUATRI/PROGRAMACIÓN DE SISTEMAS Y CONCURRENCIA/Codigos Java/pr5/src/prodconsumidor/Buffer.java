package prodconsumidor;

public class Buffer 
{ //Recurso
	
	private int[] buffer;
	private int p = 0;//indice del productor
	private int c = 0;//indice del consumidor
	private int nElem = 0; // no elementos del buffer
	
	private volatile boolean fp = false;
	private volatile boolean fc = false;
	private volatile int turno=0; //0=productor, 1=consumidor
	
	public Buffer(int tam)
	{
		buffer = new int[tam];
	}
	
	public void almacenar(int dato)
	{
		//utilizado por el productor
		//pre-prod
		
		while (nElem == buffer.length)
		{ //CS-prod
			Thread.yield();
		}
		
		fp = true; //exc. mutua
		turno = 1;
		
		while (fc && turno==1)
		{
			Thread.yield();
		}
		
		buffer[p]=dato;
		System.out.println("Productor almacena "+dato);
		p = (p + 1)%buffer.length; //para usar el buffer de forma circular
		nElem++;
		
		fp = false;
		//post-prod
	}
	
	public int extraer()
	{
		//utilizado el consumidor 
		while (nElem==0) 
		{ //CS-consumidor
			Thread.yield();
		}
		
		fc = true; // exc. mutua
		turno = 0;
		while (fp && turno==0)
		{
			Thread.yield();
		}
		
		int dato = buffer[c];
		System.out.println("                      consumidor extrae "+dato);
		c = (c+1)%buffer.length;
		nElem--;
		
		fc = false;
		
		return dato;
	}
}

//asegurar exclusión mutua (nElem)
//CS-Prod: espero hasta que el buffer no está lleno
//CS-Cons: espero hasta que el buffer no esté vacío
