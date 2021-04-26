package barca;

public class Barca extends Thread
{
	private static final int C = 4; //Asiento de la barca
	private int nios = 0, nandroid = 0; //numero personas de cada tipo que se van subiendo
	private boolean lleno = false, finviaje=false;
	
	public Barca()	{}
	
	/**
	 * Un estudiante con mÃ³vil android llama a este mÃ©todo 
	 * cuando quiere cruzar el rÃ­o. Debe esperarse a montarse en la
	 * barca de forma segura, y a llegar a la otra orilla del antes de salir del
	 * mÃ©todo
	 * @param id del estudiante android que llama al mÃ©todo
	 * @throws InterruptedException
	 */
	public synchronized void android(int id) throws InterruptedException
	{
		while(lleno ||nandroid ==2 && nios==1|| nios == 3)	wait();
		nandroid++;
		System.out.println("Android " + id + " se ha subido a la barca. 	Android: " + nandroid + ", Iphone: " + nios);
		
		if(nandroid + nios == C)
		{
			lleno = true;
			finviaje = true;
			System.out.println("----------------------------------------------------------------------------------------");

			notifyAll();
		}
		else
		{
			while(!finviaje)	wait();
		}
		
		nandroid--;
		System.out.println("Android " + id + " se ha bajado de la barca. 	Android: " + nandroid + ", Iphone: " + nios);

		if(nandroid + nios == 0)
		{
			System.out.println();
			System.out.println("********FIN VIAJE********");
			System.out.println();
			
			lleno = false;
			finviaje = false;
			notifyAll();
		}
	}
	
	/**
	 * Un estudiante con mÃ³vil android llama a este mÃ©todo 
	 * cuando quiere cruzar el rÃ­o. Debe esperarse a montarse en la
	 * barca de forma segura, y a llegar a la otra orilla del antes de salir del
	 * mÃ©todo
	 * @param id del estudiante android que llama al mÃ©todo
	 * @throws InterruptedException
	 */

	public synchronized void iphone(int id) throws InterruptedException
	{
		while(lleno || nandroid ==1 && nios==2 || nandroid == 3)	wait();
		nios++;
		System.out.println("Iphone " + id + " se ha subido a la barca. 	Android: " + nandroid + ", Iphone: " + nios);
		
		if(nandroid + nios == C)
		{
			lleno = true;
			finviaje = true;
			System.out.println("----------------------------------------------------------------------------------------");

			notifyAll();
		}
		else
		{
			while(!finviaje)	wait();
		}
		
		nios--;
		System.out.println("Iphone " + id + " se ha bajado de la barca. 	Android: " + nandroid + ", Iphone: " + nios);

		if(nandroid + nios == 0)
		{
			System.out.println();
			System.out.println("********FIN VIAJE********");
			System.out.println();
			
			lleno = false;
			finviaje = false;
			notifyAll();
		}
	}
}