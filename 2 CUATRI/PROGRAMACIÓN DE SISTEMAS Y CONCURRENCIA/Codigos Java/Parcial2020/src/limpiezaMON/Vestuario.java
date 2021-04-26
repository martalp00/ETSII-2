//Nombre y apellidos: Jose Mar�a Dom�nguez P�rez
//DNI: 77659482M
//Curso: 2do Ingenier�a de Software A

package limpiezaMON;

public class Vestuario { //Recurso
	
	private int N; //distancia máxima entre Antonio y David
	
	private boolean rosaEspera;
	private boolean davidEspera;
	private boolean antonioEspera;
	private boolean mopaCompartida;
	
	private int nVestNoSec;
	private int r, d, a; //vestuarios en los que se encuentra cada empleado (�ndices)
	
	public Vestuario(int tam)
	{
		rosaEspera = true;
		davidEspera = true;
		antonioEspera = false;
		mopaCompartida = false;
		N = tam;
		nVestNoSec = N;
		a=0;
		r=0;
		d=0;
	}
	
	/**
	 * Antonio espera en este método para poder empezar a desinfectar  
	 * un vestuario. Tiene que esperar si está alejado N vestuarios sin secar de David y,
	 * opcionalmente, si la mopa compartida está siendo utilizada.
	 */
	public synchronized void esperaDesinfectar() throws InterruptedException
	{
		while (antonioEspera) 
		{
			wait();
		}
		while (mopaCompartida)
		{
			wait();
		}
		mopaCompartida = true;
//		System.out.println("Antonio est� desinfectando el vestuario " +a);
//		System.out.println("(a:"+a+"; r:"+r+"; d:"+d+")");	
	}
	
	/**
	 * Antonio ha desinfectado el vestuario número num. Actualiza el recurso
	 * para informar de que ha terminado su tarea.
	 * @param num
	 */
	public synchronized void finDesinfectar (int num) throws InterruptedException 
	{
		boolean rosaEstabaEsperando = a==r;
		System.out.println("Antonio ha desinfectado el vestuario "+a);
		//System.out.println("Antonio finaliz� de desinfectar el vestuario " +a);
		nVestNoSec++;
		a++;
		//System.out.println("(a:"+a+"; r:"+r+"; d:"+d+")");
		//System.out.println("* Mopa disponible *");
		mopaCompartida = false;
		if(nVestNoSec>=N) 
		{
			antonioEspera = true;
			notify();
		}
		if(rosaEstabaEsperando) 
		{
			rosaEspera = false;
			notifyAll();
		}
	}

	/**
	 * Rosa espera en este método para poder limpiar 
	 * un vestuario. Debe esperar si no hay un vestuario desinfectado y no limpiado.
	 */
	public synchronized void esperaLimpiar() throws InterruptedException{
		while (rosaEspera) 
		{
			wait();
		}
//		System.out.println("Rosa est� limpiado el vestuario " +r);
//		System.out.println("(a:"+a+"; r:"+r+"; d:"+d+")");	
	}
	
	/**
	 * Rosa ha limpiado el vestuario número num. 
 	 * Actualiza el recurso para informar a David.
	 * @param num
	 */
	public synchronized void finLimpiar(int num) throws InterruptedException{
		boolean davidEstabaEsperando = r==d;
		System.out.println("Rosa ha limpiado el vestuario "+r);
		//System.out.println("Rosa finaliz� de limpiar el vestuario " +r);
		r++;
		//System.out.println("(a:"+a+"; r:"+r+"; d:"+d+")");
		if(r==a) 
		{
			rosaEspera = true;
			notify();
		}
		if(davidEstabaEsperando) 
		{
			davidEspera = false;
			notifyAll();
		}
	}

	/**
	 * David espera en este método para poder secar 
	 * un vestuario. Espera si no hay vestuario limpio no secado
	 *  y, opcionalmente, si la mopa no está libre.
	 */
	public synchronized void esperaSecar()throws InterruptedException
	{
		while (davidEspera) 
		{
			wait();
		}
		while (mopaCompartida) 
		{
			wait();
		}
		mopaCompartida = true;
		
		//System.out.println("David est� secando el vestuario " +d);
		//System.out.println("(a:"+a+"; r:"+r+"; d:"+d+")");	
	}
	
	/**
	 * David ha secado el vestuario número num. 
 	 * Actualiza el recurso para informar a sus compañeros que ha secado el vestuario.
	 * @param num
	 */
	public synchronized void finSecar(int num) throws InterruptedException
	{
		boolean antonioEstabaEsperando = N==nVestNoSec;
		System.out.println("David ha desinfectado el vestuario "+d);
		
		//System.out.println("David finaliz� de secar el vestuario " +d);
		nVestNoSec--;
		d++;
		//System.out.println("(a:"+a+"; r:"+r+"; d:"+d+")");
		//System.out.println("* Mopa disponible *");
		mopaCompartida = false;
		if(d==r) 
		{
			davidEspera = true;
			notify();
		}
		if(antonioEstabaEsperando) 
		{
			antonioEspera = false;
			notifyAll();
		}
	}	
}
