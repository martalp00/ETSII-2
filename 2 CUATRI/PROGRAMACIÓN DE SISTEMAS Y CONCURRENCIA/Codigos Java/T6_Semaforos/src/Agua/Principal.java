package Agua;

public class Principal {

	public static void main(String[] str){
		GestorAgua gestor = new GestorAgua();
		Hidrogeno[] h = new Hidrogeno[20];
		Oxigeno[] o = new Oxigeno[10];
		for (int i = 0; i<h.length; i++){
			h[i] = new Hidrogeno(i,gestor);
		}
		for (int i = 0; i<o.length; i++){
			o[i] = new Oxigeno(i,gestor);
		}
		for (int i = 0; i<h.length; i++){
			h[i].start();
		}
		for (int i = 0; i<o.length; i++){
			o[i].start();
		}
		
	}
}
