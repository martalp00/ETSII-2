package jardines;


public class JardinPI {
		
	private static int cont = 0;//visitantes al jardín
	private static volatile boolean f0 = false; // quiere ejecutar sc0?
	private static volatile boolean f1 = false; // quiere ejecutar sc1?
	
	static class Puerta0 extends Thread{
		public void run(){
			for (int i = 0; i<100; i++){
				//SNC0
				//Pre0
				f0=true;
				while (f1) {
					Thread.yield();
				}
				cont++;//SC0
				f0=false;//Post0
			}
			
		}
	}
	static class Puerta1 extends Thread{
		public void run(){
			for (int i = 0; i<100; i++){
				//SNC1
				f1=true;
				while (f0) {
					Thread.yield();
				}
				cont++;//SC1
				f1=false;//Post0
			}
		}
	}
	public static void main(String[] args){
		Puerta1 h1 = new Puerta1();
		Puerta0 h0 = new Puerta0()	;
		h1.start();
		h0.start();
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
