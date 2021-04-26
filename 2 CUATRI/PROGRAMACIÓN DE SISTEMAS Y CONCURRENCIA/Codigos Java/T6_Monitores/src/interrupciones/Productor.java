package interrupciones;

import java.util.concurrent.*;
import java.util.*;
public class Productor extends Thread{

	private BlockingQueue<Integer> buffer;

	private Random r = new Random();

	public Productor (BlockingQueue<Integer> buffer){
		this.buffer = buffer;
	}

	public void run(){
		boolean fin = false;
		int cont  = 0;
		while (!isInterrupted() && !fin){
			try{
				Thread.sleep(50);
				buffer.put(r.nextInt(25));
				cont++;
			} catch (InterruptedException ie){fin = true;}
		}
		System.out.println("He generado "+cont+" elementos");
	}
}