package prueba;

import java.util.List;
import java.util.LinkedList;

public class Estado {
	private int fila;
	private int columna; 
	static Problema problema;
	
	public Estado (Problema prob) {
		fila = prob.getInicial()/prob.getColumnas();
		columna = prob.getInicial()%prob.getColumnas();
		problema = prob;
	}
	
	public Estado (int f, int c) {
		fila = f;
		columna = c;
	}
	
	public List<Estado> sucesores () {
		List<Estado> res = new LinkedList<Estado>();
		
		for (int f = fila - 1; f <= fila + 1; f++) {
			for (int c = columna - 1; c <= columna + 1; c++) {
				if (sePuedeMover(f,c,fila,columna)) {
					res.add(new Estado(f, c));
				}
			}
		}
		return res;
	}
	
	//Comprueba posiciones libres y validas, para ver si se puede mover desde
	//fila,columna a f,c
	private boolean sePuedeMover (int f, int c, int fila, int columna) {
		boolean res = false;
		int[][] malla = problema.getMatriz();
		
		if (f >= 0 && f < problema.getFilas() && c >= 0 && c < problema.getColumnas() && (f != fila || c != columna)) {
			if (malla[f][c] != 1) {
				if (f == fila - 1 && c == columna - 1) {
					res = malla[fila-1][columna] != 1 && malla[fila][columna-1] != 1;
				} else if (f == fila - 1 && c == columna + 1) {
					res = malla[fila-1][columna] != 1 && malla[fila][columna+1] != 1;
				} else if (f == fila + 1 && c == columna - 1) {
					res = malla[fila][columna-1] != 1 && malla[fila+1][columna] != 1;
				} else if (f == fila + 1 && c == columna + 1) {
					res = malla[fila][columna+1] != 1 && malla[fila+1][columna] != 1;
				} else {
					res = true;
				}
			}
		}
		
		return res;
	}
	
	public int coste (Estado est) {
		int res;
		
		if (this.fila != est.fila && this.columna != est.columna) {
			res = 142;
		} else {
			res = 100;
		}
		
		return res;
	}
	
	//Distancia octal
	public int heuristico () {
		int vFilas = Math.abs(fila - problema.getFinal()/problema.getColumnas());
		int vColumnas = Math.abs(columna - problema.getFinal()%problema.getColumnas());
		
		return 142*Math.min(vFilas, vColumnas) + 100*(Math.max(vFilas, vColumnas) - Math.min(vFilas, vColumnas));
	}
	
	public boolean equals (Object o) {
		return (o instanceof Estado) && (((Estado) o).fila) == this.fila && (((Estado) o).columna) == this.columna;
	}
	
	public int hashCode () {
		int hash = 17;
		hash = 31*hash + Integer.hashCode(fila);
		hash = 31*hash + Integer.hashCode(columna);
		
		return hash;
	}
	
	public boolean finalP () {
		return fila == problema.getFinal()/problema.getColumnas() && columna == problema.getFinal()%problema.getColumnas();
	}
	
	public int getFila () {
		return fila;
	}
	
	public int getColumna () {
		return columna;
	}
	
	public Problema getProblema () {
		return problema;
	}
	
}
