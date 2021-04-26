/**
 * 
 * @author ***** MARTA LOPEZ PEREZ *******
 *
 */
public class SubArrayMaximoN2 extends SubArrayMaximo 
{
	public SolucionSubArrayMaximo resolver(int[] array) 
	{
		SolucionSubArrayMaximo ssam = new SolucionSubArrayMaximo();
		
		int compra = -1, venta = -1, beneficio = -1;

		for(int i=0; i<array.length; i++)
		{
			for(int j=i+1; j<array.length; j++)
			{
				int calculo = array[j]-array[i];
				if(calculo > beneficio)
				{
					beneficio = calculo;
					compra = i;
					venta = j;
				}
			}
		}
		
		ssam.setDiaCompra(compra);
        ssam.setDiaVenta(venta);
		ssam.sumaOptima = beneficio;
		
		return ssam;
	}
	
}
