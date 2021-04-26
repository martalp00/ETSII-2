package Tablero;
import java.util.Random;
public class Trabajador extends Thread { 
	private Random r = new Random(); 
	private TableroDeTareas tablero; 
	private int id;
	
	public Trabajador(TableroDeTareas tablero, int id) { 
		this.tablero = tablero;
		this.id = id;
	}

	public void run() { 
		while(true) {
			try {
				tablero.RecogerTarea(id);
				Thread.sleep(r.nextInt(50)); 
				tablero.Trabajar(id); 
				Thread.sleep(500); 
				tablero.cafe(id);
			} catch(InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
}
