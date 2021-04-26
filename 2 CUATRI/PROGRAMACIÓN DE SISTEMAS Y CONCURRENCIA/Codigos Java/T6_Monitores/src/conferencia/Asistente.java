package conferencia;

import java.util.*;
public class Asistente extends Thread {
	private static Random r = new Random();
	private int id;
	private SalaConferencias sala;
	public Asistente(int id,SalaConferencias sala){
		this.id = id;
		this.sala = sala;
	}

	public void run(){
		while (true){
			try {
				Thread.sleep(r.nextInt(500));
				sala.entroSala(id);
				sala.salgoSala(id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
