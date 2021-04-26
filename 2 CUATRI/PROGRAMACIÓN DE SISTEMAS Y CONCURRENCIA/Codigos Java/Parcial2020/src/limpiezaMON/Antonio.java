package limpiezaMON;

import java.util.Random;


public class Antonio extends Thread{
	private Random r = new Random();
	private Vestuario b;
	public Antonio(Vestuario b){
		this.b = b;
	}
	public void run(){
		for (int i=0 ; i<25; i++){
			try {
				Thread.sleep(r.nextInt(10));
				b.esperaDesinfectar();
				Thread.sleep(r.nextInt(200)); //David está haciendo el hoyo
				b.finDesinfectar(i);	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
