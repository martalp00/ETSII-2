package conferencia;


public class SalaConferencias {
	
	private int cap;
	private int numAsis = 0;
	private boolean pEntAbierta = true;
	private boolean pSalAbierta = false;
	private boolean lleno = false;
	
	public SalaConferencias(int cap){
		this.cap = cap;
	}
	
	public synchronized void entroSala(int id) throws InterruptedException{
		while (!pEntAbierta) wait();
		numAsis++;
		System.out.println("El asistente "+id+" ha entrado en la sala "+numAsis);
		if (numAsis==cap){
			pEntAbierta = false;	
			lleno = true;
			notifyAll();
			System.out.println("                                    sala llena!!");
		}	
		while (!pSalAbierta) wait();
	}
	public synchronized void salgoSala(int id) throws InterruptedException{
		
		numAsis--;
		System.out.println("El asistente "+id+" ha salido de la sala "+numAsis);
		if (numAsis==0){
			System.out.println("                                    sala vacía!!");
			pEntAbierta = true;
			pSalAbierta = false;
			notifyAll();
		}
	}
	
	public synchronized void esperaLleno() throws InterruptedException{
		while (!lleno) wait();
		lleno=false; //para la siguiente iteración
		System.out.println("                               Comienza la conferencia");
	}
	
	public synchronized void finConferencia(){
		
		System.out.println("                              Fin de la Conferencia!!!");
		pSalAbierta = true;
		notifyAll();
	}
}

//CS-As1: Un asistente tiene que esperar si la sala está llena
//CS-As2: Un asistente que está en la sala, no puede salir hasta que el conferenciante
// ha terminado
//CS-C: No puede dar la conferencia hasta que la sala no esté llena
