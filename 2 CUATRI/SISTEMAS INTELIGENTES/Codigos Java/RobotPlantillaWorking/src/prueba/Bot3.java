package prueba;

import static robocode.util.Utils.normalRelativeAngleDegrees;


import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import robocode.Robot;


/**
 * @date 2018-03-22
 * 
 * Plantilla para la definición de un robot en Robocode
 *
 */
public class Bot3 extends Robot 
{

	int fila 		= 16;
	int columna 	= 12;
	int tamCelda 	= 50;
	int numObs		= 40;
	int filaPixels 	= 800;
	long semilla 	= 0;
	
	//The main method in every robot
	public void run() 
	{
		System.out.println("Iniciando ejecución del robot");
		
		// Nuestro robot será rojo
		setAllColors(Color.red);

		//DATOS QUE DEBEN COINCIDIR CON LOS DEL PROGRAMA main DE LA CLASE RouteFinder
		
		//Orientamos inicialmente el robot hacia arriba
		turnRight(normalRelativeAngleDegrees(0 - getHeading()));
		
		//A continuación nuestro robot girará un poco sobre sí mismo		
		int k = 0;
		while(k < 20)
		{
			turnRight(90);
			k++;
		}
		
		// AQUÃ� DEBE:
		//  1. GENERARSE EL PROBLEMA DE BÃšSQUEDA
		//  2. BUSCAR LA SOLUCIÃ“N CON UN ALGORITMO DE BÃšSQUEDA
		//  3. EJECUTAR LA SOLUCIÃ“N ENCONTRADA
		
		Dato camino = getCamino(fila, columna, numObs, semilla);
		
		if (camino != null) 
		{
			Stack<Estado> step = new Stack<Estado>();		//Usamos una pila para no tener que dar la vuelta a la lista enlazada
			
			while (camino != null)
			{
				step.push(camino.getEstado());
				camino = camino.getPadre();
			}
			
			Estado actual = step.pop();
			
			while (!step.isEmpty()) 		//Segun el movimiento se indica las coordenadas iniciales y las de destino junto con su orientación
			{
				Estado siguiente = step.pop();
				
				if (siguiente.getFila() == actual.getFila() + 1 && siguiente.getColumna() == actual.getColumna() + 1) 
				{
					aumentaFilaColumna(tamCelda);
					System.out.println("(" + actual.getFila() + "," + actual.getColumna() + ") -> (" 
										+ siguiente.getFila() + "," + siguiente.getColumna() + ")		Accion: Noreste");
				} 
				else if (siguiente.getFila() == actual.getFila() + 1 && siguiente.getColumna() == actual.getColumna() - 1) 
				{
					aumentaFilaDColumna(tamCelda);
					System.out.println("(" + actual.getFila() + "," + actual.getColumna() + ") -> (" 
							+ siguiente.getFila() + "," + siguiente.getColumna() + ")		Accion: Sureste");
				} 
				else if (siguiente.getFila() == actual.getFila() - 1 && siguiente.getColumna() == actual.getColumna() - 1) 
				{
					disminuyeFilaColumna(tamCelda);
					System.out.println("(" + actual.getFila() + "," + actual.getColumna() + ") -> (" 
							+ siguiente.getFila() + "," + siguiente.getColumna() + ")		Accion: Noroeste");
				} 
				else if (siguiente.getFila() == actual.getFila() - 1 && siguiente.getColumna() == actual.getColumna() + 1) 
				{
					disminuyeFilaAColumna(tamCelda);
					System.out.println("(" + actual.getFila() + "," + actual.getColumna() + ") -> (" 
							+ siguiente.getFila() + "," + siguiente.getColumna() + ")		Accion: Suroeste");
				} 
				else if (siguiente.getFila() == actual.getFila() + 1) 
				{
					aumentaFila(tamCelda);
					System.out.println("(" + actual.getFila() + "," + actual.getColumna() + ") -> (" 
							+ siguiente.getFila() + "," + siguiente.getColumna() + ")		Accion: Este");
				} 
				else if (siguiente.getFila() == actual.getFila() - 1) 
				{
					disminuyeFila(tamCelda);
					System.out.println("(" + actual.getFila() + "," + actual.getColumna() + ") -> (" 
							+ siguiente.getFila() + "," + siguiente.getColumna() + ")		Accion: Oeste");
				} 
				else if (siguiente.getColumna() == actual.getColumna() + 1) 
				{
					aumentaColumna(tamCelda);
					System.out.println("(" + actual.getFila() + "," + actual.getColumna() + ") -> (" 
							+ siguiente.getFila() + "," + siguiente.getColumna() + ")		Accion: Norte");
				} 
				else 
				{
					disminuyeColumna(tamCelda);
					System.out.println("(" + actual.getFila() + "," + actual.getColumna() + ") -> (" 
							+ siguiente.getFila() + "," + siguiente.getColumna() + ")		Accion: Sur");
				}
				actual = siguiente;
			}
			System.out.println("Camino Completado");
		} 
		else 
		{
			System.out.println("No existe un camino valido");
		}
			
	}
	
