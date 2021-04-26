package prueba;

import java.util.*;

public class Problema 
{
	private int nFilas;
	private int nColumnas;
	private int nObstaculos;
	private long semilla;
	private int matriz[][];
	private int posInicial;
	private int posFinal; 
	
	/*	Para generar los números se siguen estos pasos:
	 * 	1º Se obtiene un numero entre 0 y (nFilas*nColumnas - 1)
	 * 	2º Luego la posición n esta en la fila=(n/nColumnas) y la columna=(n%nColumnas)
	 * 	por tanto posInicial y posFinal hay que descodificarlos de dicha manera
	 */

	public Problema (int nF, int nC, int nO, long s) 
	{
		nFilas = nF;
		nColumnas = nC;
		nObstaculos = nO;
		semilla = s;
		matriz = new int[nFilas][nColumnas];
		
		Random rnd = new Random();
		rnd.setSeed(semilla);
		SortedSet<Integer> elegidos = new TreeSet<Integer>();
		
		int i = 0;
		int pos;
		
		while (i < nObstaculos) 
		{
			pos = rnd.nextInt(nFilas*nColumnas);
			
			if (!elegidos.contains(pos)) 
			{
				int fila = pos/nColumnas;
				int columna = pos%nColumnas;
				
				matriz[fila][columna] = 1; // obstaculo
				elegidos.add(pos);
				i++;
			}
		}
		
		boolean encontrado = false;
		while (!encontrado) 
		{
			pos = rnd.nextInt(nFilas*nColumnas);
			if (!elegidos.contains(pos)) 
			{
				posInicial = pos;
				elegidos.add(pos);
				encontrado = true;
			}
		}
		
		encontrado = false;
		while (!encontrado) 
		{
			pos = rnd.nextInt(nFilas*nColumnas);
			if (!elegidos.contains(pos)) 
			{
				posFinal = pos;
				elegidos.add(pos);
				encontrado = true;
			}
		}
	}
	//getters
	public int getFilas () 
	{
		return nFilas;
	}
	
	public int getColumnas () 
	{
		return nColumnas;
	}
	
	public int getObstaculos () 
	{
		return nObstaculos;
	}
	
	public long getSemilla () 
	{
		return semilla;
	}
	
	public int[][] getMatriz () 
	{
		return matriz;
	}
	
	public int getInicial ()
	{
		return posInicial;
	}
	
	public int getFinal () 
	{
		return posFinal;
	}
}
