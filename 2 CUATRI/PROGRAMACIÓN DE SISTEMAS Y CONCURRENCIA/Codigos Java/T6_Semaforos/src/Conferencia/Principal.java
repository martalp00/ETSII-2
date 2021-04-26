package Conferencia;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SalaConferencias sala = new SalaConferencias(10);
		Conferenciante conf = new Conferenciante(sala);
		Asistente[] asis = new Asistente[25];
		for (int i=0; i<asis.length; i++){
			asis[i] = new Asistente(i,sala);
		}
		conf.start();
		for (int i=0; i<asis.length; i++){
			asis[i].start();
		}
	}

}
