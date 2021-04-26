package restauranteSEM;

public class Principal {

	public static void main(String[] args) 
	{
		Restaurante b = new Restaurante(5);
		Cocinero1 coc1 = new Cocinero1(b);
		Cocinero2 coc2 = new Cocinero2(b);
		Cocinero3 coc3 = new Cocinero3(b);
		
		coc1.start();
		coc2.start();
		coc3.start();
	}

}
