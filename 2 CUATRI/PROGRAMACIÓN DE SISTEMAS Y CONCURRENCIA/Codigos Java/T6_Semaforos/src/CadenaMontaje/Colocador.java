package CadenaMontaje;

import java.util.*;
public class Colocador extends Thread{
	private Random r = new Random();
	private Cadena c;
	public Colocador(Cadena c){
		this.c = c;
	}
	public void run(){
		while (true){
			try {
				Thread.sleep(r.nextInt(200));
				c.nuevoProducto(r.nextInt(3));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
