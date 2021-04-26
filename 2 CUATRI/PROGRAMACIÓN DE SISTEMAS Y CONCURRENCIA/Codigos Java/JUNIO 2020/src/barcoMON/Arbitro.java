package barcoMON;

import java.util.Random;

public class Arbitro extends Thread{
	
	private Meta meta;
	private static Random r = new Random();
	public Arbitro(Meta meta){
		
		this.meta = meta;
	}
	
	public void run(){
		int i=0;
		while (i<6){
			try {			
				meta.esperoTodosListos();
				meta.esperoTodosTerminan();
				i++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
