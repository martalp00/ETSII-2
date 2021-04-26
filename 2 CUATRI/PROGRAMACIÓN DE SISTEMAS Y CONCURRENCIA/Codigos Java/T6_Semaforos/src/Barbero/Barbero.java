package Barbero;

import java.util.Random;

public class Barbero extends Thread{
	private Random rnd = new Random();
    private Barberia b;
    
    public Barbero(Barberia b){
        this.b = b;
    }
    
    public void run(){
       while (true){
           try {
        	//El barbero est� continuamente pelando
        	//pero si al intentar pelar no tiene clientes se duerme
			b.pelar();
			Thread.sleep(rnd.nextInt(100));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
    }
}
