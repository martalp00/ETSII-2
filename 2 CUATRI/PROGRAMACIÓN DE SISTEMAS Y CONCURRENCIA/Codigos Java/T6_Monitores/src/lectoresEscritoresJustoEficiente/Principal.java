package lectoresEscritoresJustoEficiente;

public class Principal {
	public static void main(String[] args){
		final int NL = 10;
		final int NE = 3;
		
		GestorBD g = new GestorBD();
		
		Lector[] lec = new Lector[NL];
		Escritor[] esc = new Escritor[NE];
		
		for (int i = 0; i<NL; i++)
			lec[i] = new Lector(i, g);
		
		for (int i = 0; i<NE; i++)
			esc[i] = new Escritor(i, g);
		
		for (int i = 0; i<NL; i++){
			lec[i].start();
		}
		
		for (int i = 0; i<NE; i++){
			esc[i].start();
		}
		
		for (int i = 0; i<NL; i++){
			try {
				lec[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i<NE; i++){
			try {
				esc[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
