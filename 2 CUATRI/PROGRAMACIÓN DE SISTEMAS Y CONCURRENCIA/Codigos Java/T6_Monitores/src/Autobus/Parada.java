package Autobus;


public class Parada {
	
	private boolean hayBus = false;
	private boolean secundaria = true;
	private int numS = 0;
	private int numP = 0;
	private boolean lleno = false;
	public Parada(){
		
	}
	/**
	 * El pasajero id llama a este metodo cuando llega a la parada.
	 * Siempre tiene que esperar el siguiente autobus en uno de los
	 * dos grupos de personas que hay en la parada
	 * El metodo devuelve el grupo en el que esta esperando el pasajero
	 * 
	 */
	public synchronized int esperoBus(int id) throws InterruptedException{
	
		if (hayBus){
			System.out.println("Hay bus. Pasajero "+id+ " espera en la cola secundaria");
			numS++;
			while (hayBus) wait();
			numS--;
			if (numS==0) notifyAll();
		}
		System.out.println("No hay bus. Pasajero "+id+ " espera en la cola principal");
		numP++;
		while (!hayBus) wait();
		return 0;
		 //comentar esta línea
	}
	/**
	 * Una vez que ha llegado el autobús, el pasajero id que estaba
	 * esperando en el grupo i se sube al autobus
	 *
	 */
	public synchronized void subeAutobus(int id,int i){
		
		numP--;
		System.out.println("Pasajero "+id+ " se sube al autobús "+numP);
		if (numP==0){
			lleno = true;	
			notifyAll();
		}
	}
	/**
	 * El autobus llama a este metodo cuando llega a la parada
	 * Espera a que se suban todos los viajeros que han llegado antes
	 * que el, y se va
	 * 
	 */
	public synchronized void llegoParada() throws InterruptedException{
		System.out.println("LLEGA AUTOBUS");
		hayBus = true;
		notifyAll();
		if (numP!=0) while (!lleno) wait();
		lleno = false;
		System.out.println("SALE AUTOBUS");
		hayBus = false;
		notifyAll();
		while (numS!=0) wait();
	}
}
