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
	
	// al inicio el n�mero de vidas es VIDASMAX
	@Test
	public void vidasIniciales() 
	{
		assertEquals("Al crear las vidas son " + VIDASMAX, VIDASMAX, jugador.getVidas());
	}
	
	// al inicio el n�mero de balas es BALAMAX
	@Test
	public void balasIniciales() 
	{
		 assertEquals("Al crear las balas son " + BALASMAX, BALASMAX, jugador.getBalas());
	}
	
	// al incio el inventario est� vac�o
	@Test
	public void inventarioInicial() 
	{
		assertTrue("Al crear el inventarioInicial est� vacio" , jugador.numeroObjetosEnInventario() == 0);
	}
	
	// tras recibir da�o la vida disminuye el da�o
	@Test
	public void vidaDisminuyeTrasDa�o() throws Exception 
	{
		 int da�o = 1;
	     int vida = jugador.getVidas();
	     jugador.herir(da�o);
	     assertTrue("La vida despu�s del da�o es" + jugador.getVidas() , jugador.getVidas() == vida - da�o );
	}
	
	// la vida nunca es menor que cero
	@Test
	public void vidaNuncaMenorCero() 
	{
		assertTrue("Las vidas nunca son menores que cero" , jugador.getVidas() >= 0 );
	}
	
	// si ha recibido tanto da�o como vidasmax est� muerto
	@Test
	public void masDa�oQueVidamaxEntoncesMuerto() 
	{
		int da�o = 1;
        assertTrue("El da�o es mayor que  la vida m�xima, entonces estas muerto ", jugador.estaMuerto() == da�o > VIDASMAX  );
	}
	
	// la vida aumenta al cursarse la cantidad indicada (si ha recibido da�o)
	@Test
	public void vidaAumentaAlCurar() throws MuertoException 
	{
		int da�o = 1;
		jugador.herir(da�o);
		int vidaActual = jugador.getVidas();
		int cura = 1;
		jugador.curar(cura);
		assertTrue("Vidas aumentan al curar", jugador.getVidas() == vidaActual + cura);
	}
	
	// la vida nunca supera VIDASMAX
	@Test
	public void vidaNoSuperaVIDASMAX() 
	{
	    assertTrue("La vida no supera la vida m�xima", jugador.getVidas() <= VIDASMAX );
	}	
	
	// no es posible revivir si ya el jugador est� vivo
	@Test(expected = VivoException.class)
	public void noRevivirSiEstaVivo() throws MuertoException, VivoException 
	{
		jugador.revivir();
	}
	
	// no es posible herir a un muerto
	@Test(expected = MuertoException.class)
	public void noPodemosHerirAMuerto() throws MuertoException 
	{
		  int da�o = 6;
	      jugador= new Player(0, BALASMAX, TAMINVENTARIO);
	      jugador.herir(da�o);
	}
	
	// al revivir la vida es VIDASMAX
	@Test
	public void alRevivirVidaEsVIDASMAX() throws VivoException 
	{
		assertEquals("Al revivir la vida es igual a vida m�xima", jugador.getVidas(), VIDASMAX);
	}
	
	
	// TESTING DISPAROS
	
	// tras disparar las balas disminuyen en uno
	@Test
	public void dispararDisminuyeBalasEnUno() throws SinBalasException, MuertoException 
	{
		int balas =jugador.getBalas();
	    jugador.dispara();
	    assertTrue("La balas despu�s de disparar es" + jugador.getBalas() , jugador.getBalas() == balas - 1 );
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
	    assertEquals("La balas despu�s de disparar son" + jugador.getBalas() , jugador.getBalas() , balas - 1 );
	}
	
	// un muerto no puede disparar
	@Test(expected = MuertoException.class)
	public void unMuertoNoPuedeDisparar() throws SinBalasException, MuertoException 
	{
		jugador= new Player( 0 , BALASMAX , TAMINVENTARIO);
		jugador.dispara();
	}
	
	
	// TESTING INVENTARIO
	
	// tras a�adir un objeto al inventario, el inventario lo contiene
	@Test
	public void trasA�adirObjetoElInventarioLoContiene() throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException
	{
		Integer item = Integer.valueOf(1);
		jugador.coger(item);
		assertTrue("El objeto est� en inventario", jugador.enInventario(item));
	}
	
	// tras a�adir un objeto al inventario, el inventario tiene tama�o 1
	@Test
	public void trasA�adirObjetoElInventarioTieneUnObjeto() throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException 
	{
		Integer item = Integer.valueOf(1);
		jugador.coger(item);
		assertTrue("El inventario ahora tiene tama�o 1", jugador.numeroObjetosEnInventario()==1);
	}
	
	// tras a�adir un objeto al inventario, el inventario tiene TAMINVENTARIO - 1
	@Test
	public void trasA�adirObjetoElInventarioTieneTama�oMenosUno() throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException 
	{
		Integer item = Integer.valueOf(1);
		jugador.coger(item);
		assertTrue("El inventario ahora tiene tama�o " + jugador.espacioLibreEnInventario(),jugador.enInventario(item) );
	}
	
	
	// tras a�adir un objeto al inventario y quitarlo, el inventario NO lo contiene
	@Test
	public void trasA�adirObjetoYQuitarloElInventarioNOLoContiene() throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException, InventarioVacioException, NoEnInventarioException 
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
	public void inventarioNoPuedeTenerMasObjetoQueSuTama�o() 
	{
		assertTrue("El inventario no puede superar el tama�o m�ximo", jugador.numeroObjetosEnInventario() <= TAMINVENTARIO );
	}
	
	// si el jugador est� muerto no puede coger objetos
	@Test(expected = MuertoException.class)
	public void unMuertoNoPuedeCogerObjeto() throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException 
	{
		jugador = new Player( 0 , BALASMAX , TAMINVENTARIO);
		Integer item = Integer.valueOf(1);
		jugador.coger(item);
	}
	
	// si el jugador est� muerto no puede soltar objetos
	@Test(expected = MuertoException.class)
	public void unMuertoNoPuedeSoltarObjeto() throws InventarioVacioException, NoEnInventarioException, MuertoException, InventarioLlenoException, ObjetoEnInventarioException 
	{
		jugador= new Player( 0 , BALASMAX , TAMINVENTARIO);
		Integer item = Integer.valueOf(1);
		jugador.soltar(item);
	}
	
	// tras revivir el inventario est� vac�o
	@Test
	public void trasRevivirElInvetarioEstaVacio() throws VivoException 
	{
		jugador= new Player( 0 , BALASMAX , TAMINVENTARIO);
		jugador.revivir();
		assertTrue("tras revivir el inventario est� vac�o", jugador.inventarioVacio());
	}
	
}

