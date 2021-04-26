package jardines;



public class Peterson {

	private static int cont = 0;//visitantes al jardín
	private static volatile boolean f0 = false; // quiere ejecutar sc0?
	private static volatile boolean f1 = false; // quiere ejecutar sc1?
	private static volatile int turno = 0; //a quien le toca ejecutar la sc
	
	static class Puerta0 extends Thread{
		public void run()
		{
			for (int i = 0; i<10000; i++)
			{
				//SNC0			
				//Pre0		
				f0 = true;
				turno = 1;
				while (f1 && turno==1){ //h0 entra porque turno == 0
					Thread.yield();
				}
			    //SC0		
				cont++; //f0 es true
				System.out.println("Productor ");
				//Post0
				f0 = false;
			}	
		}
	}
	static class Puerta1 extends Thread
	{
		public void run()
		{	
			for (int i = 0; i<10000; i++)
			{
				//SNC1 //f1 es false
				//Pre1				
				f1 = true;
				turno = 0;
				
				while (f0 && turno == 0)
				{//h1 está esperando
					Thread.yield();
				}
			    //SC1	
				cont++;
				System.out.println("cons ");
				//Post1
				f1 = false;
				
			}
		}
	}
	
	//1.-cuando h0 entra en su sc0 quiero probar que h1 no está en sc1:
	//cuando h0 entra en su sc0 pueden ocurrir dos cosas
	// como f1 && turno==1 es false
	// a) f1 es false, entonces h1 está en su snc1 
	// b) turno == 0 , entonces h1 está en su pre1
	
	//2.- Mientras que h0 está en sc0, quiero probar que h1 no puede entrar en sc1
	// a) f0 es true
	// b) turno es 0
	// Peterson satisface el Requisito 1  (exclusión mutua)
	
	// Ausencia de livelock 
	// para tener livelock necesitamos que las expresiones f1 && turno == 1 y
	// f0 && turno == 0 sean ambas true simultáneamente. Si f0 y f1 son true, la variable 
	//turno o es 0 o es 1, con lo cual una de las dos expresiones es falsa.
	
	// Requisito 3: se satisface porque si h1 está en su snc1, entonces f1 es false,
	// por lo que f1 && turno==1 es false y h0 puede entrar.
	
	// Requisito adicional de justicia: 
	
	public static void main(String[] args)
	{
		Puerta0 h0 = new Puerta0();
		Puerta1 h1 = new Puerta1();
		h0.start();
		h1.start();
		try {
			h1.join();
			h0.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Han entrado "+cont);
	}

}
