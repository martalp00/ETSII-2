package restauranteSEM;

import java.util.concurrent.Semaphore;

public class Restaurante 
{
	private int N; //distancia maxima entre Cocinero1 y Cocinero3
	
	private Semaphore mutex = new Semaphore(1);
	private Semaphore cocinero1= new Semaphore(1);
	private Semaphore cocinero2 = new Semaphore(0);
	private Semaphore cocinero3= new Semaphore(0);
	private Semaphore pala = new Semaphore(1);
	
	private int psofrito = 0,parroz = 0,pagua = 0; //Recuento de las paelleras que usando cada cocinero
	
	public Restaurante(int tam)
	{		
		N = tam;
	}
	
	/**
	 * Cocinero1 espera en este método para poder empezar a hacer 
	 * el sofrito. Tiene que esperar si está alejado N paelleras sin agua de Cocinero3 y,
	 * opcionalmente, si la pala compartida está siendo utilizada.
	 */
	public void esperaHacerSofrito() throws InterruptedException
	{
		cocinero1.acquire();
		pala.acquire();
		//System.out.println(" 		Cocinero1 usa la pala"); //Compruebo que cocinero 1 y 3 hacen un buen uso de la pala, y no se solapan.
	}
	
	/**
	 * Cocinero1 hace el sofrito número num. Actualiza el recurso
	 * para informar a Cocinero2 y a Cocinero3.
	 * @param num
	 */
	public void finHacerSofrito(int num) throws InterruptedException
	{
		mutex.acquire();
		psofrito++;
		System.out.println("Cocinero1 hace sofrito en la paellera " + num);
		
		if((psofrito-pagua) < N) 
		{
			//System.out.println("										valor semph(c1) = "+cocinero1.availablePermits()); //Compruebo que el valor siempre sea 0 (de lo contrario el semaforo no seria binario)
			cocinero1.release();
		}
		if((psofrito-parroz) == 1)
		{
			//System.out.println("										valor semph(c2) = "+cocinero2.availablePermits());
			cocinero2.release();
		}
		
		//System.out.println(" 		Cocinero1 deja la pala");
		pala.release();
		mutex.release();
	}
	
	/**
	 * Cocinero2 espera en este método para poder echar el arroz a 
	 * una paellera. Debe esperar si no hay una paellera con sofrito y sin arroz.
	 */
	public void esperaEcharArroz() throws InterruptedException
	{
		cocinero2.acquire();
	}
	
	/**
	 * Cocinero2 ha puesto el arroz en la paellera número num. 
 	 * Actualiza el recurso para informar a Cocinero3.
	 * @param num
	 */
	public void finEcharArroz(int num) throws InterruptedException
	{
		mutex.acquire();
		parroz++;
		System.out.println("Cocinero2 ha echado el arroz en la paellera " + num);
		
		if(num < psofrito) 
		{
			//System.out.println("										valor semph(c2) = "+cocinero2.availablePermits());
			cocinero2.release();
			
		}
	
		if((parroz-pagua) == 1)
		{
			//System.out.println("										valor semph(c3) = "+cocinero3.availablePermits());
			cocinero3.release();
			
		}
		mutex.release();
	}
	
	/**
	 * Cocinero3 espera en este método para poder echar el agua.
	 * Espera si no hay una paellera con arroz que todavía no tenga agua
	 *  y, opcionalmente, si la pala no está libre.
	 */
	public void esperaEcharAgua() throws InterruptedException 
	{
		cocinero3.acquire();
		pala.acquire();
		//System.out.println(" 		Cocinero3 usa la pala");
	}
	
	/**
	 * Cocinero3 ha añadido el agua en la paellera número num. 
 	 * Actualiza el recurso para informar a Cocinero1 y a Cocinero2.
	 * @param num
	 */
	public void finEcharAgua(int num) throws InterruptedException
	{
		mutex.acquire();
		pagua++;
		System.out.println("Cocinero3 ha añadido el agua en la paellera " + num);
		
		if(num < parroz) 
		{
			//System.out.println("										valor semph(c3) = "+cocinero3.availablePermits());
			cocinero3.release();
		}
		
		if((psofrito-pagua) == N-1) //Cuando la distancia es 4 porque si c¡vale menos el ticket se lo da 鬠mismo
		{
			//System.out.println("										valor semph(c1) = "+cocinero1.availablePermits());
			cocinero1.release();
		}
		
		//System.out.println(" 		Cocinero3 deja la pala");
		pala.release();
		mutex.release();
	}
}