package jardines;



public class JardinSI {
		
	private static int cont = 0;//visitantes al jardín
	private static volatile boolean f0 = false; // quiere ejecutar sc0?
	private static volatile boolean f1 = false; // quiere ejecutar sc1?
	
	static class Puerta0 extends Thread{
		public void run(){
			for (int i = 0; i<1000; i++){
				//SNC0
				//Pre0		
				while (f1) {
					Thread.yield();
				}	
				f0=true;
				
				cont++;//SC0		
				
				f0=false;//Post0
			}
			
		}
	}
	static class Puerta1 extends Thread{
		public void run(){
			for (int i = 0; i<1000; i++){
				//SNC1					
				while (f0) {
					Thread.yield();
				}
				f1=true;
				
				cont++;//SC1	
				
				f1=false;//Post0
			}
		}
	}
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
