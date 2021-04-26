package Semaforos;

import java.util.concurrent.Semaphore;

public class Concurso {
	private int car0 = 0;
	private int car1 = 0;
	private int mon0 = 0;
	private int mon1 = 0;
	private int numvic0 = 0;
	private int numvic1 = 0;
	
	
	private Semaphore mutex = new Semaphore (1);
	private Semaphore esp0 = new Semaphore (1);
	private Semaphore esp1 = new Semaphore (1);
	
	public void tirarMoneda(int id,boolean cara) throws InterruptedException {
		
		if(id == 0) {
			esp0.acquire();
		}else {
			esp1.acquire();
		}
		
		mutex.acquire();
		
		if(id == 0) {
			mon0++;
			if(cara) {
				car0++;
			}
			if(mon0 < 10) {
				esp0.release();
			}
		
		} else {
			mon1++;
			if(cara) {
				car1++;
			}
			if(mon1 < 10) {
				esp1.release();
			}
		}
		
		if(mon0 == 10 && mon1 == 10) {
			if(car0 > car1) {
				System.out.println("Ha ganado la partida el jugador 0 con " + car0 + " caras");
				numvic0++;
			}else if(car1 > car0) {
				System.out.println("Ha ganado la partida el jugador 1 con " + car1 + " caras");
				numvic1++;
			} else {
				System.out.println("El juego ha empatado");
			}
			
			if(numvic0 == 3) {
				System.out.println("Final del concurso. Ha ganado el jugador 0");
			}
			if(numvic1 == 3) {
				System.out.println("Final del concurso. Ha ganado el jugador 1");
			}
			
			
			mon0 = 0;
			mon1 = 0;
			car0 = 0;
			car1 = 0;
			esp0.release();
			esp1.release();
		}
		
		mutex.release();
		
	}	
	
	public boolean concursoTerminado() {
		return numvic0 == 3 || numvic1 == 3;
	}
}