package BCitaV2;

public class Principal {

	public static void main(String[] args) {
		Cita cita = new Cita();
		H1 h1 = new H1(cita);
		H2 h2 = new H2(cita);
		
		h1.start();
		h2.start();
		
	}

}
