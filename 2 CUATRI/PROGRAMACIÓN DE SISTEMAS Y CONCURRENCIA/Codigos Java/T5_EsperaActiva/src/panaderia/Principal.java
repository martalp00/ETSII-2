package panaderia;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int N = 3;
		Panaderia pan = new Panaderia(N);
		Cliente[] c = new Cliente[N];
		for (int i=0; i<c.length; i++){
			c[i] = new Cliente(i,pan);
		}
		for (int i=0; i<c.length; i++){
			c[i].start();
		}
		for (int i=0; i<c.length; i++){
			try {
				c[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Clientes: "+pan.contador());
	}

}
