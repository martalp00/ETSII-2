package Monitores;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Concurso {
	private int car0 = 0;
	private int car1 = 0;
	private int mon0 = 0;
	private int mon1 = 0;
	private int numvic0 = 0;
	private int numvic1 = 0;
	
	private Lock l = new ReentrantLock();
	
	private boolean diezmon0 = false;
	private Condition  Cdiezmon0 = l.newCondition();
	
	private boolean diezmon1 = false;
	private Condition  Cdiezmon1 = l.newCondition();
	
	public void tirarMoneda(int id,boolean cara) throws InterruptedException {
		l.lock();
		try {
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
				diezmon0 = false;
				diezmon1 = false;
				Cdiezmon0.signalAll();
				Cdiezmon1.signalAll();
			}
			
			
			
			if(id == 0) {
				while(diezmon0) {
					Cdiezmon0.await();
				}
				
				mon0++;
				if(cara) {
					car0++;
				}
				if(mon0 == 10) {
					diezmon0 = true;
				}
				
				
			}else {
				while(diezmon1) {
					Cdiezmon1.await();
				}
				
				mon1++;
				if(cara) {
					car1++;
				}
				if(mon1 == 10) {
					diezmon1 = true;
				}
			}

		}finally {
			l.unlock();
		}
		
	}	
	
	public boolean concursoTerminado() {
		return numvic0 == 3 || numvic1 == 3;
	}
}