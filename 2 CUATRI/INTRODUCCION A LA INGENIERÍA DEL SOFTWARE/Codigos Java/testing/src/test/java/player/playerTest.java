package player;

import static org.junit.Assert.*;

import org.junit.*;

public class playerTest {
	private static final int VIDASMAX = 3;
	private static final int BALASMAX = 5;
	private static final int TAMINVENTARIO = 3;
	private Player jugador;

	@Before
	public void setUp() throws Exception 
	{
		jugador = new Player(VIDASMAX, BALASMAX, TAMINVENTARIO);
	}

	@After
	public void tearDown() throws Exception 
	{
		jugador = null;
	}
	
	// al inicio el número de vidas es VIDASMAX
	@Test
	public void vidasIniciales() 
	{
		assertEquals("Al crear las vidas son " + VIDASMAX, VIDASMAX, jugador.getVidas());
	}
	
	// al inicio el número de balas es BALAMAX
	@Test
	public void balasIniciales() 
	{
		 assertEquals("Al crear las balas son " + BALASMAX, BALASMAX, jugador.getBalas());
	}
	
	// al incio el inventario está vacío
	@Test
	public void inventarioInicial() 
	{
		assertTrue("Al crear el inventarioInicial está vacio" , jugador.numeroObjetosEnInventario() == 0);
	}
	
	// tras recibir daño la vida disminuye el daño
	@Test
	public void vidaDisminuyeTrasDaño() throws Exception 
	{
		 int daño = 1;
	     int vida = jugador.getVidas();
	     jugador.herir(daño);
	     assertTrue("La vida después del daño es" + jugador.getVidas() , jugador.getVidas() == vida - daño );
	}
	
	// la vida nunca es menor que cero
	@Test
	public void vidaNuncaMenorCero() 
	{
		assertTrue("Las vidas nunca son menores que cero" , jugador.getVidas() >= 0 );
	}
	
	// si ha recibido tanto daño como vidasmax está muerto
	@Test
	public void masDañoQueVidamaxEntoncesMuerto() 
	{
		int daño = 1;
        assertTrue("El daño es mayor que  la vida máxima, entonces estas muerto ", jugador.estaMuerto() == daño > VIDASMAX  );
	}
	
	// la vida aumenta al cursarse la cantidad indicada (si ha recibido daño)
	@Test
	public void vidaAumentaAlCurar() throws MuertoException 
	{
		int daño = 1;
		jugador.herir(daño);
		int vidaActual = jugador.getVidas();
		int cura = 1;
		jugador.curar(cura);
		assertTrue("Vidas aumentan al curar", jugador.getVidas() == vidaActual + cura);
	}
	
	// la vida nunca supera VIDASMAX
	@Test
	public void vidaNoSuperaVIDASMAX() 
	{
	    assertTrue("La vida no supera la vida máxima", jugador.getVidas() <= VIDASMAX );
	}	
	
	// no es posible revivir si ya el jugador está vivo
	@Test(expected = VivoException.class)
	public void noRevivirSiEstaVivo() throws MuertoException, VivoException 
	{
		jugador.revivir();
	}
	
	// no es posible herir a un muerto
	@Test(expected = MuertoException.class)
	public void noPodemosHerirAMuerto() throws MuertoException 
	{
		  int daño = 6;
	      jugador= new Player(0, BALASMAX, TAMINVENTARIO);
	      jugador.herir(daño);
	}
	
	// al revivir la vida es VIDASMAX
	@Test
	public void alRevivirVidaEsVIDASMAX() throws VivoException 
	{
		assertEquals("Al revivir la vida es igual a vida máxima", jugador.getVidas(), VIDASMAX);
	}
	
	
	// TESTING DISPAROS
	
	// tras disparar las balas disminuyen en uno
	@Test
	public void dispararDisminuyeBalasEnUno() throws SinBalasException, MuertoException 
	{
		int balas =jugador.getBalas();
	    jugador.dispara();
	    assertTrue("La balas después de disparar es" + jugador.getBalas() , jugador.getBalas() == balas - 1 );
	}
	
	// tras recargar las balas son BALASMAX
	@Test
	public void trasRecargarBalasSonBALASMAX() 
	{
		jugador.recarga();
		assertEquals("Tras recargar las balas son BALASMAX", jugador.getBalas(), BALASMAX);
	}
	
	// no es posible disparar sin balas
	@Test
	public void noPuedeDispararSinBalas() throws SinBalasException, MuertoException 
	{
		int balas =jugador.getBalas();
	    jugador.dispara();
	    assertEquals("La balas después de disparar son" + jugador.getBalas() , jugador.getBalas() , balas - 1 );
	}
	
