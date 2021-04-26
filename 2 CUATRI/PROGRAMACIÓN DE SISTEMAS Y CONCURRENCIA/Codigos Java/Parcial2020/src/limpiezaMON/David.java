package limpiezaMON;

import java.util.*;

public class David extends Thread{
	private Random r = new Random();
	private Vestuario b;

	public David(Vestuario b){
		this.b = b;
	}
	public void run(){
		for (int i=0 ; i<25; i++){
			try {
				Thread.sleep(r.nextInt(10));
				b.esperaSecar();
				Thread.sleep(r.nextInt(200)); //Fran está cubriendo el hoyo
				b.finSecar(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
