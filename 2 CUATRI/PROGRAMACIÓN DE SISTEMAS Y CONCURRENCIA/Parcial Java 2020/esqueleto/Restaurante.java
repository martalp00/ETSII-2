package restaurante;


public class Restaurante { //Recurso
	
	private int N; //distancia máxima entre Cocinero1 y Cocinero3
	
	public Restaurante(int tam){
		
		N = tam;
	}
	
	/**
	 * Cocinero1 espera en este método para poder empezar a hacer 
	 * el sofrito. Tiene que esperar si está alejado N paelleras sin agua de Cocinero3 y,
	 * opcionalmente, si la pala compartida está siendo utilizada.
	 */
	public void esperaHacerSofrito() throws InterruptedException {
		
	}
	
	/**
	 * Cocinero1 hace el sofrito número num. Actualiza el recurso
	 * para informar a Cocinero2 y a Cocinero3.
	 * @param num
	 */
	public void finHacerSofrito(int num) throws InterruptedException{
		
	}
	
	/**
	 * Cocinero2 espera en este método para poder echar el arroz a 
	 * una paellera. Debe esperar si no hay una paellera con sofrito y sin arroz.
	 */
	public void esperaEcharArroz() throws InterruptedException{
		
	}
	
	/**
	 * Cocinero2 ha puesto el arroz en la paellera número num. 
 	 * Actualiza el recurso para informar a Cocinero3.
	 * @param num
	 */
	public void finEcharArroz(int num) throws InterruptedException{
		
	}
	
	/**
	 * Cocinero3 espera en este método para poder echar el agua.
	 * Espera si no hay una paellera con arroz que todavía no tenga agua
	 *  y, opcionalmente, si la pala no está libre.
	 */
	public void esperaEcharAgua() throws InterruptedException {}
	
	/**
	 * Cocinero3 ha añadido el agua en la paellera número num. 
 	 * Actualiza el recurso para informar a Cocinero1 y a Cocinero2.
	 * @param num
	 */
	public void finEcharAgua(int num) throws InterruptedException{
		
	}
}
