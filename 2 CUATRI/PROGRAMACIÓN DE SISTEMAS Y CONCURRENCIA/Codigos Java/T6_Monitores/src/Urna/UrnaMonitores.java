package Urna;

import java.util.*;

public class UrnaMonitores {

	private int numBolas;// numero de bolas
	private Random r = new Random();
	private int roja;
	private int quieroJugar;
	private boolean actualizado;
	private boolean[] heJugado;
	private int jugadores;
	// Rellena aquí

	public UrnaMonitores(int num) {
		// Rellena aquí
		System.out.println("EJERCICIO DE MONITORES");
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

	public synchronized boolean jugar(int id) throws InterruptedException {
		// Rellena aquí
		boolean resultado;
		while (heJugado[id]) {
			wait();
		}
		if (!actualizado) {
			numBolas -= 1;
			roja = r.nextInt(numBolas) + 1;
			quieroJugar = numBolas;
			System.out.println("El numero de bolas en la urna es " + numBolas);
			System.out.println("**La bola roja es :" + roja);
			actualizado = true;
		}

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
			actualizado = false;
			for (int i = 0; i < jugadores; i++) {
				heJugado[i] = false;
			}
			System.out.println("-------------TODOS LOS JUGADORES HAN TERMINADO------------");
			notifyAll();
		}
		return resultado;

	}

}
