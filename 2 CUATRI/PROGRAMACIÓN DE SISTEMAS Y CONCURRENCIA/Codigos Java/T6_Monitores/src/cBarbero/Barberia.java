package cBarbero;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barberia{
	private Lock l = new ReentrantLock(true); //Creamos el Lock
	
	private Condition cbLibre = l.newCondition(), //Creamos las condiciones
					  csOcupada = l.newCondition(),
			          cpAbierta = l.newCondition(),
			          cCiclo = l.newCondition();
	
	private boolean bLibre = false, sOcupada = false, pAbierta = false; //Vbles. asociadas a las condiciones

	//El cliente llama a este metodo para cortarse el pelo
	public void cortar(int id) throws InterruptedException{
		try{
			l.lock();
			System.out.println("Cliente " + id + " esperando al barbero");
			//CS-Cliente 1: Tiene que esperar a que el barbero esté libre
			while (!bLibre) 
				cbLibre.await();
			bLibre = false;      //El barbero ya no está libre

			System.out.println("Cliente " + id + " se ha sentado");
			//Avisa al barbero de que la silla está ocupada
			sOcupada = true; 	
			csOcupada.signal(); 

			System.out.println("Cliente " + id + " se esta pelando");
			//CS-Cliente 2: Tiene que esperar a que el barbero le abra la puerta
			while (!pAbierta) 
				cpAbierta.await();
			pAbierta = false;   //Cierra la puerta al salir
			
			System.out.println("Cliente " + id + " cierra la puerta");
			//Avisa de que ha terminado el ciclo (la puerta vuelve a estar cerrada)
			cCiclo.signal();	
		} finally {
			l.unlock();
		}
	}

	public void siguiente() throws InterruptedException{
		try{
			l.lock();
			System.out.println("El barbero avisa de que esta libre");
			//Avisa de que esta libre 
			bLibre = true; 		
			cbLibre.signal();	

			System.out.println("El barbero espera a que el cliente se siente");
			//CS-Barbero: Se espera a que haya un cliente sentado en la silla
			while (!sOcupada)   
				csOcupada.await();
			sOcupada = false;   
			
		}finally{
			l.unlock();
		}
	}
	
	public void finPelar() throws InterruptedException{
		try{
			l.lock();
			System.out.println("El barbero termina de pelar al cliente y le abre la puerta");
			//Avisa de que la puerta esta abierta
			pAbierta = true;   
			cpAbierta.signal();
			
			System.out.println("El barbero espera a que el cliente salga y cierre la puerta");
			//CS-Barbero 2: Se espera mientras la puerta esté abierta. La cierra el cliente al salir
			while (pAbierta)
				cCiclo.await();

		}finally{
			l.unlock();
		}
	}
}