package restauranteMON;

public class Restaurante
{ 
	private int distancia; 									//distancia maxima entre Cocinero1 y Cocinero3
	private int sofrito=0, arroz=0;
	private boolean pala = true;
	//private Condition cesperaHacerSofrito = l.newCondition();

	public Restaurante(int tam)
	{
		distancia = tam;
	}
	
	/**
	 * Cocinero1 espera en este método para poder empezar a hacer 
	 * el sofrito. Tiene que esperar si está alejado N paelleras sin agua de Cocinero3 y,
	 * opcionalmente, si la pala compartida está siendo utilizada.
	 */
	public synchronized void esperaHacerSofrito() throws InterruptedException 
	{
		while(distancia == 0 || !pala)	wait();
		pala = false;
	}
	
	/**
	 * Cocinero1 hace el sofrito número num. Actualiza el recurso
	 * para informar a Cocinero2 y a Cocinero3.
	 * @param num
	 */
	public synchronized void finHacerSofrito(int num) throws InterruptedException
	{
		System.out.println("Cocinero 1 hace sofrito en la paellera " + num);
		distancia--;
		pala = true;
		sofrito++;
		notifyAll();
	}
	
	/**
	 * Cocinero2 espera en este método para poder echar el arroz a 
	 * una paellera. Debe esperar si no hay una paellera con sofrito y sin arroz.
	 */
	public synchronized void esperaEcharArroz() throws InterruptedException
	{
		while(sofrito == 0)	wait();
	}
	
	/**
	 * Cocinero2 ha puesto el arroz en la paellera número num. 
 	 * Actualiza el recurso para informar a Cocinero3.
	 * @param num
	 */
	public synchronized void finEcharArroz(int num) throws InterruptedException
	{
		System.out.println("Cocinero 2 echa arroz en la paellera " + num);
		sofrito--;
		arroz++;
		if(arroz == 1)	notifyAll();
	}
	
	/**
	 * Cocinero3 espera en este método para poder echar el agua.
	 * Espera si no hay una paellera con arroz que todavía no tenga agua
	 *  y, opcionalmente, si la pala no está libre.
	 */
	public synchronized void esperaEcharAgua() throws InterruptedException 
	{
		while(arroz == 0 || !pala)	wait();
		pala = false;
	}
	
	/**
	 * Cocinero3 ha añadido el agua en la paellera número num. 
 	 * Actualiza el recurso para informar a Cocinero1 y a Cocinero2.
	 * @param num
	 */
	public synchronized void finEcharAgua(int num) throws InterruptedException
	{
		System.out.println("Cocinero 3 echa agua en la paellera " + num);
		arroz--;
		distancia++;
		pala = true;
		notifyAll();
	}
}
