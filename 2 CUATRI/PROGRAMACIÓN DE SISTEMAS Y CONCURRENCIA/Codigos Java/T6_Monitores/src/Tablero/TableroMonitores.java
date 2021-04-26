package Tablero;
import java.util.Arrays;

public class TableroMonitores implements TableroDeTareas {
	private final int MAX; //numero max de tareas propuestas
	private char[] tareas; 
	private int huecosLibres;
	private int sitioLibre;
	private int huecoA;
	
	public TableroMonitores(int max) { 
		this.MAX = max;
		this.tareas = new char[max]; 
		this.huecosLibres = max;
	}
	
	private int buscarLibre() {
		int pointer = 0;
		while (tareas[pointer] != 0) {
			pointer++;
		}
		return pointer;
	}
	
	@Override
	public synchronized void PonerTareaA() throws InterruptedException { 
		// Mientras no haya huecos libres, mantener en espera.
		while (huecosLibres == 0) {
			wait();
		}
		
		// Buscar la posicioÌ�n de un hueco libre.
		sitioLibre = buscarLibre();
		
		// Asignar la tarea y decrementar huecos libres.
		tareas[sitioLibre] = 'A';
		huecosLibres--;
		System.out.println("El jefe A ha puesto una nueva tarea.");
		System.out.println(Arrays.toString(tareas));
		System.out.println("*************************");
		notifyAll();
	}
	
	@Override
	public synchronized void PonerTareaB() throws InterruptedException { 
		// Mientras no haya huecos libres, mantener en espera.
		while (huecosLibres == 0) {
			wait();
		}
		// Buscar la posicioÌ�n de un hueco libre.
		sitioLibre = buscarLibre();
		
		// Asignar la tarea y decrementar huecos libres.
		tareas[sitioLibre] = 'B';
		huecosLibres--;
		System.out.println("El jefe B ha puesto una nueva tarea.");
		System.out.println(Arrays.toString(tareas));
		System.out.println("*************************");
		notifyAll();
	}
	
	private int encontrarA() {
		int pointer = 0;
		while(pointer < tareas.length && tareas[pointer] != 'A') {
			pointer++;
		}
		return pointer;
	}
	
	@Override
	public synchronized void RecogerTarea(int id) throws InterruptedException { 
		// Mientras todos los huecos esten libres, esperar. 
		while(huecosLibres == tareas.length) wait();
		// Buscar primeramente si hay tareas prioritarias (tipo A)
		huecoA = encontrarA();
		// Si no se ha encontrado una tarea prioritaria, buscar una de tipo B.
		if(huecoA == tareas.length) {
			huecoA = 0;
			while(tareas[huecoA] == 0) {
				huecoA++;
			}
		}
		System.out.println("El trabajador " + id + " recoge una tarea de tipo " + tareas[huecoA]);
		System.out.println(Arrays.toString(tareas)); 
		System.out.println("*************************");
		// Eliminar tarea de la lista y aumentar los huecos libres. 
		tareas[huecoA] = 0;
		huecosLibres++;
		notifyAll();
	}
	

	@Override
	public void Trabajar(int id) throws InterruptedException {
		System.out.println("El trabajador " + id + " trabaja en su tarea.");
	}

	@Override
	public void cafe(int id) throws InterruptedException {
		System.out.println("El trabajador " + id + " se toma su cafe.");
	}
}
