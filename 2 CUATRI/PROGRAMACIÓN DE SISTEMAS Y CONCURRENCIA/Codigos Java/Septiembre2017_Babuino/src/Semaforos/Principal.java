package Semaforos;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BabuinoSN[] id = new BabuinoSN[25];
		BabuinoNS[] di = new BabuinoNS[25];
		Cuerda2 p = new Cuerda2();
		
		for (int i = 0; i<id.length; i++){
			id[i] = new BabuinoSN(i,p);
		}
		for (int i = 0; i<di.length; i++){
			di[i] = new BabuinoNS(i,p);
		}
		
		for (int i = 0; i<id.length; i++){
			id[i].start();;
		}
		for (int i = 0; i<di.length; i++){
			di[i].start();
		}
	}

}
