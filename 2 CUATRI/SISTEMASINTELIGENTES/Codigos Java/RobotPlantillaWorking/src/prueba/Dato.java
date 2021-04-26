package prueba;

public class Dato implements Comparable<Dato> 
{
	private Dato padre;
	private Estado estado;
	private int aux;
	
	public Dato (Estado e)
	{
		padre = null; 
		estado = e;
		aux = 0;
	}
	
	public Dato(Dato p, Estado e) 
	{
		padre = p;
		estado = e;
		aux = p.aux + e.coste(p.estado);
	}
	
	//Ordenamos la PriorityQueue
	public int compareTo(Dato inf) 
	{
		return Integer.compare(this.getFN(), inf.getFN());
	}
	
	public int getFN() 
	{
		return aux + estado.heuristico();
	}
	
	public int getAux () 
	{
		return aux;
	}
	
	public Dato getPadre () 
	{
		return padre;
	}
	
	public Estado getEstado () 
	{
		return estado;
	}
}