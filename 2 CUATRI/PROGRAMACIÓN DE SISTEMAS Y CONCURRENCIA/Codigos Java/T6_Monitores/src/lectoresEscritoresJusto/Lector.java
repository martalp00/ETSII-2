package lectoresEscritoresJusto;

import java.util.Random;

public class Lector extends Thread{
	private Random rnd = new Random();
	private int id; 
	private GestorBD g;
	   
	public Lector(int id,GestorBD g){
	    this.id = id; 
	    this.g = g;
	}
	   
	public void run(){
	  while (true){
	    try{
	       g.entraLector(id);
	       //lector id en la BD
	       System.out.println("	Lector " + id + " en la BD");
	       Thread.sleep(rnd.nextInt(100));
	       g.saleLector(id);
	       Thread.sleep(rnd.nextInt(100));
	       //Thread.sleep(rnd.nextInt(1000));
	     }catch(InterruptedException ie){}
	   }
	 }
}
