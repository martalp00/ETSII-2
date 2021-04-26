package Tiovivo;

public class Tiovivo {
		
	private int numSitios;
	private int pasajeros=0;
	
	private boolean moviendo = false;
	private boolean vacio = true;
	private boolean viajeTerminado = false;
	private boolean hayPersonasViajeAnterior = false;
	
	public Tiovivo(int i) {
		this.numSitios=i;
	}

	public synchronized void subir(int id) throws InterruptedException{
		while(moviendo || pasajeros == numSitios || !vacio) {
			wait();
		}
		
		pasajeros++;
		System.out.println("Se monta " + id);
		notifyAll();
	}
	
	public synchronized void bajar(int id) throws InterruptedException{
		while(moviendo || !viajeTerminado) {
			wait();
		}
		pasajeros--;
		System.out.println("Se baja " + id);
		
		if(pasajeros==0) {
			viajeTerminado=false;
			vacio=true;
			hayPersonasViajeAnterior=false;
			
			notifyAll();
		}	
	}
	
	public  synchronized void esperaLleno () throws InterruptedException{
		while(pasajeros < numSitios || hayPersonasViajeAnterior) {
			wait();
		}
		moviendo = true;
		System.out.println("Inicio del viajeeeeee");
		notifyAll();
	}
	
	public synchronized void finViaje (){
		System.out.println("Se acabo el viajeeeeeee");
		moviendo=false;
		
		viajeTerminado=true;
		vacio = false;
		hayPersonasViajeAnterior=true;
		
		notifyAll();
		
	}
}
