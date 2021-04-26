package Semaforos;


import java.util.concurrent.*;

public class GestorAgua {

	private int n_o = 0;
	private int n_h = 0;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore h = new Semaphore(1);
	private Semaphore o = new Semaphore(1);
	
	public void hListo(int id) throws InterruptedException{ 
		h.acquire();
		mutex.acquire();
		n_h++;
		System.out.println("Entra hidrogeno y hay " + n_h + "atomo/s de hidrogeno");
		
		mutex.release();
		if(n_h < 2) {
			h.release();
		}
		if(n_o == 1 && n_h == 2) {
			System.out.println("Molecula formada");
			o.release();
			h.release();
			n_o = 0;
			n_h = 0;
		}
	}
	
	public void oListo(int id) throws InterruptedException{ 
		
		o.acquire();
		mutex.acquire();
		n_o++;
		System.out.println("Entra oxigeno y hay " + n_o + "atomo/s de oxigeno");
		if(n_o == 1 && n_h == 2) {
			System.out.println("Molecula formada");
			o.release();
			h.release();
			n_o = 0;
			n_h = 0;
		}
		mutex.release();
		
	}
}