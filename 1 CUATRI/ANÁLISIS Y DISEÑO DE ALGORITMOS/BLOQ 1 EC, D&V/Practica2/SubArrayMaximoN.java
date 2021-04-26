/**
 * 
 * @author ***** Indicar aqui el autor de la practica *******
 *
 */
public class SubArrayMaximoN extends SubArrayMaximo {
	
	public SolucionSubArrayMaximo resolver(int[] array) {
		
		SolucionSubArrayMaximo ssam = new SolucionSubArrayMaximo();
		
        
		int min = array[0]; 
	       int beneficioMax = 0; 
	       int beneficio = 0;
	       int compra = 0; 
	       int venta = 0; 

	      
	       for (int i = 1; i < array.length; i++) {
	           
	           if (array[i] < min) {
	               min = array[i];
	               compra = i;
	           }
	           beneficio = array[i] - min;
	          
	           if (beneficio > beneficioMax) {
	               beneficioMax = beneficio;
	               venta = i;
	           }
	       }
		
		ssam.setDiaCompra(compra);
		ssam.setDiaVenta(venta);
		ssam.sumaOptima = beneficioMax;
		
		return ssam;
	}
	
}
