package PiedraPapelTijeras;


import java.util.concurrent.*;


public class Mesa {
    //0 - piedra, 1 - papel, 2 - tijera\par
	private int N;
	public int piedra=0, papel=0, tijera=0;
	public int piedraj=0, papelj=0, tijeraj=0;
	public Semaphore b = new Semaphore(0);
	public Semaphore mutex = new Semaphore(1);
	public int resultado = -1;
	public int gente = 0;

	public Mesa (int Jugadores) {
		N=Jugadores;
		System.out.println("Mesa " + N);
	}

	public int nuevaJugada(int jug,int juego) throws InterruptedException {
		mutex.acquire();
		System.out.println("Jugador " + jug + " juega " + juego);
		
		//Van a entrar las dos primeras personas
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
			mutex.release();
			b.acquire();
			
		}else {	//Entra la tercera persona
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
			
			//Mostrar los resultados			
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

			b.release(2);
			piedra=0;
			piedraj=0;
			papel=0;
			papelj=0;
			tijera=0;
			tijeraj=0;
			gente = 0;
			mutex.release();

		}	
		return resultado;
	}
}




