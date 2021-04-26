package Impresoras;

import java.util.Random;

public class Cliente extends Thread {
	private Gestor g;
	private int id;
	private static Random r= new Random();
	
	public Cliente(int id, Gestor g) {
		this.id=id;
		this.g = g;
	}
	
	public void run() {
		for(int i=0; i<25; i++) {
			try {
				Thread.sleep(r.nextInt(1000));
				g.qimpresora(id);
				Thread.sleep(r.nextInt(200));//el cliente usando la impresora
				g.dimpresora(id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
