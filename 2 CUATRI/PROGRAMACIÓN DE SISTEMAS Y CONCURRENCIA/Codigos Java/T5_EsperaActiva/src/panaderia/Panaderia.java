package panaderia;

public class Panaderia {
	
	private int[] turno;
	private boolean[] cogiendoTurno;
 	private int cont = 0;
	public Panaderia(int nclientes){
		turno = new int[nclientes];
		cogiendoTurno = new boolean[nclientes];
		for (int i=0; i<turno.length; i++){
			turno[i] = 0; //cliente i no quiere entrar en la panadería
		}
		for (int i=0; i<cogiendoTurno.length; i++){
			cogiendoTurno[i] = false;
		}
		
	}
	
	public void cogeTurno(int id){//entidad sin control de acceso
		cogiendoTurno[id] = true;
		int max = 0;
		for (int i = 0; i<turno.length; i++){
			if (max < turno[i]) max = turno[i];
		}
		turno[id] = max + 1;	
		cogiendoTurno[id] = false;	
	}
	
	public void esperoTurno(int id){
		for (int i = 0; i<turno.length; i++){
			while (cogiendoTurno[i]) Thread.yield();
			while (!meToca(id,i)) Thread.yield();
		}
	}
	
	public boolean meToca(int id,int i){
		//true sii le toca a id antes que a i
		if (turno[i]>0 && turno[i]<turno[id]) return false;
		else if (turno[i] == turno[id] && i < id) return false;//injusta
		else return true;
	}
	public void salgoPanaderia(int id){
		turno[id] = 0;
	}
	public void inc(){
		cont++;
	}
	
	public int contador(){
		return cont;
	}
	
	
	
}
