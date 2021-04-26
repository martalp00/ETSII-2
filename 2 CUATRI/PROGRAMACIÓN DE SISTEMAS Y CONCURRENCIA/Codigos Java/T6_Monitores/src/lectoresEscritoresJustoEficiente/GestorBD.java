package lectoresEscritoresJustoEficiente;

import java.util.concurrent.Semaphore;

//NO ES UNA IMPLEMENTACIÓN JUSTA
//Los lectores podrían no dejar a los escritores entrar nunca
public class GestorBD {

	private int nLectores = 0; //Lectores en la BD
	private boolean escribiendo = false;
	private int nEscritores = 0; //Escritores que quieren entrar en la BD (estén dentro o no)
	
	private Condicion okLeer = new Condicion();  
	private Condicion okEscribir = new Condicion();
	
	public synchronized void entraEscritor(int id) throws InterruptedException{
		nEscritores++; 
		while (escribiendo || nLectores > 0) okEscribir.cwait();
		escribiendo = true;
		System.out.println("	Escritor " + id + " entrando en la BD");
	}
		
	public synchronized void entraLector(int id) throws InterruptedException{
		while (escribiendo || nEscritores > 0) okLeer.cwait();
		nLectores++;
		System.out.println("Lector + " + id + " entrando en la BD");
	}
	
	public synchronized void saleEscritor(int id){
		nEscritores--;
		escribiendo = false;
		System.out.println("	Escritor " + id + " saliendo de la BD");
		if (nEscritores > 0) okEscribir.cnotify();
		else okLeer.cnotifyAll();
	}

	public synchronized void saleLector(int id) throws InterruptedException{
		nLectores --;
		System.out.println("Lector + " + id + " saliendo de la BD");
		if (nLectores == 0) okEscribir.cnotify(); //AHORA TIENE QUE SER notifyAll porque podría haber lectores/escritores esperando 
	}
}
