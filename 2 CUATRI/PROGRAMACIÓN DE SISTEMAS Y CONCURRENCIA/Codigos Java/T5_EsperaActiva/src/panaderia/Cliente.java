package panaderia;

public class Cliente extends Thread{
	private int id;
	private Panaderia pan;
	
	public Cliente(int id,Panaderia pan){
		this.id = id;
		this.pan = pan;
	}
	
	public void run(){
		
		for (int i=0; i<20; i++){
			pan.cogeTurno(id);//preProt
			pan.esperoTurno(id);//preProt: espero a que me toque el turno
			
			pan.inc();	//seccion crítica
			
			pan.salgoPanaderia(id); //PostProt
			//sncid
		}
	}
	
}
