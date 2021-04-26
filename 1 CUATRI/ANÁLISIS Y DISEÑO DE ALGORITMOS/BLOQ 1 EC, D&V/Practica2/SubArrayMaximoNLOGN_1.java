/**
 * 
 * @author ***** Indicar aqui el autor de la practica *******
 *
 */
public class SubArrayMaximoNLOGN extends SubArrayMaximo {

	public SolucionSubArrayMaximo resolver(int[] array) {
		SolucionSubArrayMaximo ssam = new SolucionSubArrayMaximo();
		 /** Implementar aqui una solucion mediante el metodo Divide y Venceras */
		int inf = 0;
		int sup = array.length;
		array = super.vectorDiferencias(array);
		suma(array, inf, sup);
		return ssam;
	}

	private SolucionSubArrayMaximo suma(int[] array, int inf, int sup) {
		// TODO Auto-generated method stub
		if(inf >= sup) {
			return new SolucionSubArrayMaximo(array[inf], inf, inf);
		}else {
			int medio = (inf+sup)/2;
			SolucionSubArrayMaximo s1 = suma(array, inf, medio);
			SolucionSubArrayMaximo s2 = suma(array, medio+1, sup);
			int max1 = array[medio], v1 = array[medio], j1 = medio;
			for(int i = medio-1; i >= inf; i--) {
				v1 += array[i];
				if(v1 > max1) {
					max1 = v1;
					j1 = i;
				}
			}
			int max2 = array[medio+1], v2 = array[medio+1], j2 = medio+1;
			for(int j = medio+2; j <= sup; j++) {
				v2 += array[j];
				if(v2 > max2) {
					max2 = v2;
					j2 = j;
				}
			}
			int max = max1+max2;
			if(s1.getSumaOptima() >= s2.getSumaOptima() && s1.getSumaOptima() >= max) {
				return s1;
			}else if(s2.getSumaOptima() >= s1.getSumaOptima() && s2.getSumaOptima() >= max) {
				return s2;
			}else {
				return new SolucionSubArrayMaximo(j1,j2,max);
			}
		}
			
	}
}
