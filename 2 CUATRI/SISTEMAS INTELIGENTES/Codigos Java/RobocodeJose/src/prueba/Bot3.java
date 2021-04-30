package prueba;

import static robocode.util.Utils.normalRelativeAngleDegrees;


import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import robocode.Robot;

/**
 * 
 */

/** 
 * @date 2018-03-22
 * 
 * Plantilla para la definiciÃ³n de un robot en Robocode
 *
 */
public class Bot3 extends Robot 
{
	//The main method in every robot
	public void run() {
		

		System.out.println("Iniciando ejecuciÃ³n del robot");
		
		// Nuestro robot serÃ¡ rojo
		setAllColors(Color.red);

		//DATOS QUE DEBEN COINCIDIR CON LOS DEL PROGRAMA main DE LA CLASE RouteFinder
		long semilla = 0;
		int nFil = 16;
		int nCol = 12;
		int nObst = 40;
		int tamCelda = 50;
		
		//Orientamos inicialmente el robot hacia arriba
		turnRight(normalRelativeAngleDegrees(0 - getHeading()));
		
		//A continuaciÃ³n nuestro robot girarÃ¡ un poco sobre sÃ­ mismo		
		int k = 0;
		while(k < 20){
			turnRight(90);
			k++;
		}
		
		Info camino = getCamino(nFil, nCol, nObst, semilla);
		
		if (camino != null) {
			//Usamos una pila para no tener que dar la vuelta a la lista enlazada
			//resultante que nos dará el camino
			Stack<Estado> pasos = new Stack<Estado>();
			
			while (camino != null) {
				pasos.push(camino.getEstado());
				camino = camino.getPadre();
			}
			
			Estado actual = pasos.pop();
			
			while (!pasos.isEmpty()) {
				Estado siguiente = pasos.pop();
				
				if (siguiente.getFila() == actual.getFila() + 1 && siguiente.getColumna() == actual.getColumna() + 1) {
					aumentaFilaColumna(tamCelda);
				} else if (siguiente.getFila() == actual.getFila() + 1 && siguiente.getColumna() == actual.getColumna() - 1) {
					aumentaFilaDColumna(tamCelda);
				} else if (siguiente.getFila() == actual.getFila() - 1 && siguiente.getColumna() == actual.getColumna() - 1) {
					disminuyeFilaColumna(tamCelda);
				} else if (siguiente.getFila() == actual.getFila() - 1 && siguiente.getColumna() == actual.getColumna() + 1) {
					disminuyeFilaAColumna(tamCelda);
				} else if (siguiente.getFila() == actual.getFila() + 1) {
					aumentaFila(tamCelda);
				} else if (siguiente.getFila() == actual.getFila() - 1) {
					disminuyeFila(tamCelda);
				} else if (siguiente.getColumna() == actual.getColumna() + 1) {
					aumentaColumna(tamCelda);
				} else {
					disminuyeColumna(tamCelda);
				}
				actual = siguiente;
			}
			//Informamos de que hemos alcanzado el objetivo
			System.out.println("Objetivo alcanzado");
			
			
		} else {
			//Informamos si no existe camino
			System.out.println("No hay camino valido hasta mi objetivo");
		}
			
	}
	
	//Usamos algoritmo A* con heuristico = distancia octal
	private static Info getCamino (int nFilas, int nColumnas, int nObstaculos, long semilla) {
		Problema prob = new Problema(nFilas, nColumnas, nObstaculos, semilla);
		
		//Indicamos donde esta la posicion final
		System.out.println("Voy hacia: (" + prob.getFinal()/prob.getColumnas() + "," + prob.getFinal()%prob.getColumnas() + ")");
		
		PriorityQueue<Info> abiertos = new PriorityQueue<Info>();
		HashMap<Estado, Info> arbol = new HashMap<Estado, Info>();
		
		Estado inicial = new Estado(prob);
		Info actual = new Info(inicial);
		
		abiertos.add(actual);
		arbol.put(inicial, actual);
		
		
		while (!abiertos.isEmpty()) {
			actual = abiertos.remove();
			
			if (actual.getEstado().finalP()) {
				return actual;
			} else {
				List<Estado> sucesores = actual.getEstado().sucesores();
				
				for (Estado est : sucesores) {
					Info nuevo = new Info(actual, est);
					if (!arbol.containsKey(est)) {
						abiertos.add(nuevo);
						arbol.put(est,nuevo);
					} else if (arbol.get(est).compareTo(nuevo) > 0) {
						abiertos.add(nuevo);
						arbol.put(est,nuevo);
					}
				}
			}
		}
		return null;
	}
	
	//Funciones de movimiento
	
	private void aumentaFila (int tamCelda) {
		turnRight(normalRelativeAngleDegrees(90-getHeading()));
		ahead(tamCelda);
	}
	
	private void disminuyeFila (int tamCelda) {
		turnRight(normalRelativeAngleDegrees(270-getHeading()));
		ahead(tamCelda);
	}
	
	private void aumentaColumna (int tamCelda) {
		turnRight(normalRelativeAngleDegrees(0-getHeading()));
		ahead(tamCelda);
	}
	
	private void disminuyeColumna (int tamCelda) {
		turnRight(normalRelativeAngleDegrees(180-getHeading()));
		ahead(tamCelda);
	}
	
	private void aumentaFilaColumna (int tamCelda) 
	{
		turnRight(normalRelativeAngleDegrees(45-getHeading()));
		ahead(Math.sqrt(2)*tamCelda);
	}
	
	private void aumentaFilaDColumna (int tamCelda) {
		turnRight(normalRelativeAngleDegrees(135-getHeading()));
		ahead(Math.sqrt(2)*tamCelda);
	}
	
	private void disminuyeFilaAColumna (int tamCelda) {
		turnRight(normalRelativeAngleDegrees(315-getHeading()));
		ahead(Math.sqrt(2)*tamCelda);
	}
	
	private void disminuyeFilaColumna (int tamCelda) {
		turnRight(normalRelativeAngleDegrees(225-getHeading()));
		ahead(Math.sqrt(2)*tamCelda);
	}
	
}