	// un muerto no puede disparar
	@Test(expected = MuertoException.class)
	public void unMuertoNoPuedeDisparar() throws SinBalasException, MuertoException 
	{
		jugador= new Player( 0 , BALASMAX , TAMINVENTARIO);
		jugador.dispara();
	}
	
	
	// TESTING INVENTARIO
	
	// tras añadir un objeto al inventario, el inventario lo contiene
	@Test
	public void trasAñadirObjetoElInventarioLoContiene() throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException
	{
		Integer item = Integer.valueOf(1);
		jugador.coger(item);
		assertTrue("El objeto está en inventario", jugador.enInventario(item));
	}
	
	// tras añadir un objeto al inventario, el inventario tiene tamaño 1
	@Test
	public void trasAñadirObjetoElInventarioTieneUnObjeto() throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException 
	{
		Integer item = Integer.valueOf(1);
		jugador.coger(item);
		assertTrue("El inventario ahora tiene tamaño 1", jugador.numeroObjetosEnInventario()==1);
	}
	
	// tras añadir un objeto al inventario, el inventario tiene TAMINVENTARIO - 1
	@Test
	public void trasAñadirObjetoElInventarioTieneTamañoMenosUno() throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException 
	{
		Integer item = Integer.valueOf(1);
		jugador.coger(item);
		assertTrue("El inventario ahora tiene tamaño " + jugador.espacioLibreEnInventario(),jugador.enInventario(item) );
	}
	
	
	// tras añadir un objeto al inventario y quitarlo, el inventario NO lo contiene
	@Test
	public void trasAñadirObjetoYQuitarloElInventarioNOLoContiene() throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException, InventarioVacioException, NoEnInventarioException 
	{
		Integer item = Integer.valueOf(1);
		jugador.coger(item);
		jugador.soltar(item);
	}
	
	// no podemos coger dos veces el mismo objeto
	@Test(expected = ObjetoEnInventarioException.class)
	public void NoPodemosCogerElMismoObjetoDosVeces() throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException 
	{
		Integer item = Integer.valueOf(1);
		jugador.coger(item);
		jugador.coger(item);
	}
	
	// no es posible soltar un objeto que no hemos cogido
	@Test(expected=NoEnInventarioException.class)
    public void NoPodemosSoltarUnObjetoNoCogido() throws InventarioVacioException, NoEnInventarioException, MuertoException, InventarioLlenoException, ObjetoEnInventarioException
	{
        jugador=new Player(VIDASMAX,BALASMAX,TAMINVENTARIO);
        Integer item = Integer.valueOf(1);
        Integer item2 = Integer.valueOf(2);
        jugador.coger(item2);

        if(!jugador.inventarioVacio() && !jugador.estaMuerto()) 
        {
        	jugador.soltar(item);
        }
	}
	
	// el inventario no puede contener mas de TAMINVENTARIO objetos
	@Test
	public void inventarioSeLlenaSiInsertamosObjeto() throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException 
	{
	    Integer objeto = Integer.valueOf(4);
        jugador.coger(objeto);
	}
	
	// el inventario no puede contener mas de TAMINVENTARIO objetos
	@Test
	public void inventarioNoPuedeTenerMasObjetoQueSuTamaño() 
	{
		assertTrue("El inventario no puede superar el tamaño máximo", jugador.numeroObjetosEnInventario() <= TAMINVENTARIO );
	}
	
	// si el jugador está muerto no puede coger objetos
	@Test(expected = MuertoException.class)
	public void unMuertoNoPuedeCogerObjeto() throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException 
	{
		jugador = new Player( 0 , BALASMAX , TAMINVENTARIO);
		Integer item = Integer.valueOf(1);
		jugador.coger(item);
	}
	
	// si el jugador está muerto no puede soltar objetos
	@Test(expected = MuertoException.class)
	public void unMuertoNoPuedeSoltarObjeto() throws InventarioVacioException, NoEnInventarioException, MuertoException, InventarioLlenoException, ObjetoEnInventarioException 
	{
		jugador= new Player( 0 , BALASMAX , TAMINVENTARIO);
		Integer item = Integer.valueOf(1);
		jugador.soltar(item);
	}
	
	// tras revivir el inventario está vacío
	@Test
	public void trasRevivirElInvetarioEstaVacio() throws VivoException 
	{
		jugador= new Player( 0 , BALASMAX , TAMINVENTARIO);
		jugador.revivir();
		assertTrue("tras revivir el inventario está vacío", jugador.inventarioVacio());
	}
	
}

