package Tablero;


import java.util.Random;

public class Jefe extends Thread {
	private Random r = new Random();
	private TableroDeTareas tablero;
	private char tipoTarea;

	public Jefe(TableroDeTareas tablero, char tipoTarea) {
		this.tablero = tablero;
		this.tipoTarea = tipoTarea;
	}

	public void run() { 
		while(true) {
			try {
				Thread.sleep(r.nextInt(100)); 
				if(tipoTarea == 'A')
					tablero.PonerTareaA(); 
				else if(tipoTarea == 'B')
					tablero.PonerTareaB(); 
				else
						throw new RuntimeException("El tipo de tarea estaÌ�mal indicado o no existe.");
			} catch(InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
}
