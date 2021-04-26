package lectoresEscritoresJustoEficiente;

import java.util.Random;

public class Escritor extends Thread{
	private Random rnd = new Random();
	private int id; 
	private GestorBD g;
	
	public Escritor(int id, GestorBD g){
		this.id = id; 
		this.g = g;
	}
	
	public void run(){
		while (true){
			try{
				g.entraEscritor(id);
				//escritor id en la BD
				System.out.println("	Escritor " + id + " en la BD");
				Thread.sleep(rnd.nextInt(100));
				g.saleEscritor(id);
				Thread.sleep(rnd.nextInt(100));
			}catch(InterruptedException ie){}     }
	}
}
