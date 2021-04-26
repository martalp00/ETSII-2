package Monitores;

import java.util.concurrent.locks.*;

public class Mesa2 {
	
	
	
	/**
	 * 
	 * @param id
	 * El estudiante id quiere una ración de pizza. 
	 * Si hay una ración la coge y sigue estudiante.
	 * Si no hay y es el primero que se da cuenta de que la mesa está vacía
	 * llama al pizzero y
	 * espera hasta que traiga una nueva pizza. Cuando el pizzero trae la pizza
	 * espera hasta que el estudiante que le ha llamado le pague.
	 * Si no hay pizza y no es el primer que se da cuenta de que la mesa está vacía
	 * espera hasta que haya un trozo para él.
	 * @throws InterruptedException 
	 * 
	 */
	
	private int trozos = 0;
	
	private Lock l = new ReentrantLock();
	
	
	private boolean esperaPizza = true;
	private Condition cEsperaPizza = l.newCondition();
	
	private boolean esperaLlamada = true;
	private Condition cLlamada = l.newCondition();
	
	private boolean hayPizza = true;
	private Condition cHayPizza = l.newCondition();
	
	private boolean esperoDinero = true;
	private Condition cEsperoDinero = l.newCondition();
	
	
	
	
	public  void nuevaRacion(int id) throws InterruptedException{
		l.lock();	//entramos en region critica
		
		try {
			
			while(!hayPizza) {
				cHayPizza.await();
			}
			
			if(trozos == 0) {
				hayPizza = false;
				
				System.out.println("el estudiante "+id+ " llama al pizzero");
				esperaLlamada = false;
				cLlamada.signal();
				
				while(esperaPizza) {
					cEsperaPizza.await();
				}
				
				esperaPizza = true;
				System.out.println("Paga al pizzero");
				esperoDinero = false;
				cEsperoDinero.signal();
				System.out.println("Han traido nueva pizza");
				hayPizza = true;
				cHayPizza.signalAll();
			}
			
			
			trozos--;
			System.out.println("el estudiante "+id+ " se come un trozo y quedan "+trozos);
			
		}finally {
			l.unlock();
		}
	}


	/**
	 * El pizzero entrega la pizza y espera hasta que le paguen para irse
	 * @throws InterruptedException 
	 */
	public  void nuevoCliente() throws InterruptedException{
		l.lock();
		
		try {
			
			while(esperaLlamada) {
				cLlamada.await();
			}
			esperaLlamada = true;
			
			
		}finally {
			l.unlock();
		}
		
	}
	

/**
	 * El pizzero espera hasta que algún cliente le llama para hacer una pizza y
	 * llevársela a domicilio
	 * @throws InterruptedException 
	 */
	public  void nuevaPizza() throws InterruptedException{
		l.lock();
		
		try {
			System.out.println("el pizzero hace una nueva pizza");
			trozos = 8;
			esperaPizza = false;
			cEsperaPizza.signal();
			while(esperoDinero) {
				cEsperoDinero.await();
			}
			
			
		}finally {
			l.unlock();
		}
	}

}
