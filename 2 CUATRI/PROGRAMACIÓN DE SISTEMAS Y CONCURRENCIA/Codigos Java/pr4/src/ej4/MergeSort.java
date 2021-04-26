package ej4;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MergeSort 
{
	private static List<Integer> crear_lista_aleatoria() 
	{
		Random r = new Random();
		List<Integer> lista = new ArrayList<Integer>();
		
		for(int i=0; i<100; i++)
		{
			lista.add(r.nextInt(100));
		}
		
		return lista;
	}

	public static void main(String[] args) 
	{
		try 
		{
			List<Integer> lista = new ArrayList<Integer>();
			lista = crear_lista_aleatoria();
			
			System.out.println("Inicial: ");
			System.out.println(lista);
			
			Nodo n1 = new Nodo(lista);
			n1.start();
			n1.join();
			
			System.out.println("Resultado: ");
			System.out.println(lista);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
