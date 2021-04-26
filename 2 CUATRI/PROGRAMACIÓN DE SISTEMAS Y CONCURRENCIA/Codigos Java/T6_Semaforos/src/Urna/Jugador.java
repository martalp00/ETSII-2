package Urna;

public class Jugador extends Thread{
	private int id;
	private UrnaSemaforos arb;
	private int n;
	public Jugador(int n,int id,UrnaSemaforos arb){
		this.id = id;
		this.arb = arb;
		this.n = n;
	}

	
	public void run(){
		boolean esRoja=false;
		do{
			try {
				esRoja=arb.jugar(id);
				n--;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		} while (!esRoja && n>0);
		if (n==0) System.out.println("El ganador es "+id);
		else System.out.println("El jugador "+id+" sale del juego");
	}
	
}
