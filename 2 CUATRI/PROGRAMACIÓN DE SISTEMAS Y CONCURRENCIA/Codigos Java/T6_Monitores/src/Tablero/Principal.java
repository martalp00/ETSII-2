package Tablero;
public class Principal {
	public static void main(String[] args) {
		final int MAX = 10;
		final int N = 3;
		
		//TableroDeTareas tablero = new TableroSemaforos(MAX);
		TableroDeTareas tablero = new TableroMonitores(MAX);
		Jefe jefeA = new Jefe(tablero, 'A');
		Jefe jefeB = new Jefe(tablero, 'B');
		Trabajador[] trabajadores = new Trabajador[N];
		
		// Crear los trabajadores
		for (int i = 0; i < trabajadores.length; i++) {
			trabajadores[i] = new Trabajador(tablero, i);
			trabajadores[i].start();
		}
		jefeA.start();
		jefeB.start();
	}
}
