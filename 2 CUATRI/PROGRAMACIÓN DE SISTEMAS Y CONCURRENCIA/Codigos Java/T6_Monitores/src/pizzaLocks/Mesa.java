package pizzaLocks;

import java.util.concurrent.locks.*;
public class Mesa {
	
	private Lock l = new ReentrantLock(true);
	private int numRac = 0;//inicialmente vacío
	
	private boolean esperoPizza = true;
	private Condition cEsperoPizza = l.newCondition();
	
	private boolean esperaLlamada = true;
	private Condition cEsperaLlamada = l.newCondition();
	
	private boolean hayPizza = true;
	private Condition cHayPizza = l.newCondition();
	
	private boolean esperoDinero = true;
	private Condition cEsperoDinero = l.newCondition();
	
	
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
	public  void nuevaRacion(int id) throws InterruptedException{
		l.lock();
		try{
			while (!hayPizza) cHayPizza.await();
			if (numRac==0){
				hayPizza = false;
				System.out.println("No hay pizza, llamo pizzero. Estudiante "+id);
				esperaLlamada = false;
				cEsperaLlamada.signal();		
				
				while (esperoPizza) cEsperoPizza.await();
				esperoPizza = true;
				
				esperoDinero=false;
				cEsperoDinero.signal();
				System.out.println("Pago pizzero");
				hayPizza=true; 
				cHayPizza.signalAll();
			}	
			numRac--;
			System.out.println("Estudiante "+id+" come pizza "+numRac);
			
		} finally{
			l.unlock();
		}	
	}


	/**
	 * El pizzero entrega la pizza y espera hasta que le paguen para irse
	 * @throws InterruptedException 
	 */
	public  void nuevoCliente() throws InterruptedException{
		l.lock();
		try{
			while (esperaLlamada) cEsperaLlamada.await();
			esperaLlamada = true;
			
			
			
		} finally{
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
		try{
			System.out.println("Pizzero lleva nueva pizza");
			numRac = 5;
			esperoPizza = false;
			cEsperoPizza.signal();
			System.out.println("Pizzero espero dinero");
			while (esperoDinero) cEsperoDinero.await();
			esperoDinero = true;
			
		} finally{
			l.unlock();
		}
		
	}
}

//CS-Est1: Un estudiante que encuentra la bandeja vacía llama al 
// pizzero y espera a que le traiga la pizza
//CS-Est2: Si se ha llamado al pizzero un nuevo estudiante tiene
//que esperar que coma el que llamó
//CS-Pizzero1: Espera hasta que lo llaman
//CS-Pizzero2: Espera hasta que le pagan

