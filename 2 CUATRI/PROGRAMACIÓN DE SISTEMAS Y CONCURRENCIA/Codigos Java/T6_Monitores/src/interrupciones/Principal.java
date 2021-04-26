package interrupciones;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Principal {
	public static void main(String[] args){
		BlockingQueue<Integer> buffer = new ArrayBlockingQueue<Integer>(5);

		Productor p = new Productor(buffer);
		Consumidor c = new Consumidor(buffer);
		p.start();
		c.start();
		try{
			Thread.sleep(new java.util.Random().nextInt(5000));
			p.interrupt(); //Interrumpimos al productor
			p.join();
			c.interrupt(); //Interrumptimos al consumidor (que esperará hasta consumir todo lo pendiente)
			c.join();
		}catch (InterruptedException ie){}
		System.out.println("Programa terminado");
	}
}
