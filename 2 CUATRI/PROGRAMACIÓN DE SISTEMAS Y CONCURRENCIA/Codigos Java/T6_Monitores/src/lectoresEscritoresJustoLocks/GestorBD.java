package lectoresEscritoresJustoLocks;

import java.util.concurrent.locks.*;

//NO ES UNA IMPLEMENTACIÓN JUSTA
//Los lectores podrían no dejar a los escritores entrar nunca
public class GestorBD {

	private int nLectores = 0; //Lectores en la BD
	private boolean escribiendo = false;
	private int nEscritores = 0; //Escritores que quieren entrar en la BD (estén dentro o no)
	
	private Lock l = new ReentrantLock();
	private Condition okLeer = l.newCondition();
	private Condition okEscribir = l.newCondition();
	
	public void entraEscritor(int id) throws InterruptedException{
		l.lock();
		nEscritores++; 
		while (escribiendo || nLectores > 0) okEscribir.await();
		escribiendo = true;
		System.out.println("	Escritor " + id + " entrando en la BD");
		l.unlock();
	}
		
	public void entraLector(int id) throws InterruptedException{
		l.lock();
		while (escribiendo || nEscritores > 0) okLeer.await();
		nLectores++;
		System.out.println("Lector + " + id + " entrando en la BD");
		l.unlock();
	}
	
	public void saleEscritor(int id){
		l.lock();
		nEscritores--;
		escribiendo = false;
		System.out.println("	Escritor " + id + " saliendo de la BD");
		if (nEscritores > 0) okEscribir.signal();
		else okLeer.signalAll();
		l.unlock();
	}

	public void saleLector(int id) throws InterruptedException{
		l.lock();
		nLectores --;
		System.out.println("Lector + " + id + " saliendo de la BD");
		if (nLectores == 0) okEscribir.signal(); //AHORA TIENE QUE SER notifyAll porque podría haber lectores/escritores esperando 
		l.unlock();
	}
}
