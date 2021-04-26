package Barbero;

//SOLUCIÃ“N 2 - EFICIENTE (numero minimo de espera.release() y espera.acquire()
import java.util.concurrent.*;
public class Barberia {
	//Atributos
	private int n=0;
	
	//SEMÃ�FOROS BINARIOS
	private Semaphore mutex = new Semaphore(1); //semaforo binario
	private Semaphore espera = new Semaphore(0); //semaforo binario
	
//	private boolean primera = true;
	
    public void nuevoCliente() throws InterruptedException {
    	mutex.acquire();
    	//SC
    	n++;
    	System.out.println("Llega un cliente");
    	//Solo lo despierta si lo ve dormido en la sala de espera (n == 0)
    	//No lo despierta si la sala estÃ¡ vacÃ­a pero el barbero estÃ¡ pelando (n == 1)
    	if (n==0){System.out.println("Despierta al barbero..."); espera.release();}
    	mutex.release();
    	
    }
    
    public void pelar() throws InterruptedException {
//    	if (primera) { espera.acquire(); primera = false;}
    	mutex.acquire();
    	//SC
    	n--;
    	if (n == -1) {System.out.println("Me voy a dormir"); 
    	 	//FORMA DE NO QUEDARSE BLOQUEADOS EN UN MUTEX
    		mutex.release();  //libero el mutex
    		espera.acquire(); //espero al cliente
    		mutex.acquire();  //vuelvo a coger el mutex
    	}
//    	int m = n;
    	System.out.println("Pelo a un cliente");
    	mutex.release();
 //   	if (m == 0){ System.out.println("Me voy a dormir"); espera.acquire();}
    }
}
