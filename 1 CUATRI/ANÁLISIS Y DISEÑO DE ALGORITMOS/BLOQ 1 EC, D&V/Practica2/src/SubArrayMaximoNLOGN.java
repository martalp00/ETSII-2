/**
 * 
 * @author ***** MARTA LOPEZ PEREZ *******
 *
 */
public class SubArrayMaximoNLOGN extends SubArrayMaximo 
{
	public SolucionSubArrayMaximo resolver(int[] array) 
	{
		SolucionSubArrayMaximo ssam = new SolucionSubArrayMaximo();
		
		if(array.length == 1)	//caso base: si el array inicial tiene 1 solo elemento compra y vende el mismo dia
		{
			ssam.setDiaCompra(0);
            ssam.setDiaVenta(0);
            ssam.setSumaOptima(0);
		}
		else
		{
			int[] A = SubArrayMaximo.vectorDiferencias(array);
			ssam = divideYvenceras(A, 0, A.length-1);
		}

		return ssam;
	}

	private SolucionSubArrayMaximo divideYvenceras(int[] a, int inf, int sup) 
	{
		if(inf>=sup)	return new SolucionSubArrayMaximo(a[inf],inf,sup);
		else
		{	
			int med = (inf+sup) / 2;
			SolucionSubArrayMaximo izq = divideYvenceras(a, inf, med);		//caso esta a la izquierda
			SolucionSubArrayMaximo der = divideYvenceras(a, med+1, sup);	//caso esta a la derecha

			//caso esta entre las dos partes
			int mayor_i = 0, recuento_i = 0, compras = 0;
			for(int i=med; i>=inf; i--)		//posicion del mayor beneficio de la parte izq
			{
				recuento_i += a[i];
				if(recuento_i > mayor_i)
				{
					//System.out.println(med);
					mayor_i = recuento_i;
					compras = i;
					//System.out.println("-" + compras);
				}
			}

			int mayor_j = 0, recuento_j = 0, ventas = 0;
															
			for(int j=med+1; j<=sup; j++)		//posicion del mayor beneficio de la parte der
			{
				recuento_j += a[j];
				if(recuento_j >= mayor_j)
				{
					mayor_j = recuento_j;
					ventas = j+1;
				}

				
			}
			
			int	beneficio_izqder = mayor_i + mayor_j;

			// comparamos los 3 beneficios y devolvemos el mayor
			if(izq.getSumaOptima() > der.getSumaOptima() && izq.getSumaOptima() > beneficio_izqder) return izq;
			else if(der.getSumaOptima() > izq.getSumaOptima() && der.getSumaOptima() > beneficio_izqder) return der;
			else return new SolucionSubArrayMaximo(beneficio_izqder, compras, ventas);
		}
	}
}