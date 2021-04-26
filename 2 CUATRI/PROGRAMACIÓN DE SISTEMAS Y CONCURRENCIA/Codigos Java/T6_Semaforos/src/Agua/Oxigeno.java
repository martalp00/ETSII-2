package Agua;
import java.util.*;
public class Oxigeno extends Thread{
	private int id;
	private static Random r = new Random();
	private GestorAgua gestor;
	public Oxigeno(int id,GestorAgua gestor){
		this.id = id;
		this.gestor = gestor;
	}
	
	public void run(){
		while (true){
			try {
				Thread.sleep(r.nextInt(1000));
				gestor.llegaOxigeno(id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