	//Distancia octal
	private static Dato getCamino (int nf, int nc, int no, long semilla) 
	{
		Problema prob = new Problema(nf, nc, no, semilla);
		
		System.out.println("Destino: (" + prob.getFinal()/prob.getColumnas() + "," + prob.getFinal()%prob.getColumnas() + ")");		//Se indica el destino
		
		PriorityQueue<Dato> abiertos = new PriorityQueue<Dato>();
		HashMap<Estado, Dato> arbol = new HashMap<Estado, Dato>();
		
		Estado inicial = new Estado(prob);
		Dato actual = new Dato(inicial);
		
		abiertos.add(actual);
		arbol.put(inicial, actual);
		
		while (!abiertos.isEmpty()) 
		{
			actual = abiertos.remove();
			
			if (actual.getEstado().finalP())
			{
				return actual;
			} 
			else 
			{
				List<Estado> sucesores = actual.getEstado().sucesores();
				
				for (Estado est : sucesores) 
				{
					Dato nuevo = new Dato(actual, est);
					
					if (!arbol.containsKey(est)) 
					{
						abiertos.add(nuevo);
						arbol.put(est,nuevo);
					} 
					else if (arbol.get(est).compareTo(nuevo) > 0) 
					{
						abiertos.add(nuevo);
						arbol.put(est,nuevo);
					}
				}
			}
		}
		return null;
	}
	
	//Funciones de movimiento
	
	private void aumentaFila (int tamCelda) 
	{
		turnRight(normalRelativeAngleDegrees(90-getHeading()));
		ahead(tamCelda);
	}
	
	private void disminuyeFila (int tamCelda)
	{
		turnRight(normalRelativeAngleDegrees(270-getHeading()));
		ahead(tamCelda);
	}
	
	private void aumentaColumna (int tamCelda) 
	{
		turnRight(normalRelativeAngleDegrees(0-getHeading()));
		ahead(tamCelda);
	}
	
	private void disminuyeColumna (int tamCelda) 
	{
		turnRight(normalRelativeAngleDegrees(180-getHeading()));
		ahead(tamCelda);
	}
	
	private void aumentaFilaColumna (int tamCelda) 
	{
		turnRight(normalRelativeAngleDegrees(45-getHeading()));
		ahead(Math.sqrt(2)*tamCelda);
	}
	
	private void aumentaFilaDColumna (int tamCelda) 
	{
		turnRight(normalRelativeAngleDegrees(135-getHeading()));
		ahead(Math.sqrt(2)*tamCelda);
	}
	
	private void disminuyeFilaAColumna (int tamCelda) 
	{
		turnRight(normalRelativeAngleDegrees(315-getHeading()));
		ahead(Math.sqrt(2)*tamCelda);
	}
	
	private void disminuyeFilaColumna (int tamCelda)
	{
		turnRight(normalRelativeAngleDegrees(225-getHeading()));
		ahead(Math.sqrt(2)*tamCelda);
	}


/**
* Este método se ejecutará cuándo se pulse el botón Pintar
*/
	public void onPaint(Graphics2D g) 
	{

		double filaInicial = 0, columnaInicial = 500.0;
		double filaFinal   = 550.0, columnaFinal = 200.0;

		
	    // Inicial
	    g.setColor(Color.GREEN);
	    g.fillRect((int)filaInicial,(int) columnaInicial, 15, 15);
	    
	    // DESTINO
	    g.setColor(new Color(0xff, 0x00, 0x00, 0x80));
	    g.fillRect((int) filaFinal,(int) columnaFinal, 15, 15);
		
	    
	    //Cuadrículas

	    
		g.setPaint(Color.white);

	    for (int i=0; i<fila;i++)
	    		g.drawLine(i*tamCelda+1, 0, i*tamCelda+1, filaPixels);
	    
	    for (int i=0; i<columna;i++)
	    		g.drawLine (0, i*tamCelda+1, filaPixels, i*tamCelda+1);
	}
}
