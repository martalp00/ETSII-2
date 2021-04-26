package Tablero;
public interface TableroDeTareas {
	//Jefe de tipo A pone tareas de tipo A
	public void PonerTareaA() throws InterruptedException;

	// Jefe de tipo B pone tareas de tipo B
	public void PonerTareaB() throws InterruptedException; 
	
	// Trabajador recoge tarea
	public void RecogerTarea(int id) throws InterruptedException; 
	
	// Trabajador trabaja
	public void Trabajar(int id) throws InterruptedException;

	//Trabajador toma cafe
	public void cafe(int id) throws InterruptedException;
}
