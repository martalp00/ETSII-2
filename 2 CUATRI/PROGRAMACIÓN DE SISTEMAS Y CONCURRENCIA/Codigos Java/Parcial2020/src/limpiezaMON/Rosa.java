package limpiezaMON;

import java.util.Random;

public class Rosa extends Thread{
	private Random r = new Random();
	private Vestuario b;
	public Rosa(Vestuario b){
		this.b = b;
	}
	public void run(){
		for (int i=0 ; i<25; i++){
			try {
				Thread.sleep(r.nextInt(10));
				b.esperaLimpiar();
				Thread.sleep(r.nextInt(200)); //Juan está poniendo una semilla
				b.finLimpiar(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}