package conferencia;


import java.util.*;
public class Conferenciante extends Thread {
	private static Random r = new Random();

	private SalaConferencias sala;
	public Conferenciante(SalaConferencias sala){
		this.sala = sala;
	}

	public void run(){
		while (true){
			try {
				Thread.sleep(r.nextInt(500));
				sala.esperaLleno();
				Thread.sleep(r.nextInt(500));
				sala.finConferencia();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}