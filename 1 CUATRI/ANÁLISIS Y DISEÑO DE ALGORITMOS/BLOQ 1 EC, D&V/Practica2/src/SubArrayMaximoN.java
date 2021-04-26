/**
 * 
 * @author ***** MARTA LOPEZ PEREZ *******
 *
 */
public class SubArrayMaximoN extends SubArrayMaximo 
{
	public SolucionSubArrayMaximo resolver(int[] array) 
	{
		SolucionSubArrayMaximo ssam = new SolucionSubArrayMaximo();
		//int[] A = SubArrayMaximo.vectorDiferencias(array);

		int compra = -1, venta = -1, beneficio = -1;	// -1 para que se vea bien si falla en algun momento
		int menor = array[0], recuento = 0, i = 0;
		
		while(i<array.length)
		{
			if(array[i] < menor)
			{
				menor = array[i];
				compra = i;
			}
			
			recuento = array[i] - menor;
			if(recuento >= beneficio)
			{
				beneficio = recuento;
				venta = i;
			}
			i++;
		}

		ssam.setDiaCompra(compra);
        ssam.setDiaVenta(venta);
		ssam.sumaOptima = beneficio;
		
		return ssam; 	
	}
}
