package guarderia;


public class Guarderia {
	
	private int nBebe=0;
	private int nAdulto=0;
	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public synchronized void entraBebe(int id) throws InterruptedException{
		while (nBebe+1 > 3*nAdulto) wait();
		nBebe++;
		System.out.println("Hay entrado un bebe. Hay bebe: "+nBebe+" Adulto: "+nAdulto);
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public synchronized void saleBebe(int id) throws InterruptedException{
		nBebe--;
		System.out.println("Hay salido un bebe. Hay bebe: "+nBebe+" Adulto: "+nAdulto);
		notifyAll();
	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public synchronized void entraAdulto(int id) throws InterruptedException{
		nAdulto++;
		System.out.println("Hay entrado un Adulto. Hay bebe: "+nBebe+" Adulto: "+nAdulto);
		notifyAll();
		
	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public synchronized void saleAdulto(int id) throws InterruptedException{
		while (nBebe>3*(nAdulto-1)) wait();
		nAdulto--;
		System.out.println("Hay salido un adulto. Hay bebe: "+nBebe+" Adulto: "+nAdulto);
	}

}
