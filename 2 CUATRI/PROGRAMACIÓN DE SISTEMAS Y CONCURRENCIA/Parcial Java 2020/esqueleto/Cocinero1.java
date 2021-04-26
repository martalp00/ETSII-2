package restaurante;

import java.util.Random;

public class Cocinero1 extends Thread{
	private Random r = new Random();
	private Restaurante b;
	public Cocinero1(Restaurante b){
		this.b = b;
	}
	public void run(){
		for (int i=0 ; i<25; i++){
			try {
				Thread.sleep(r.nextInt(10));
				b.esperaHacerSofrito();
				Thread.sleep(r.nextInt(200)); //David está haciendo el hoyo
				b.finHacerSofrito(i);	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
