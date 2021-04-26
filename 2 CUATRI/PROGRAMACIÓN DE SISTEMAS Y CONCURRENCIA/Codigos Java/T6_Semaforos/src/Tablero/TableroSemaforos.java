package Tablero;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class TableroSemaforos implements TableroDeTareas {
	private final int MAX;
	private char[] tareas;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore huecosDisponibles; // Numero de tareas que se pueden introducir
	private Semaphore tareasDisponibles; //Numero de tareas que se pueden relaizar
	private int huecoLibre;
	private int huecoTipoA;
	       
	public TableroSemaforos(int max) { 
		this.MAX = max;
		this.tareas = new char[max]; 
		this.huecosDisponibles = new Semaphore(MAX); 
		this.tareasDisponibles = new Semaphore(0);
	}
	
	private int huecoDisponible() {
		int pointer = 0;
		while(tareas[pointer] != 0) {
			pointer++;
		}
		return pointer;
	}
	
	private int buscarTareaTipoA() {
		int pointer = 0;
		while (pointer < tareas.length && tareas[pointer] != 'A') {
			pointer++; 
		}
		return pointer;
	}
	
	public void PonerTareaA() throws InterruptedException {
		// TODO: Se valorara en la nota que si hay jefes esperando de los dos
		//los de tipo A pongan tareas prioritariamente sobre B.
		// Si se puede introducir tareas, intentar adquirir el mutex. */ 		
		huecosDisponibles.acquire();
		mutex.acquire();
		
		// Buscar hueco en el buffer de tareas.
		huecoLibre= huecoDisponible();
		
		// Insertar la nueva tarea.
		tareas[huecoLibre] = 'A';
		System.out.println("El jefe A ha puesto una nueva tarea."); 
		System.out.println(Arrays.toString(tareas)); 
		System.out.println("*************************");
		
		// Liberar un permiso en el semaforo de tareas disponibles. 
		tareasDisponibles.release();
		mutex.release();
	}
	
	public void PonerTareaB() throws InterruptedException {
		// Si se puede introducir tareas, intentar adquirir el mutex. */ 
		huecosDisponibles.acquire();
		mutex.acquire();
		
		// Buscar hueco en el buffer de tareas.
		huecoLibre= huecoDisponible();
		
		// Insertar la nueva tarea.
		tareas[huecoLibre] = 'B';
		System.out.println("El jefe B ha puesto una nueva tarea."); 
		System.out.println(Arrays.toString(tareas)); 
		System.out.println("*************************");
		
		// Liberar un permiso en el semaÌ�foro de tareas disponibles. 
		tareasDisponibles.release();
		mutex.release();
	}
	
	public void RecogerTarea(int id) throws InterruptedException {
		// Si hay tareas disponibles para realizar, adquirir el mutex.
		tareasDisponibles.acquire();
		mutex.acquire();
		
		// Buscar primeramente si hay tareas prioritarias (tipo A)
		huecoTipoA= buscarTareaTipoA();
		
		// Si no se ha encontrado una tarea prioritaria, buscar una de tipo B.
		if(huecoTipoA == tareas.length) {
			huecoTipoA = 0;
			while (tareas[huecoTipoA] == 0) huecoTipoA++;				
		}
		
		System.out.println("El trabajador "+id+" recoge una tarea de tipo "+tareas[huecoTipoA]);
		System.out.println(Arrays.toString(tareas));
		System.out.println("*************************"); 
		
		// Eliminar tarea de la lista
		tareas[huecoTipoA]=0;
		
		// Liberar un permiso de huecos disponibles. 
		huecosDisponibles.release();
		mutex.release();
	}

	public void Trabajar(int id) throws InterruptedException { 
		System.out.println("El trabajador " + id + " trabaja en su tarea.");
	}

	public void cafe(int id) throws InterruptedException {
		System.out.println("El trabajador " + id + " se toma su cafeÌ�.");
	}
}
