package jardines;

public class Jardin {//recurso
	private static int cont = 0;//visitantes al jardín
	
	
	

	static class Puerta1 extends Thread{
		public void run(){
			for (int i=0; i<1000; i++)	
				cont++; 
			}
	}
	static class Puerta0 extends Thread{
		public void run(){
			for (int i=0; i<1000; i++)			
				cont++; 
			}
	}
	public static void main(String[] args){
		Puerta1 p1 = new Puerta1();
		Puerta0 p0 = new Puerta0()	;
		p1.start();
		p0.start();
		try {
			p1.join();
			p0.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Han entrado "+cont);
	}
}
