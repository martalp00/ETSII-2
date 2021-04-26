// ALUMNO: Marta Lopez Perez
// GRUPO: 2A

import java.util.*;


public class TableroSudoku implements Cloneable {
	
	// constantes relativas al n� de filas y columnas del tablero
	protected static final int MAXVALOR=9; 
	protected static final int FILAS=9; 
	protected static final int COLUMNAS=9; 
							 
	protected static Random r = new Random();
	
	protected int celdas[][]; // una celda vale cero si est\u00E1 libre.
	
	public TableroSudoku() {
		celdas = new int[FILAS][COLUMNAS]; //todas a cero.
	}

	// crea una copia de su par\u00E1metro
	public TableroSudoku(TableroSudoku uno) {
		TableroSudoku otro = (TableroSudoku) uno.clone();
		this.celdas = otro.celdas;
	}

	// crear un tablero a parir de una configuraci\u00D3n inicial (las celdas vac\u00EDas
	// se representan con el caracter ".".
    public TableroSudoku(String s) {
    	this();
    	if(s.length() != FILAS*COLUMNAS) {
    		throw new RuntimeException("Construcci\u00D3n de sudoku no v\u00E1lida.");
    	} else {
    		for(int f=0;f<FILAS;f++) 
				for(int c=0;c<COLUMNAS;c++) {
					Character ch = s.charAt(f*FILAS+c);
					celdas[f][c] = (Character.isDigit(ch) ? Integer.parseInt(ch.toString()) : 0 ); 
				}		
		}		
    }

	
	/* Realizar una copia en profundidad del objeto
	 * @see java.lang.Object#clone()
	 */
	public Object clone()  {
		TableroSudoku clon;
		try {
			clon = (TableroSudoku) super.clone();
			clon.celdas = new int[FILAS][COLUMNAS]; 
			for(int i=0; i<celdas.length; i++)
				System.arraycopy(celdas[i], 0, clon.celdas[i], 0, celdas[i].length);
		} catch (CloneNotSupportedException e) {
			clon = null;
		}	
		return clon;
	}
	
	/* Igualdad para la clase
	 * @see java.lang.Object#equals()
	 */
	public boolean equals(Object obj) {
		if (obj instanceof TableroSudoku) {
			TableroSudoku otro = (TableroSudoku) obj;
			for(int f=0; f<FILAS; f++)
				if(!Arrays.equals(this.celdas[f],otro.celdas[f]))
					return false;
			return true;		
		} else
			return false;
	}
	


	public String toString() {
		String s = "";

		for(int f=0;f<FILAS;f++) {
			for(int c=0;c<COLUMNAS;c++) 
				s += (celdas[f][c]==0 ? "." : String.format("%d",celdas[f][c])); 
		}
		return s;	
	}


	// devuelva true si la celda del tablero dada por fila y columna est\u00E1 vac\u00EDa.
	protected boolean estaLibre(int fila, int columna) {
		return celdas[fila][columna] == 0;
	}
	
	// devuelve el n�mero de casillas libres en un sudoku.
	protected int numeroDeLibres() {
		int n=0;
	    for (int f = 0; f < FILAS; f++) 
	        for (int c = 0; c < COLUMNAS; c++)
	        	if(estaLibre(f,c))
	        		n++;
	    return n;
	}
	
	protected int numeroDeFijos() {
		return FILAS*COLUMNAS - numeroDeLibres();
	}

	// Devuelve true si @valor ya esta en la fila @fila.
	protected boolean estaEnFila(int fila, int valor) 
	{		// A completar por el alumno
		
		boolean encontrado = false;
		int i=0;

		while(i<COLUMNAS && !encontrado) 
		{
			if(celdas[fila][i] == valor) 
			{
				encontrado = true;
			}
			i++;
		}
		return encontrado;
	}    

	// Devuelve true si @valor ya esta en la columna @columna.
	protected boolean estaEnColumna(int columna, int valor) 
	{		// A completar por el alumno
		
		boolean encontrado = false;
		int i=0;

		while(i<FILAS && !encontrado) 
		{
			if(celdas[i][columna] == valor) 
			{
				encontrado = true;
			}
			i++;
		}
		return encontrado;
	}    
	

	// Devuelve true si @valor ya esta en subtablero al que pertence @fila y @columna.
	protected boolean estaEnSubtablero(int fila, int columna, int valor) 
	{		// A completar por el alumno	
		
		// Localizamos el subtablero e inicializamos para recorrer posiciones
		int iniF = (int) (fila / 3)*3;
		int iniC = (int) (columna / 3)*3;
		int i = iniF;
		int j = iniC;
		
		boolean encontrado = false;
		
		while(i <= iniF + 2 && !encontrado)
		{
			while(j <= iniC + 2 && !encontrado)
			{
				if(celdas[i][j] == valor)
				{
					encontrado = true;   
				}    
				j++;
			}
			j = iniC;   
			i++; 
		}  
		return encontrado; 	
	}    

	
	// Devuelve true si se puede colocar el @valor en la @fila y @columna dadas.
	protected boolean sePuedePonerEn(int fila, int columna, int valor) 
	{		// A completar por el alumno
	
		boolean sePuede = false;
		
		if( !estaEnSubtablero(fila, columna, valor) && !estaEnFila(fila, valor) && !estaEnColumna(columna, valor) )
		{
			sePuede = true;
		}
		
		return sePuede;
	}
	
	

	protected void resolverTodos(List<TableroSudoku> soluciones, int fila, int columna) 
	{		// A completar por el alumno

		if (numeroDeFijos() == COLUMNAS*FILAS) 
		{
			soluciones.add(new TableroSudoku(this));	// Añade la solucion encontrada
		} 
		else if (estaLibre(fila,columna)) 
		{
			for(int i=1; i<=MAXVALOR; i++) 	// Prueba cada valor en todas las posiciones
			{
				if (sePuedePonerEn(fila, columna, i)) 
				{
					celdas[fila][columna] = i;

					if(fila < FILAS - 1 &&  columna == COLUMNAS -1)
					{
						resolverTodos(soluciones, fila + 1, 0);
					}
					else
					{
						resolverTodos(soluciones, fila, columna + 1);
					}    

					celdas[fila][columna] = 0;
				}
			}
		} 
		else 
		{
			if(fila < FILAS - 1 &&  columna == COLUMNAS -1)
			{
				resolverTodos(soluciones, fila + 1, 0);
			}
			else
			{
				resolverTodos(soluciones, fila, columna + 1);
			} 
		}
	}
	

	public List<TableroSudoku> resolverTodos() {
        List<TableroSudoku> sols  = new LinkedList<TableroSudoku>();
        resolverTodos(sols, 0, 0);
		return sols;
	}
	
	
	public static void main(String arg[]) {
		TableroSudoku t = new TableroSudoku( 
			    ".4....36263.941...5.7.3.....9.3751..3.48.....17..62...716.9..2...96.......312..9.");
		List<TableroSudoku> lt = t.resolverTodos();
		System.out.println(t);
		System.out.println(lt.size());
		for(Iterator<TableroSudoku> i= lt.iterator(); i.hasNext();) {
			TableroSudoku ts = i.next(); 
			System.out.println(ts);
			
		}

	}
	
	
}
