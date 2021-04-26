package Impresoras;

public class Principal {

	public static void main(String[] args) {
		Gestor g = new Gestor(3);
		Cliente[] c = new Cliente[10];
		for(int i=0; i<c.length; i++) {
			c[i]=new Cliente(i,g);
		}
		for(int i=0; i<c.length; i++) {
			c[i].start();
		}
	}
}
