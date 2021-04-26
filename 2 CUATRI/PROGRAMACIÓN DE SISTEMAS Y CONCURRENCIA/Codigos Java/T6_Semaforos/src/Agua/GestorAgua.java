package Agua;

import java.util.concurrent.*;
public class GestorAgua {
	
	private int nH = 0;
	private int nO = 0;
	private Semaphore mutex = new Semaphore(1);//para nH y nO
	private Semaphore puertaH = new Semaphore(1); //para la entrada de h
	private Semaphore puertaO = new Semaphore(1); //para la entrada de o
	private Semaphore espera = new Semaphore(0);
	public void llegaHidrogeno(int id) throws InterruptedException{
		
		puertaH.acquire();
		mutex.acquire();
		nH++;//<=2
		System.out.println("Ha llegado el hidrógeno "+id);
		if (nH<2) puertaH.release();
		if (nH == 2 && nO==1){
			System.out.println("Se ha formado la molécula!!!!");
			System.out.println("******************************");
		} else{
			mutex.release();
			espera.acquire();//hay bloqueados 2 procesos
			mutex.acquire();
		}
		nH--;
		System.out.println("Ha salido el hidrógeno "+id);
		if (nH==0 && nO==0){
			puertaO.release();puertaH.release();
		} else {
			espera.release();
		}
		
		mutex.release();
	}
	public void llegaOxigeno(int id) throws InterruptedException{	
		puertaO.acquire();
		mutex.acquire();
		nO++;//==1
		System.out.println("Ha llegado el oxígeno "+id);
		if (nH == 2 && nO==1){
			System.out.println("Se ha formado la molécula!!!!");
			System.out.println("******************************");
		} else{
			mutex.release();
			espera.acquire();
			mutex.acquire();
		}
		nO--;
		System.out.println("Ha salido el oxígeno "+id);
		if (nH==0 && nO==0){
			puertaO.release();puertaH.release();
		} else {
			espera.release();
		}	
		mutex.release();
	}
	
}
//CS-un hidrogeno/oxigeno que llega tiene que esperarse hasta que se ha formado
//la molecula de agua
