package FilosofosTenedores;

public class Principal {

	public static void main(String[] args) {
		final int N = 5;
		
		Sillas sillas = new Sillas();
		
		Tenedor [] tenedores = new Tenedor[5];
		for (int i=0; i<tenedores.length; i++){
			tenedores[i] = new Tenedor(i);
		}
		
		Filosofo [] filosofos = new Filosofo[5];
		for (int i=0; i<filosofos.length; i++){
			filosofos[i] = new Filosofo(i,tenedores[i],tenedores[(i+1)%N],sillas);
		}

		for (int i=0; i<N; i++){
			filosofos[i].start();
		}
		
		for (int i=0; i<N; i++){
			try {
				filosofos[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
