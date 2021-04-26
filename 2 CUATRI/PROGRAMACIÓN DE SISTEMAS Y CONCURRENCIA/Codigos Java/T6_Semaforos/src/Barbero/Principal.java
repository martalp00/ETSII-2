package Barbero;

public class Principal {
	public static void main(String[] args){
        //Creamos la barberia
		Barberia b = new Barberia(); 
       
		//Creamos el barbero
        Barbero barbero = new Barbero(b); 
        
        //Creamos el entorno, en el que los clientes van entrando a la barber�a
        Entorno entorno = new Entorno(b); 
        
        entorno.start();
        barbero.start();
            
        try {
        	entorno.join();
			barbero.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
