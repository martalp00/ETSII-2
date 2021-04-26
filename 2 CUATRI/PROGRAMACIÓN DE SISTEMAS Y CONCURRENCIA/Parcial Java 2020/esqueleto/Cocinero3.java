package restaurante;

import java.util.*;
public class Cocinero3 extends Thread{
	private Random r = new Random();
	private Restaurante b;

	public Cocinero3(Restaurante b){
		this.b = b;
	}
	public void run(){
		for (int i=0 ; i<25; i++){
			try {
				Thread.sleep(r.nextInt(10));
				b.esperaEcharAgua();
				Thread.sleep(r.nextInt(200)); //Fran está cubriendo el hoyo
				b.finEcharAgua(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
