
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author ***** Indicar aqui el autor de la practica *******
 *
 */
public class MochilaAV extends Mochila 
{
	public SolucionMochila resolver(ProblemaMochila pm) 
	{
		SolucionMochila sm = new SolucionMochila();

		int[] w = pm.getPesos();
		int[] v = pm.getValores();
		int[] u = pm.getUnidades();
		ArrayList <Integer> res = new ArrayList<>();
		//List <Integer> v2 = new ArrayList<>();

		int[] densidad = new int[u.length];

		for(int i = 0; i < u.length; i++)
		{
			densidad[i] = v[i] / w[i];
		}

		int menor = 0;

		//densidad = ordenar(densidad[0], densidad);

		for(int i = 0; i < densidad.length; i++)
		{
			int menorT = 10000;
			if(densidad[i] > menor && densidad[i] < menorT)
			{
				menor = densidad[i];
				menorT = densidad[i];
			}

			if(i == densidad.length -1)
			{
				res.add(menor);
				i = 0;
			}
		}

		for(int i = 0; i < res.size(); i++)
		{
			System.out.println(res.get(i) + ", ");
		}

		System.out.println( "aaaaaaaaaaaaaaaaaaaa ");




		//sm.setSumaPesos(peso);
		//sm.setSumaValores(valor);
		sm.setSolucion(res);
		return sm;
	}
}
