package huertoMON;

public class Huerto //Recurso
{ 
	private int distancia;
	private int numHoyos  = 0;
	private int numSemillas=0;
	private boolean hayPala = true;
	
	public Huerto(int tam)
	{
		distancia = tam;
	}
	
	public synchronized void esperaHacerHoyo() throws InterruptedException 
	{
		while (distancia==0 || !hayPala) wait();
		hayPala = false;
	}
	
	public synchronized void finHacerHoyo(int num) throws InterruptedException
	{
		System.out.println("1.David ha cavado el hoyo "+num);
		distancia--;
		hayPala = true;
		numHoyos++;
		notifyAll();
	}
	
	public synchronized void esperaPonerSemilla() throws InterruptedException
	{
		while (numHoyos==0) wait();
	}
	public synchronized void finPonerSemilla(int num) throws InterruptedException
	{
		System.out.println("2.Juan ha puesto una semilla en el hoyo "+num);
		numHoyos--;
		numSemillas++;
		if (numSemillas==1) notifyAll();
		
	}
	public synchronized void esperaEcharTierra() throws InterruptedException
	{
		while (numSemillas == 0|| !hayPala) wait();
		hayPala = false;
	}
	public synchronized void finEcharTierra(int num) throws InterruptedException
	{	
		System.out.println("3.Fran ha tapado el hoyo "+num);
		numSemillas--;
		distancia++;
		hayPala = true;
		notifyAll();
	}
}
