//MARTA LÓPEZ PÉREZ 26260171J VERSION 0

package barcoMON;

import java.util.Arrays;
import java.util.concurrent.*;

public class Meta 
{
	private int  N;
	private int[] ganados;
	
	private int veleros = 0, llegameta = 0;
	private boolean lleno = false, fincarrera = false;
	
	public Meta(int numVeleros)
	{
		N = numVeleros;//Numero de veleros que participan en las regatas
		ganados = new int[N];
		for (int i=0; i<ganados.length; i++)
			ganados[i] = 0;//regatas que ha ganado cada velero
	}
	
	/*
	 * El velero id espera a que se pueda empezar la siguiente regata.
	 * El velero id indica que esta listo para la siguiente 
	 * regata y espera a que el arbitro de la salida.
	 */
	public synchronized void esperaSalida(int id) throws InterruptedException
	{
		while(lleno || fincarrera)	wait();
		
		veleros++;
		System.out.println("El velero "+id+" esta listo para salir");
		
		if(veleros == N)
		{
			lleno = true;
		}
		notifyAll();
	}
	
	/*
	 * El velero id ha llegado a la meta. El ganador actualiza el array
	 * ganados.
	 * 
	 */
	public synchronized void finalCarrera(int id) throws InterruptedException
	{
		while(llegameta == N)	wait();
		
		System.out.println("El velero "+id+" ha llegado a la meta");
		llegameta++;
		
		if(llegameta == 1)
		{
			System.out.println("El velero "+id+" ha llegado el primero");
			ganados[id]++;	//si el velero id ha ganado se actualiza ganados[id]
		}
		veleros--;
		
		if(veleros == 0)
		{
			lleno = false; 
			fincarrera = true;
		}
		notifyAll();
		
	}
	/*
	 * El arbitro espera hasta que todos los veleros esten listos para
	 * salir. A continuacion, indica a todos que empieza la regata.
	 */
	public synchronized void esperoTodosListos() throws InterruptedException
	{
		while(!lleno)	wait();
		System.out.println("Empieza la carrera!!!!");
		llegameta=0;
		notifyAll();
	}
	/*
	 * El arbitro espera que hayan llegado todos los veleros. 
	 * A continuacion, les informa de que pueden hacer otra carrera.
	 */
	public synchronized void esperoTodosTerminan() throws InterruptedException
	{
		while(!fincarrera)	wait();
		System.out.println("Todos han terminado!!!!");
		//lleno=false;
		fincarrera=false;
		notifyAll();
	}
	
	public void resultados()
	{
		System.out.println("Resultados "+Arrays.toString(ganados));
	}

}
