package CadenaMontaje;

import java.util.Arrays;
import java.util.concurrent.*;
public class Cadena {
	
	private int N = 5;
	private int[] buffer = new int[N];

	private Semaphore hayHuecos = new Semaphore(1);
	private int numHuecos = N;
	//hayHuecos = 0 sii numHuecos = 0
	private Semaphore[] espera = new Semaphore[3];
	private int[] elem = new int[3];
	//espera[i] = 0 sii num[i] = 0
	private Semaphore mutex = new Semaphore(1);
	private int numTotal=0;
	public Cadena(){
		for (int i=0; i<buffer.length; i++)
			buffer[i]=-1; //la componente i está vacía
		for (int i = 0; i<3 ; i++){
			espera[i] = new Semaphore(0);
			elem[i] = 0;
		}
	}
	
	private int busca(int elem){
		//sabemos que elem está en buffer
		int i=0;
		while (buffer[i]!=elem) i++;
		return i;
	}
	
	public void nuevoProducto(int prod) throws InterruptedException{
		//productor
		hayHuecos.acquire();
		mutex.acquire();
		int i = busca(-1);
		buffer[i]=prod;
		System.out.println("El colocador ha puesto "+prod);
		System.out.println(Arrays.toString(buffer));
		numHuecos--;
		if (numHuecos>0) hayHuecos.release();
		elem[prod]++;
		if (elem[prod]==1) espera[prod].release();
		mutex.release();
	}
	
	public void extraeProducto(int id) throws InterruptedException{
		//consumidor id = 0,1,2
		espera[id].acquire();
		mutex.acquire();
		int i = busca(id);
		buffer[i]=-1;
		numTotal++;
		System.out.println("El empaquetador "+id+" ha cogido un elemento");
		System.out.println(Arrays.toString(buffer));
		System.out.println("Total empaquetados "+numTotal);
		elem[id]--;
		if (elem[id]>0) espera[id].release();
		numHuecos++;
		if (numHuecos==1) hayHuecos.release();
		mutex.release();	
	}
	
	

}

//colocador no puede almacenar si el buffer está lleno
//Empaquetador i no puede extraer si no hay datos i en buffer
