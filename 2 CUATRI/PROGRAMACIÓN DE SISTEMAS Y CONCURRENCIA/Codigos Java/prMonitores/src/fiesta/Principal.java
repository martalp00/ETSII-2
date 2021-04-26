package fiesta;

public class Principal 
{
	public static void main(String[] args) 
	{
		Bandeja pasteleria = new Bandeja();
		Pastelero pastelero = new Pastelero(pasteleria);
		Ni�o[] ni�o = new Ni�o[30];
		
		for (int i = 0; i<ni�o.length; i++)
		{
			ni�o[i] = new Ni�o(pasteleria,i);
		}
		
		pastelero.start();
		for (int i = 0; i<ni�o.length; i++)
		{
			ni�o[i].start();
		}
	}
}
