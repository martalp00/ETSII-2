package Agua;

import java.util.Random;

public class Hidrogeno extends Thread{
	private int id;
	private static Random r = new Random();
	private GestorAgua gestor;
	public Hidrogeno(int id,GestorAgua gestor){
		this.id = id;
		this.gestor = gestor;
	}
	
	public void run(){
		while (true){
			try {
				Thread.sleep(r.nextInt(1000));
				gestor.llegaHidrogeno(id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
