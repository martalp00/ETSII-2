package Conferencia;

import java.util.concurrent.*;
public class SalaConferencias {
	
	private int cap;
	private int numAsis = 0;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore pEntAbierta = new Semaphore(1);
	//pEntAbierta = 1 sii numAsis<cap, pEntAbierta = 0 sii numAsis=cap
	private Semaphore pSalAbierta = new Semaphore(0);
	//se inicializa a 0 para que no salga nadie hasta que estén todos
	private Semaphore lleno = new Semaphore(0);
	public SalaConferencias(int cap){
		this.cap = cap;
	}
	
	public void entroSala(int id) throws InterruptedException{
		pEntAbierta.acquire();
		mutex.acquire();
		numAsis++;
		System.out.println("El asistente "+id+" ha entrado en la sala "+numAsis);
		if (numAsis<cap){
			pEntAbierta.release();
			mutex.release();	
		} else {
			System.out.println("                                    sala llena!!");
			lleno.release();
			mutex.release();
		}
		pSalAbierta.acquire();
	}
	public void salgoSala(int id) throws InterruptedException{
		mutex.acquire();
		numAsis--;
		System.out.println("El asistente "+id+" ha salido de la sala "+numAsis);
		if (numAsis!=0){
			pSalAbierta.release();
			mutex.release();
		} else {
			System.out.println("                                    sala vacía!!");
			pEntAbierta.release();
			mutex.release();
		}
		
	}
	
	public void esperaLleno() throws InterruptedException{
		lleno.acquire();
		System.out.println("                               Comienza la conferencia");
	}
	
	public void finConferencia(){
		System.out.println("                              Fin de la Conferencia!!!");
		pSalAbierta.release();
	}
}

//CS-As1: Un asistente tiene que esperar si la sala está llena
//CS-As2: Un asistente que está en la sala, no puede salir hasta que el conferenciante
// ha terminado
//CS-C: No puede dar la conferencia hasta que la sala no esté llena
