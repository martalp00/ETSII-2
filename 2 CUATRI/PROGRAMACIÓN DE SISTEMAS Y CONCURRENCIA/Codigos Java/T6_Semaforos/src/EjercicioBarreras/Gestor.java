package EjercicioBarreras;

import java.util.concurrent.Semaphore;

public class Gestor {
	private final int N;
	
	private int hanTerminado=0;
	private Semaphore mutex = new Semaphore(1);//binarios
	private Semaphore espera = new Semaphore(0); //binario. Siempre vale 0.
	private Semaphore sigIteracion = new Semaphore(1);
	//sigIteracion =0 sii no se puede comenzar finIteracion de la siguiente
	public Gestor(int N) {
		this.N=N;
	}
	
	public void finIteraccion(int id, int iter) throws InterruptedException {
		sigIteracion.acquire();
		mutex.acquire();
		hanTerminado++;
		System.out.println("El worker " + id + " ha terminado la iter " + iter);
		if(hanTerminado<N) {
			mutex.release();
			sigIteracion.release();
			espera.acquire();//N-1 procesos bloqueados
			mutex.acquire();
		}
		hanTerminado--;
		if(hanTerminado != 0) {
			espera.release();			
		}else {
			sigIteracion.release();
		}
		mutex.release();
	}
}
//CS- worker no puede comenzar la iteracion i hasta que todos hayan terminado la iteracion
