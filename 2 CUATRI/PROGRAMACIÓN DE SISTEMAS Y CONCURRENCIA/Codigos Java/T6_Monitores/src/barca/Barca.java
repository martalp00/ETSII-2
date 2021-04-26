package barca;

public class Barca extends Thread{
	private static final int C = 4;
	private int nIPhone = 0;
	private int nAndroid = 0;
	private boolean lleno = false;
	private boolean finViaje = false;
	
	public Barca(){
	}
	
	/**
	 * Un estudiante con mÃ³vil android llama a este mÃ©todo 
	 * cuando quiere cruzar el rÃ­o. Debe esperarse a montarse en la
	 * barca de forma segura, y a llegar a la otra orilla del antes de salir del
	 * mÃ©todo
	 * @param id del estudiante android que llama al mÃ©todo
	 * @throws InterruptedException
	 */
	public synchronized void android(int id) throws InterruptedException{
		while (lleno || nAndroid ==2 && nIPhone==1 || nIPhone==3) 
				wait();
		nAndroid++;
		System.out.println("Android "+id+" se ha subido. And: "+nAndroid+" Iph: "+ nIPhone);
		if (nAndroid+nIPhone == C){
			lleno = true;
			
			finViaje = true;
			notifyAll();
		} else {
			while(!finViaje) wait();
		}
		nAndroid--;
		System.out.println("Android "+id+" se ha bajado. And: "+nAndroid+" Iph: "+ nIPhone);
		if (nAndroid+nIPhone == 0){
			System.out.println("*****************************************************");
			finViaje = false;
			lleno = false;
			notifyAll();
		}
	
	}
	
	/**
	 * Un estudiante con mÃ³vil android llama a este mÃ©todo 
	 * cuando quiere cruzar el rÃ­o. Debe esperarse a montarse en la
	 * barca de forma segura, y a llegar a la otra orilla del antes de salir del
	 * mÃ©todo
	 * @param id del estudiante android que llama al mÃ©todo
	 * @throws InterruptedException
	 */

	public synchronized void iphone(int id) throws InterruptedException{
		while (lleno || nAndroid ==1 && nIPhone==2 || nAndroid==3) 
			wait();
	nIPhone++;
	System.out.println("IPhone "+id+" se ha subido. And: "+nAndroid+" Iph: "+ nIPhone);
	if (nAndroid+nIPhone == C){
		lleno = true;
		
		finViaje = true;
		notifyAll();
	} else {
		while(!finViaje) wait();
	}
	nIPhone--;
	System.out.println("Android "+id+" se ha bajado. And: "+nAndroid+" Iph: "+ nIPhone);
	if (nAndroid+nIPhone == 0){
		System.out.println("*****************************************************");
		finViaje = false;
		lleno = false;
		notifyAll();
	}

		
	}
}

//Un estudiante no se sube a la barca hasta que hay sitio y la
//la configuración es segura
//2 iphones 2 android, 4 iphones o 4 Android

//Un estudiante no se baja de la barca hasta que no ha terminado
//el viaje

//NOTA: el último estudiante en subir hace de conductor
