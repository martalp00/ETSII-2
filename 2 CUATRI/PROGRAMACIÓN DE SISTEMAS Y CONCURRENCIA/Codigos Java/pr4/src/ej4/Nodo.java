package ej4;

import java.util.List;
import java.util.ArrayList;

public class Nodo extends Thread 
{
	List<Integer> lista = new ArrayList<Integer>();
	
	public Nodo(List<Integer> l) 
	{
		lista = l;
		if(lista == null) throw new IllegalArgumentException();
	}
	
	private void mezcla(List<Integer> l1, List<Integer> l2)
	{
		int i1 = 0, i2 = 0;
		
		lista.clear();
		
		while ((i1 < l1.size()) && (i2 < l2.size())) 
		{
			int n1 = l1.get(i1);
			int n2 = l2.get(i2);
			
			if(n1 <= n2)
			{
				lista.add(n1);
				i1++;
			}
			else
			{
				lista.add(n2);
				i2++;
			}
		}
		
		int tam1 = l1.size();		
		int tam2 = l2.size();
		
		while(i1 < tam1 || i2 < tam2)
		{
			if( i1 < tam1 )
			{
				lista.add(l1.get(i1));
				i1++;
			}
			else if( i2 < tam2 )
			{
				lista.add(l2.get(i2));
				i2++;
			}
		}
		
		/*		 OTRO MODO PONIENDO LOS WHILE DIRECTAMENTE PERO NO SÉ SI ESTARIA BIEN AUNQUE EL RESULTADO DE CORRECTO
 
		while(i1 < tam1)
		{
				lista.add(l1.get(i1));
				i1++;
		}
		while(i2 < tam2)
		{
				lista.add(l2.get(i2));
				i2++;
		}
		
 		*/
	}
	
	public void run()
	{
		try 
		{
			int tam = lista.size();
			
			if (tam > 1) 
			{
				List<Integer> l1 = new ArrayList<Integer>();
				List<Integer> l2 = new ArrayList<Integer>();
				
				int mitad = tam/2;
				
				l1.addAll(lista.subList(0, mitad));	// mitad -1
				l2.addAll(lista.subList(mitad, tam));
				
				Nodo n1 = new Nodo(l1);
				Nodo n2 = new Nodo(l2);
				
				n1.start();
				n2.start();
				
				n1.join();
				n2.join();

				mezcla(l1, l2);
			}
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
}
