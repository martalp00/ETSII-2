package PiedraPapelTijeras;


import java.util.concurrent.*;


public class Mesa {
    //0 - piedra, 1 - papel, 2 - tijera\par
	private int N;
	public int piedra=0,papel=0,tijera=0;
	public int piedraj=0,papelj=0,tijeraj=0;
	public int resultado;
	public int gente=0;
	public boolean ya=false;

	public Mesa (int Jugadores) {
		N=Jugadores;
		System.out.println(" Mesa " + N);
	}

	public synchronized int nuevaJugada(int jug,int juego) throws InterruptedException {
		
		System.out.println(" jugador " + jug + " juega " + juego);
		if(gente!=2) {
			gente++;
			if(juego==0) {
				piedra++;
				piedraj=jug;
			}
			if(juego==1) {
				papel++;
				papelj=jug;
			}
			if(juego==2) {
				tijera++;
				tijeraj=jug;
			}
			//En realidad esto no sirve			
			while(!ya) {
				wait();				
			}
		}else {
			if(juego==0) {
				piedra++;
				piedraj=jug;
			}
			if(juego==1) {
				papel++;
				papelj=jug;
			}
			if(juego==2) {
				tijera++;
				tijeraj=jug;
			}	
			
			if(piedra==1 && tijera ==2) {
				System.out.println("termina juego, gana "+piedraj );
				resultado=piedraj;
			}else if(tijera==1 && papel==2) {
				System.out.println("termina juego, gana "+tijeraj );
				resultado=tijeraj;
			}else if(papel==1 && piedra==2){
				System.out.println("termina juego, gana "+papelj );
				resultado=papelj;
			}else{
				System.out.println("termina juego en empate " );
			}
			

			ya=true;
			piedra=0;
			piedraj=0;
			papel=0;
			papelj=0;
			tijera=0;
			tijeraj=0;
			gente = 0;
			notifyAll();
		}		
		return resultado;
	}

}


