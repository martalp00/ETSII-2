//MARTA LÓPEZ PÉREZ 26260171J VERSION 0

package barcoSEM;

import java.util.Arrays;
import java.util.concurrent.*;

public class Meta 
{
	private int  N; 	//num veleros que participan
	private int[] ganados;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore velero = new Semaphore(1);
	private Semaphore listo = new Semaphore(0);
	private Semaphore acaba = new Semaphore(0);
	
	private int nlistos=0, llegameta=0;
	
	public Meta(int numVeleros)
	{
		N = numVeleros;//Numero de veleros que participan en las regatas
		ganados = new int[N];
		for (int i=0; i<ganados.length; i++)	ganados[i] = 0;//regatas que ha ganado cada velero
	}
	
	/*
	 * El velero id espera a que se pueda empezar la siguiente regata.
	 * El velero id indica que esta listo para la siguiente 
	 * regata y espera a que el arbitro de la salida.
	 */
	public  void esperaSalida(int id) throws InterruptedException
	{
		velero.acquire();
		mutex.acquire();
		
		nlistos++;
		System.out.println("El velero "+id+" esta listo para salir");
		
		if(nlistos == N)
		{
			//Hago esto para comprobar que los semaforos son binarios y no se me pasa ninguno
			//System.out.println("						valor semph(listo) = "+listo.availablePermits());
			listo.release();
		}
		
		if(nlistos < N)
		{
			//System.out.println("						valor semph(velero) = "+velero.availablePermits());
			velero.release();
		}
		
		mutex.release();
	}
	
	/*
	 * El velero id ha llegado a la meta. El ganador actualiza el array
	 * ganados.
	 * 
	 */
	public  void finalCarrera(int id) throws InterruptedException
	{
		acaba.acquire();
		mutex.acquire();
		
		nlistos--;
		System.out.println("El velero "+id+" ha llegado a la meta");
		
		llegameta++;
		if(llegameta == 1)
		{
			System.out.println("El velero "+id+" ha llegado el primero");
			ganados[id]++;	//si el velero id ha ganado se actualiza ganados[id]
		}
		
		if(nlistos == 0)	
		{
			//System.out.println("						valor semph(velero) = "+velero.availablePermits());
			velero.release();
		}
		if(nlistos > 0)	
		{
			//System.out.println("						valor semph(acaba) = "+acaba.availablePermits());
			acaba.release();
		}
		
		mutex.release();
		
	}
	/*
	 * El arbitro espera hasta que todos los veleros esten listos para
	 * salir. A continuacion, indica a todos que empieza la regata.
	 */
	public  void esperoTodosListos() throws InterruptedException
	{
		listo.acquire();
		mutex.acquire();
		
		System.out.println("Empieza la carrera!!!!");
		
		mutex.release();
	}
	/*
	 * El arbitro espera que hayan llegado todos los veleros. 
	 * A continuacion, les informa de que pueden hacer otra carrera.
	 */
	public  void esperoTodosTerminan() throws InterruptedException
	{
		mutex.acquire();
		//acaba.acquire();
		llegameta = 0;
		System.out.println("Todos han terminado!!!!");
		
		acaba.release();
		mutex.release();
	}
	
	public void resultados()
	{
		System.out.println("Resultados "+Arrays.toString(ganados));
	}
}
