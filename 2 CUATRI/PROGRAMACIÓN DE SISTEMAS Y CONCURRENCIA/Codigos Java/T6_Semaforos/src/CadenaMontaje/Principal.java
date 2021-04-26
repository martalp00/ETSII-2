package CadenaMontaje;

public class Principal {
	public static void main(String[] args){
		Cadena c = new Cadena();
		Colocador col = new Colocador(c);
		Empaquetador[] emp = new Empaquetador[3];
		for (int i = 0; i<emp.length; i++)
			emp[i] = new Empaquetador(c,i);
		col.start();
		for (int i = 0; i<emp.length; i++)
			emp[i].start();
	}

}
