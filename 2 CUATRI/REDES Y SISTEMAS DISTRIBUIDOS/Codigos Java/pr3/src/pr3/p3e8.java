//Alumno: Marta López Pérez
//Grado: Ingeniería Informática
//Grupo: 2º A

package pr3;

public class p3e8 
{
	public static void main(String[] args) 
	{
		String cadena = "192.168.45.30 21";
		
		String [] ip = cadena.split("[ .]");				//Dividimos la cadena en el array
		
		int numMascara = Integer.parseInt(ip[4]);			// Asignamos la ultima posicion al numero de la mascara
			
		String IP = mostrarIP(ip);							// y las 4 primeras a la IP
		String MASCARA = mascaraIPdecimal(numMascara);		// Se expresa la máscara de la forma X.X.X.X a partir del número de máscara que se introduce
		
		System.out.println("La IP introducida es: " + IP);
		System.out.println("La mascara introducida es : " + numMascara + " ("+ MASCARA + ")");
		System.out.println();
		System.out.println("La IP es de clase " + claseIP(Integer.parseInt(ip[0]))); 
		System.out.println("Red: " + indentificadorRed(IP, MASCARA) + "/" + numMascara);
		System.out.println("Difusión: " + difusion(IP, MASCARA));
		System.out.println("Número de IPs para host: " + numeroIPhost(numMascara));
	}
	
	private static String mostrarIP(String[] s)
	{
		return s[0] + "." + s[1] + "." + s[2] + "." + s[3];		//Se muestra la ip del modo X.X.X.X
	}
	
	private static char claseIP(int b) 
	{
		char res;
		
		if(b<128) 
		{ 									//Si el primer byte está entre   0 y 127 (ambos incluidos): A
			res='A';
		}
		else if (b<192) 
		{ 									//Si el primer byte está entre 128 y 191 (ambos incluidos): B
			res='B';
		}
		else if (b<224) 
		{ 									//Si el primer byte está entre 192 y 223 (ambos incluidos): C
			res='C';
		}
		else if (b<240) 
		{									 //Si el primer byte está entre 224 y 239 (ambos incluidos): D
			res='D';
		}
		else 
		{ 									//Si el primer byte está entre 240 y 255 (ambos incluidos): E
			res='E';
		}
		
		return res;
	}
	
	private static String mascaraIPdecimal(int n) 
	{
		String res = "", num = "", aux = ""; 
		int resto = n%8, relleno = n/8, cont = 0;	
		
		/* 
		 * Dividimos la mascara entre cuantos grupos de 8 bits están completos (todo 1, se guarda en relleno) 
		 * y los que no están llenos del todo (todo 0 o cualquier otro número, se guarda en resto)
		 */
		
		for(int i=0; i<relleno; i++)
		{
			res += 255 + ".";		// Si está todo 1 el número es 255.
		}
		int tam = 4-relleno;
		
		while(tam>0)			// Se rellenan los demas grupos que no hayan sido completados
		{
			if(resto == cont)	// Si en resto no hay o queda nada el número es un 0
			{
				num += "0.";	
			}
			else				// Si no, se rellenan tantos 1 como haya (siempre sera <8) y luego hasta 8 con 0
			{
				for(int j=0; j<resto; j++)
				{
					aux += "1";
					cont++;
				}
				for(int k=aux.length(); k<8; k++)
				{
					aux += "0";
				}
				num += binarioAdecimal(aux) + ".";	// Como queremos la máscara en decimal y no en binario, 
													// pasamos el número que acabamos de crear en este grupo a decimal
				aux = "";
			}
			tam--;
		}
		
		return res + num.substring(0, num.length()-1);			// Esto es para quitarle el "." del final
	}
	
	private static String binarioAdecimal(String aux)
	{
		int suma = 0;
		int tam = aux.length();
		int num = Integer.parseInt(aux);
		
		for(int i=0; i<tam; i++)
		{
			if( num % 10 == 1)
			{
				suma += Math.pow(2, i);				// Convierte el numero binario pasado por parámetro a decimal 
			}										// sumando todas las posiciones de la cadena y del tipo 2^0 + 2^1 + ... hasta que acaba
			num /= 10;
		}
		
		return String.valueOf(suma);
	}
	
	private static String indentificadorRed(String ip, String mascara) 
	{
		/*
		 * Para calcular el identificador se hace una suma AND entre la IP y la máscara
		 */
		
		String[] sip = ip.split("[.]");		// Creamos un array para cada String dividido por ".", para poder sumar por grupos de 8 bits.
		String[] smas = mascara.split("[.]");
		
		int and;
		
		String res = "";
		
		for(int i=0; i<4; i++)			// Recorremos los 4 grupos y sumamos cada grupo con el correspondiente
		{
			and = Integer.parseInt(sip[i]) & Integer.parseInt(smas[i]);
			res += String.valueOf(and);

			if(i != 3)
			{
				res += ".";
			}
		}
		return res ;
	}
	
	private static String difusion(String ip, String mascara)
	{
		/*
		 * Para calcular la difusion se hace una suma OR entre la IP y la máscara inversa
		 */
		
		String[] sip = ip.split("[.]");		// Creamos un array para cada String dividido por ".", para poder sumar por grupos de 8 bits.
		
		String mas = mascaraIPdifusion(mascara);	// Necesitamos la inversa de la máscara y este método te devuelve un String con su inversa
		String[] smas = mas.split("[.]");
		
		int or;
		
		String res = "";
		
		for(int i=0; i<4; i++)				// Recorremos los 4 grupos y sumamos cada grupo con el correspondiente
		{
			or = Integer.parseInt(sip[i]) | Integer.parseInt(smas[i]);		
			res += String.valueOf(or);

			if(i != 3)
			{
				res += ".";
			}
		}
		return res ;
	}
	
	private static String mascaraIPdifusion(String mas)
	{
		String[] m = mas.split("[.]");				// Dividimos el String en un array separado por ".", para poder sumar por grupos de 8 bits.
		String res = "";
		int resta = 0;
		
		for(int i=0; i<m.length; i++)
		{
			resta = 255 - Integer.parseInt(m[i]);	// Restamos 255-el numero que tenga para calcular el inverso
			res += resta + ".";
		}
		
		return res.substring(0, res.length()-1);	// Esto es para quitarle el "." del final
	}
	
	private static int numeroIPhost(int mascara)
	{
		/*
		 * El número de host disponibles se calcula en funcion de la máscara 
		 * menos 2 direcciones especiales (ID_red y Broadcast)--> ((32-máscara)^2)-2
		 */
		
		return (int)(Math.pow(2, 32- mascara) -2);		
	}
}

/*	
	** Utilicé esta pero este metodo devuelve la máscara en binario no en decimal, por eso se usa el otro **
	
	private static String mascaraIPdbinario(int n)
	{
		String res = ""; 
		
		for(int i=1; i<=33; i++)
		{
			if(i<=n)
			{
				res += 1;
			}
			else
			{
				res += 0;
			}
			if(i%8 == 0 && i != 32)
			{
				res += ".";
			}
		}
		return res;
	}
	
*/
