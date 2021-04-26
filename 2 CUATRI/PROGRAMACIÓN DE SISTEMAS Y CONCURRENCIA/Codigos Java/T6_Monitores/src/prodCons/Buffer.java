package prodCons;

public class Buffer {
	private int[] elem; //array de elementos
	private int p;      //posici�n donde guardar pr�ximo elemento
	private int c;      //posici�n donde est� el siguiente elemento a consumir
	private int nelem;  //n�mero de elementos en el buffer

	public Buffer(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		//n == hayHuecos + hayElementos
				
		elem = new int[n];
		p = 0;
		c = 0;
		nelem = 0;
	}
	
	public synchronized void insertar(int x) throws InterruptedException {
		//Condicion de sincronizacion - si el buffer esta lleno espero
		while (nelem == elem.length) wait();
		elem[p] = x;
		p = (p+1) % elem.length; //incremento circular
		++nelem;
		System.out.println("Elemento Producido: " + x);
		notifyAll(); //despierta a todas las hebras en el conjunto de espera
	}
	
	public synchronized int extraer() throws InterruptedException {
		//Condicion de sincronizacipn - si el buffer esta vacio espera
		while (nelem == 0) wait();
		int x = elem[c];
		c = (c+1) % elem.length; //incremento circular
		--nelem;
		System.out.println("Elemento Consumido: " + x);
		notifyAll(); //despierta a todas las hebras en el conjunto de espera
		return x;
	}
}
