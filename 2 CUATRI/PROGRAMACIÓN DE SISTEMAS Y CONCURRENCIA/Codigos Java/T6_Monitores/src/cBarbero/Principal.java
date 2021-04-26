package cBarbero;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Barberia barberia = new Barberia();
		
		//N clientes
		Cliente [] clientes = new Cliente[10];
		for (int i = 0; i< 10; i++){
			clientes[i] = new Cliente(barberia,i);
			clientes[i].start();
		}
		
		Barbero barbero = new Barbero(barberia);
		barbero.start();
	}

}
