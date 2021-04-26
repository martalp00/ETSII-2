package Urna;

public class Principal {

	public static void main(String[] args){
		int NBolas = 4;
		UrnaSemaforos arb = new UrnaSemaforos(NBolas);
		Jugador[] jugador = new Jugador[NBolas];
		for (int i = 0; i<jugador.length; i++)
			jugador[i] = new Jugador(NBolas,i,arb);
		
		for (int i = 0; i<jugador.length; i++)
			jugador[i].start();
	}
}
