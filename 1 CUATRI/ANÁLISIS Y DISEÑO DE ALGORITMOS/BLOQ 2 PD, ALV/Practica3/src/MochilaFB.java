import static java.lang.Math.pow;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author ***** Jose A. Onieva ******* Asumimos que: a) Todos los items tienen
 *         un valor >=1 b) W>0
 */

public class MochilaFB extends Mochila
{

	public SolucionMochila resolver(ProblemaMochila pm) 
	{
		SolucionMochila sm = new SolucionMochila();
		int[] w = pm.getPesos();
		int[] v = pm.getValores();
		int[] u = pm.getUnidades();
		List <Integer> w2 = new ArrayList<>();
		List <Integer> v2 = new ArrayList<>();
		
		int[] res = new int[u.length];

/*		for(int i = 0; i < res.length; i++)		// inicializamos el resultado
		{
			res[i] = 0;
		}

		for(int i = 0; i < res.length; i++)		// inicializamos el resultado
		{
			System.out.println(res[i]);
		}
*/
		int mejor_peso = 0;
		int mejor_valor = 0;
		int peso, valor;
		
		int ntotal = 0;
		
		for(int i = 0; i < u.length ; i++)
		{
			ntotal += u[i];

			for(int j = 0; j < u[i] ; j++)
			{
				w2.add(w[i]);
				v2.add(v[i]);
			}
		}
/*		int b=0;
		while(b< w2.size())			// IMPRIMIR LOS VALORES DE W2 (COMPROBAR)
		{
			System.out.print("W2 = " + w2.get(b));
			System.out.println();
			b++;
		}
		b=0;
		while(b< v2.size())			// IMPRIMIR LOS VALORES DE V2 (COMPROBAR)
		{
			System.out.print("V2 = " + v2.get(b));
			System.out.println();
			b++;
		}
*/		
		int[] res2 = new int[ntotal];

		for (int i = 0; i < pow(2, ntotal); i++) 
		{
			int j = ntotal - 1; 
			peso = 0;
			valor = 0;

			while (res2[j] != 0 && j > 0) 
			{
				res2[j] = 0;
				j--;
			}
			res2[j] = 1;

		/*	for(int x = 0; x < ntotal ; x++)		// IMPRIMIR COMBINACIONES
			{
				System.out.print(res2[x]);
			}
			System.out.println();
		*/
			for (int k = 0; k < res2.length; k++) 
			{
				if (res2[k] == 1) 
				{
					peso += w2.get(k);
					valor += v2.get(k);
				}
			}

			if (valor > mejor_valor && peso <= pm.getPesoMaximo()) 
			{
				//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				mejor_valor = valor;
				mejor_peso = peso;
				sm.setSumaPesos(peso);
				sm.setSumaValores(valor);
				
				int cont = 0;
				for(int l = 0; l < res.length; l++)
				{
					res[l] = 0;
					for(int m = 0; m < u[l]; m++)
					{
						if(res2[cont] == 1)
						{
							res[l]++;
						}
						//System.out.print("pos " + cont + " -> " + res2[cont]);
						cont++;
					}
					//System.out.print("res " + l + " -> " + res[l] + " ");
					//System.out.println();
				}
				//System.out.println(cont);
				sm.setSolucion(ArrayUtils.toArray(res));
			}
		}
		return sm;
	}
}
