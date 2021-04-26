package interrupciones;

import java.util.concurrent.BlockingQueue;

public class Consumidor extends Thread{

	private BlockingQueue<Integer> buffer;

	public Consumidor (BlockingQueue<Integer> buffer){
		this.buffer = buffer;
	}

	public void run(){

		boolean fin = false;
		int cont = 0;
		while (!isInterrupted() && (!fin) || buffer.size() > 0){
			try{
				Thread.sleep(50);
				System.out.println(isInterrupted());
				int i = buffer.take();
				cont++;
				System.out.println(i);
			} catch (InterruptedException ie){fin = true;}
		}
		System.out.println("He consumido "+cont+" elementos.");
	}
}