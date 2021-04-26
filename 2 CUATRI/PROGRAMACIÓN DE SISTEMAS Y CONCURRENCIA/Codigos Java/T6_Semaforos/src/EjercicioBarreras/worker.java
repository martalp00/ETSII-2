package EjercicioBarreras;

import java.util.Random;

public class worker extends Thread {
	private Gestor g;
	private int id;
	private static Random r = new Random();
	
	public worker(int id, Gestor g) {
		this.id=id;
		this.g=g;
	}
	
	public void run() {
		for(int i=0; i<25; i++) {			
			try {
				Thread.sleep(r.nextInt(100));
				g.finIteraccion(id, i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


