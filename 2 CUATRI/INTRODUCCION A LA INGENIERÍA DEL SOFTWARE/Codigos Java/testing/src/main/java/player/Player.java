package player;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private int vidas;
	private int vidasMaximas;
	private int balas;
	private int balasMaximas;
	private int tamañoInventario;
	private List<Object> inventario = null;
	
	public Player(int vidas, int balas, int tamaño) {
		inventario = new ArrayList<Object>();
		
		this.vidas = this.vidasMaximas = vidas;
		this.balas = this.balasMaximas = balas;
		this.tamañoInventario = tamaño;
	}
	
	// funciones con VIDA
	
	public int getVidas() {
		return vidas;
	}
	
	public void herir(int daño) throws MuertoException {
		if (estaMuerto())
			throw new MuertoException("Estás muerto");
		
		vidas -= daño;
		if (vidas < 0)
			vidas = 0;
	}
	
	public void revivir() throws VivoException {
		if (!estaMuerto())
			throw new VivoException("Estás vivo");
		
		vidas = vidasMaximas;
		// vaciamos el inventario
		vaciarInventario();
	}
	
	public void curar(int cura) throws MuertoException {
		if (estaMuerto())
			throw new MuertoException("Estás muerto");
		
		vidas += cura;
		if (vidas > vidasMaximas)
			vidas = vidasMaximas;
	}
	
	public boolean estaMuerto() {
		return vidas == 0;
	}
	
	// funciones con BALAS
	
	public int getBalas() {
		return balas;
	}
	
	public void dispara() throws SinBalasException, MuertoException 
	{
		if (estaMuerto())
			throw new MuertoException("Estás muerto");
		if (sinBalas())
			throw new SinBalasException("No quedas balas");
		
		balas--;
	}
	
	public void recarga() {
		balas = balasMaximas;
	}
	
	public boolean sinBalas() {
		return balas == 0;
	}
	
	// funciones inventario
	
	public void coger(Object item) throws InventarioLlenoException, MuertoException, ObjetoEnInventarioException {
		if (estaMuerto())
			throw new MuertoException("Estás muerto");
		if (inventarioLleno())
			throw new InventarioLlenoException("El inventario está lleno");
		if (enInventario(item))
			throw new ObjetoEnInventarioException("El objeto ya está en el inventario");
		
		inventario.add(item);
	}
	
	public void soltar(Object item) throws InventarioVacioException, NoEnInventarioException, MuertoException {
		if (estaMuerto())
			throw new MuertoException("Estás muerto");
		if (inventarioVacio())
			throw new InventarioVacioException("El inventario está vacío");
		
		if (!enInventario(item))
			throw new NoEnInventarioException("El item no está en el inventario");
		
		inventario.remove(item);
	}
	
	public void vaciarInventario() {
		inventario.clear();
	}
	
	public boolean enInventario(Object item) {
		return inventario.contains(item);
	}
	
	public boolean inventarioLleno() {
		return inventario.size() == tamañoInventario;
	}
	
	public boolean inventarioVacio() {
		return inventario.size() == 0;
	}
	
	public int numeroObjetosEnInventario() {
		return inventario.size();
	}
	
	public int espacioLibreEnInventario() {
		return tamañoInventario - inventario.size();
	}
} 
