package Urna;

import java.util.*;
import java.util.concurrent.*;

public class UrnaSemaforos {

	private int numBolas;// numero de bolas
	private Random r = new Random();
	private int roja;
	// Rellena aquí
	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore nuevaJugada = new Semaphore(0, true);
	private Semaphore siguiente = new Semaphore(1, true);
	private boolean actualizado;
	private int quieroJugar;
	private boolean[] heJugado;
	private int jugadores;

	public UrnaSemaforos(int num) {
		// Rellena aquí
		roja = r.nextInt(num) + 1;
		numBolas = num;
		System.out.println("El numero de bolas en la urna es " + numBolas);
		System.out.println("**La bola roja es :" + roja);
		quieroJugar = num;
		actualizado = true;
		heJugado = new boolean[numBolas];
		for (int i = 0; i < numBolas; i++) {
			heJugado[i] = false;
		}
		jugadores = numBolas;

	}

	public boolean jugar(int id) throws InterruptedException {
		// Rellena aquí
		mutex.acquire();
		if (heJugado[id]) {
			mutex.release();
			nuevaJugada.acquire();
			mutex.acquire();
			if (!actualizado) {
				numBolas -= 1;
				roja = r.nextInt(numBolas) + 1;
				System.out.println("El numero de bolas en la urna es " + numBolas);
				System.out.println("**La bola roja es :" + roja);
				actualizado = true;
			}
			quieroJugar++;
			if (quieroJugar < numBolas)
				nuevaJugada.release();
			if (quieroJugar == numBolas) {
				siguiente.release();
				for (int i = 0; i < jugadores; i++) {
					heJugado[i] = false;
				}
			}
		}
		mutex.release();
		boolean resultado;
		siguiente.acquire();
		mutex.acquire();
		if (quieroJugar == roja) {

			if (numBolas != 1) {

				System.out.println("\tJugador  " + id + " saca bola " + quieroJugar + " = " + roja
						+ " ( roja ) y  pierde la partida");
			} else {

				System.out.println("\tJugador  " + id + " es el �nico jugador");
			}

			resultado = true;

		} else {
			System.out.println(
					"\tJugador  " + id + " saca bola " + quieroJugar + " != " + roja + " (roja) y sigue jugando");
			resultado = false;

		}
		quieroJugar--;
		heJugado[id] = true;
		if (quieroJugar == 0) {
			System.out.println("-------------TODOS LOS JUGADORES HAN TERMINADO------------");
			nuevaJugada.release();
			actualizado = false;
		} else {
			siguiente.release();
		}
		mutex.release();
		return resultado;

	}
}
