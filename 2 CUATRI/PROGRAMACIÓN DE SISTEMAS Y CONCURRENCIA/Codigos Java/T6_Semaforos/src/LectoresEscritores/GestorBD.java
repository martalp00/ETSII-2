package LectoresEscritores;

import java.util.concurrent.*;
public class GestorBD {
	private Semaphore escribiendo = new Semaphore(1);
	private int nLectores = 0;//cuantos lectores hay en la BD
	private Semaphore mutex1 = new Semaphore(1); //protege a nLectores
	private Semaphore leyendo = new Semaphore(1);
	private int nEsc = 0;//quieren entrar o está uno está dentro de la BD
	private Semaphore mutex2 = new Semaphore(1); //protege nEsc
	private Semaphore mutex3 = new Semaphore(1);
	public void entraLector(int id) throws InterruptedException{
		mutex3.acquire();//L5 L3
		leyendo.acquire();
		mutex1.acquire(); //está sobrecargado
		//mutex=0
		nLectores++; //LO
		if (nLectores == 1) escribiendo.acquire(); //puedo bloquearme en la SC
		System.out.println("Entra lector "+id+" Hay "+nLectores);
		mutex1.release();
		leyendo.release();
		mutex3.release();
	}
	public void entraEscritor(int id) throws InterruptedException{
		mutex2.acquire();
		nEsc++; 
		if (nEsc==1) leyendo.acquire();//cerrar la puerta al siguiente lector
		mutex2.release();
		
		escribiendo.acquire();//E1
		System.out.println("                      Entra escritor "+id+" Hay "+nLectores);
	}
	
	public void saleLector(int id) throws InterruptedException{
		mutex1.acquire();
		nLectores--;
		if (nLectores==0) escribiendo.release();
		System.out.println("Sale lector "+id+" Hay "+nLectores);
		mutex1.release();
	}
	public void saleEscritor(int id) throws InterruptedException{
		System.out.println("                      Sale escritor "+id+" Hay "+nLectores);
		escribiendo.release();
		mutex2.acquire();
		nEsc--;
		if (nEsc==0) leyendo.release();
		mutex2.release();
		
	}

}
//CS-Escritores: exclusion mutua
//CS-Lectores: puede haber varios pero nunca con un escritor