package FilosofosTenedores;

import java.util.Random;

public class Filosofo extends Thread{
	private static Random rnd = new Random();
	private int id;
	private Tenedor izq, dcha;
	private Sillas silla;
	
	public Filosofo(int id, Tenedor izq, Tenedor dcha, Sillas silla){
		this.id = id;
		this.izq = izq;
		this.dcha = dcha;
		this.silla = silla;
	}
	
	public void run(){
		try{
			while(true){
				//CUIDADO PORQUE CON LOS sleep NO BLOQUEA SIEMPRE Y NO SE VE EL BLOQUEO
				Thread.sleep(rnd.nextInt(10)); //pensando
				silla.cojoSilla(id);
				izq.cojoTenedor(id);
				dcha.cojoTenedor(id);
				Thread.sleep(rnd.nextInt(10)); //comiendo
				dcha.devuelvoTenedor(id);
				izq.devuelvoTenedor(id);
				silla.devuelvoSilla(id);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
