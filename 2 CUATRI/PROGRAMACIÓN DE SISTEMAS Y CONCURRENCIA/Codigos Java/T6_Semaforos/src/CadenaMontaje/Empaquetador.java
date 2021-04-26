package CadenaMontaje;

import java.util.Random;

public class Empaquetador extends Thread{
	private static Random r = new Random();
	private Cadena c;
	private int id;
	public Empaquetador(Cadena c,int id){
		this.c = c;
		this.id = id;
	}
	public void run(){
		while (true){
			try {
				
				c.extraeProducto(id);
				Thread.sleep(r.nextInt(1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
