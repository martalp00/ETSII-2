package prBolos;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class JuegoBolosTest 
{
	private JuegoBolos bolos;
	
	void bolosTirados(int tirosTotales, int puntosPorTiro, JuegoBolos partida)
	{
		for (int i = 0; i < tirosTotales; i++)
		{
			partida.tirada(puntosPorTiro);
		}
	}
	
	@Before
	public void setup()
	{
		bolos = new JuegoBolos();
	}
	
	 //Todas las tiradas de la partida no tiran ningún bolo. Puntuación final: 0 puntos
	@Test
	public void JuegoPesimo() 
	{
		bolosTirados(20, 0, bolos);
		assertEquals(0, bolos.puntuar());
	}
	
	//Todas las rondas con pleno. Puntuación final:	300 puntos
	@Test
	public void JuegoPerfecto() 
	{
		bolosTirados(10, 10, bolos);
		assertEquals(300, bolos.puntuar());
	}
	
	//Todas las tiradas de la partida tiran un solo bolo. Puntuación final: 20 puntos
	@Test
	public void JuegoTodoUno() 
	{
		bolosTirados(20, 1, bolos);
		assertEquals(20, bolos.puntuar());
	}
	
	//Tirada con un pleno y el resto de tiradas a 0 (18	tiradas). Puntuación final: 30 puntos
	@Test
	public void JuegoUnPleno() 
	{	
		bolos.tirada(10);
		bolosTirados(18, 0, bolos);
		
		assertEquals(30, bolos.puntuar());
	}
	
	//Tirada con un semipleno, la siguiente tirada con un valor (3 p.e.) y el resto de tiradas a 0. 
	//Puntuación final: (10 + 3) + 3 = 16 puntos (primera ronda 10+3)
	@Test
	public void JuegoUnSemipleno() 
	{
		bolos.tirada(4);
		bolos.tirada(6);
		bolos.tirada(3);
		bolosTirados(17, 0, bolos);
		assertEquals(16, bolos.puntuar());
	}
	
}
