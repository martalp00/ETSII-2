package prueba;

public class Info implements Comparable<Info> {
	
	private Info padre;
	private Estado estado;
	private int gn;
	
	public Info (Estado e) {
		padre = null; 
		estado = e;
		gn = 0;
	}
	
	public Info(Info p, Estado e) {
		padre = p;
		estado = e;
		gn = p.gn + e.coste(p.estado);
	}
	
	//compareTo para que se ordene adecuadamente la PriorityQueue
	public int compareTo(Info inf) {
		return Integer.compare(this.getFN(), inf.getFN());
	}
	
	public int getFN() {
		return gn + estado.heuristico();
	}
	
	public int getGN () {
		return gn;
	}
	
	public Info getPadre () {
		return padre;
	}
	
	public Estado getEstado () {
		return estado;
	}
	
}