package Monitores;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {
	
	
	
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
	Lock l = new ReentrantLock();
	
	private boolean llamaPizzero = false;
	Condition CllamaPizzero = l.newCondition();
	
	private boolean entregaPizza = false;
	Condition CentregaPizza = l.newCondition();
	
	private boolean hayPizza = true;
	Condition ChayPizza = l.newCondition();
	
	private boolean esperaPizza = true;
	Condition CesperaPizza = l.newCondition();
	
	
	private int n_trozos = 0;
	
	public void nuevaRacion(int id) throws InterruptedException{
		l.lock();
		try {
			while(!hayPizza) {
				ChayPizza.await();
			}
			
			if(n_trozos == 0) {
				hayPizza = false;
				llamaPizzero = true;
				CllamaPizzero.signal();
				System.out.println(id + " -> No hay pizza y se llama al pizzero");
				
				while(esperaPizza) {
					CesperaPizza.await();
				}
				esperaPizza = true;
				
			}	
			n_trozos--;
			System.out.println("Estudiante con id :" + id + " coge un trozo de pizza y quedan " + n_trozos);
		}finally {
			l.unlock();
		}
		
	}


	/**
	 * El pizzero entrega la pizza y espera hasta que le paguen para irse
	 * @throws InterruptedException 
	 */
	public void nuevoCliente() throws InterruptedException{
		l.lock();
		try {
			while(!llamaPizzero) {
				CllamaPizzero.await();
			}
			llamaPizzero = false;
			System.out.println("El pizzero coge la llamada y la lleva a domicilio");
			entregaPizza = true;
			CentregaPizza.signal();
			
		}finally {
			l.unlock();
		}
		
	}
	

/**
	 * El pizzero espera hasta que algún cliente le llama para hacer una pizza y
	 * llevársela a domicilio
	 * @throws InterruptedException 
	 */
	public void nuevaPizza() throws InterruptedException{

		l.lock();
		try {
			while(!entregaPizza) {
				CentregaPizza.await();
			}
			entregaPizza = false;
			System.out.println("El pizzero entrega la pizza y es pagado");
			n_trozos = 8;
			hayPizza = true;
			ChayPizza.signal();
			esperaPizza = false;
			CesperaPizza.signal();
		}finally {
			l.unlock();
		}
	}

}
