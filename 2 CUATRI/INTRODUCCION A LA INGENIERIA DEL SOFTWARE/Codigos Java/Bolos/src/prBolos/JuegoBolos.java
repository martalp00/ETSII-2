package prBolos;

public class JuegoBolos 
{
	private int[] tiradas = new int[21];
	private int tiradaActual;
	private int RONDAS = 10;
	
	public int puntuar() 
	{
		int puntosTotales = 0;
		int numTirada = 0;

		for (int i = 0; i < RONDAS; i++)
		{
			if (tiradas[numTirada] == 10) 
			{
				puntosTotales += 30;
				numTirada++;
			} 
			else if ((tiradas[numTirada] + tiradas[numTirada+1]) == 10) 
			{
				puntosTotales += 10 + tiradas[numTirada+2];
				numTirada += 2;
			} 
			else 
			{
				puntosTotales += tiradas[numTirada] + tiradas[numTirada+1];
				numTirada += 2;
			}
		}

		return puntosTotales;
	}
	
	public void tirada(int p) 
	{
		tiradas[tiradaActual++] = p;
	}
}