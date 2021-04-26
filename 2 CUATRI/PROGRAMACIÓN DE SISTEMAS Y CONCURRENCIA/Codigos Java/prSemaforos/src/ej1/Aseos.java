package ej1;

import java.util.concurrent.Semaphore;

public class Aseos
{
	private int numClientes = 0;
	private int limpia = 0;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore cliente = new Semaphore(1);
	private Semaphore equipo = new Semaphore(0);
	
	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando o
	 * está esperando para poder limpiar los aseos
	 * @throws InterruptedException 
	 */
	public void entroAseo(int id) throws InterruptedException
	{
		cliente.acquire();
		mutex.acquire();
		numClientes++;
		
		System.out.println("Llega el cliente " + id + " y entonces hay " + numClientes);
		
		cliente.release();
		mutex.release();
	}
	
	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * @throws InterruptedException 
	 */
	public void salgoAseo(int id) throws InterruptedException
	{
		cliente.acquire();
		mutex.acquire();
		numClientes--;
		
		System.out.println("											Sale el cliente " + id + " y ahora quedan " + numClientes);
		
		if(numClientes == 0) 
		{
			equipo.release();
		}
		else 
		{
			cliente.release();
		}
		mutex.release();
	}
	
	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos 
	 * CS: El equipo de trabajo está solo en los aseos, es decir, espera hasta que no
	 * haya ningún cliente.
	 * @throws InterruptedException 
	 */
    public void entraEquipoLimpieza() throws InterruptedException
    {
		equipo.acquire();
		mutex.acquire();
		limpia++;
		
		System.out.println("Entra el equipo de limpieza ");
		
		
		equipo.release();
		mutex.release();		
	}
    
    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos 
     * @throws InterruptedException 
	 * 
	 * 
	 */
    public void saleEquipoLimpieza() throws InterruptedException
    {
    	equipo.acquire();
		mutex.acquire();
		limpia--;
		
		System.out.println("											Sale el equipo de limpieza ");
		
		if(limpia == 0) 
		{
			cliente.release();
		}
		else 
		{
			equipo.release();
		}

		mutex.release();
	}
}
