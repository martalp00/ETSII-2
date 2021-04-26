package jardines;


public class JardinTI {
	
	private static int cont = 0;//visitantes al jardín
	//private static volatile boolean f0 = false; // quiere ejecutar sc0?
	//private static volatile boolean f1 = false; // quiere ejecutar sc1?
	private static volatile int turno = 0; //a quien le toca ejecutar la sc
	
	static class Puerta0 extends Thread{
		public void run(){
			for (int i = 0; i<10000; i++){
				//SNC0
				//Pre0	
				while (turno==1){
					Thread.yield();//espera activa
				}
			    //SC0		
				cont++;//mientras que h0 está en sc0 turno == 0
				//Post0
				turno = 1;
			}
			
		}
	}
	static class Puerta1 extends Thread{
		public void run(){
			
			for (int i = 0; i<10000; i++){
				//SNC1
				//Pre1
				while (turno==0){
					Thread.yield();
				}
			    //SC1	
				cont++; //turno == 1
				//Post1
				turno = 0;
				
			}
		}
	}
	//cuando h0 entra en sc0, h1 no está en sc1 porque turno == 0
	//mientras que h0 está sc0, h1 no puede entrar en sc1 porque turno==0
	//en conclusión se satisface el requisito 1 (exclusión mutua)
	
	//Como turno es 0 o 1, no es posible que ambos procesos estén bloqueados
	//en sus esperas activas, por lo que se satisface el requisito 2 
	//ausencia de livelock
	
	public static void main(String[] args){
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