package pr3;

import java.util.Scanner;

public class jose 
{
	// Precondici�n: array int ip que representa la ip.
	// Poscondici�n: devuelve un String que representa la ip (Formato: X.X.X.X).
	private static String ip_toString(int[] ip) 
	{
		return ip[0]+"."+ip[1]+"."+ip[2]+"."+ip[3];
	}
	
	
	
	// Precondici�n: int mascara (cuyo valor debe estar entre 0 y 32).
	// Poscondici�n: devuelve un int con el n�mero de ips disponibles para un host.
	private static int numero_ips(int mascara) {
		if(mascara<0 || mascara>32) { //Si el parametro mascara no respeta su rango, lanzar� una excepci�n
			throw new RuntimeException("Rango de mascara invalido");
		}
		//El n�mero de host disponibles ser� las IPs disponibles en funcion de la m�scara
		//menos 2 direcciones especiales (ID_red y Broadcast): ((32-m�scara)^2)-2
		return (int) (Math.pow(2, 32-mascara) -2);
	}
	
	

	// Precondici�n: array int ip que representa la ip y int mascara (cuyo valor debe estar entre 0 y 32)
	// Poscondici�n: devuelve un String con la direcci�n de difusi�n de la red
	private static String difusi�n(int[] ip, int mascara) {
		int[] broadcast = {0,0,0,0}; //broadcast guardar� la ip que se utilizar� de difusi�n
		int potencia2; //potencia2 almacenar� el valor decimal que representa cada bit en cada byte (128, 64, 32, 16, 8, 4, 2 o 1)
		int copiaByte; //copiaByte es una variable auxiliar donde copiaremos el byte donde estemos trabajando
		boolean copiamosElBit; //copiamosElBit es un tipo boolean que nos ayudar� a hacer una AND bit a bit entre ip y la mascara
		
		for(int numByte=0;numByte<4;numByte++) { // Recorremos los 4 byte de la direcci�n ip
			copiaByte=ip[numByte]; // Realizamos una copia del byte para luego
			
			for(int i=1;i<9;i++) { // Recorremos los 8 bits de cada byte
				potencia2=(int) Math.pow(2, 8-i); //Calculamos el valor del bit en el que nos encotramos
				//Si el bit en el que estamos est� detro de la mascara: debemos copiar el valor de ese bit
				//(otra forma de verlo es si el bit de la m�scara es 1, hacemos una AND con el bit de ip; si es 0, ponemos todos los bits a 1)
				if(numByte*8+i<=mascara){
					copiamosElBit=(copiaByte>=potencia2);			 //Si el valor de copiaByte es mayor que el bit en el que nos encontramos: copiamos el bit (1 AND 1 = 1)
					broadcast[numByte]+=(copiamosElBit)?potencia2:0; //Si copiamosElBit==True, le sumamos al byte de broadcast el valor de dicho bit.
					copiaByte-=(copiamosElBit)?potencia2:0;			 //Si copiamosElBit==True, le restamos a la copia del byte el valor de dicho bit para la siguiente comparaci�n.
				}else {
					broadcast[numByte]+=potencia2;				   	 //Si el bit est� fuera de la m�scar�, lo ponemos a 1 (sumamos el valor decimal que representa dicho bit)
				}
			}
		}
		return ip_toString(broadcast); //Devolvemos el valor en forma de String
	}
	
	// Precondici�n: array int ip que representa la ip y int mascara (cuyo valor debe estar entre 0 y 32)
	// Poscondici�n: devuelve un String con el identificador de red y su mascara	
	private static String id_red(int[] ip, int mascara) {
		int[] ip_id = {0,0,0,0}; //ip_id guardar� la ip que identifica la red
		int potencia2; //potencia2 almacenar� el valor decimal que representa cada bit en cada byte (128, 64, 32, 16, 8, 4, 2 o 1)
		int copiaByte; //copiaByte es una variable auxiliar donde copiaremos el byte donde estemos trabajando
		boolean copiamosElBit; //copiamosElBit es un tipo boolean que nos ayudar� a hacer una AND bit a bit entre ip y la mascara
		
		for(int numByte=0;numByte<4;numByte++) { // Recorremos los 4 byte de la direcci�n ip
			copiaByte=ip[numByte]; // Realizamos una copia del byte para luego
			
			for(int i=1;i<9;i++) { // Recorremos los 8 bits de cada byte
				//Si el bit en el que estamos est� detro de la mascara: debemos copiar el valor de ese bit
				//(otra forma de verlo es si el bit de la m�scara es 1, hacemos una AND con el bit de ip; si es 0, no hacemos nada)
				if(numByte*8+i<=mascara){
					potencia2=(int) Math.pow(2, 8-i);			 //Calculamos el valor del bit en el que nos encotramos
					copiamosElBit=(copiaByte>=potencia2); 		 //Si el valor de copiaByte es mayor que el bit en el que nos encontramos: copiamos el bit (1 AND 1 = 1)
					ip_id[numByte]+=(copiamosElBit)?potencia2:0; //Si copiamosElBit==True, le sumamos al byte de ip_id el valor de dicho bit.
					copiaByte-=(copiamosElBit)?potencia2:0;		 //Si copiamosElBit==True, le restamos a la copia del byte el valor de dicho bit para la siguiente comparaci�n.
				}
			}
		}
		return ip_toString(ip_id)+"/"+mascara;	//Devolvemos el valor en forma de String
	}
	
	// Precondici�n: int primerByte que representa el primer byte de ip (el valor debe estar entre 0-255, ambos incluidos).
	// Poscondici�n: devuelve un tipo char con la clase de la ip (A, B, C, D o E).
	private static char clase_ip(int primerByte) {
		char res;
		if(primerByte<128) { 		//Si el primer byte est� entre   0 y 127 (ambos incluidos): A
			res='A';
		}else if(primerByte<192) { 	//Si el primer byte est� entre 128 y 191 (ambos incluidos): B
			res='B';
		}else if (primerByte<224) { //Si el primer byte est� entre 192 y 223 (ambos incluidos): C
			res='C';
		}else if (primerByte<240) { //Si el primer byte est� entre 224 y 239 (ambos incluidos): D
			res='D';
		}else { 					//Si el primer byte est� entre 240 y 255 (ambos incluidos): E
			res='E';
		}
		return res;
	}

	
	public static void main(String[] args) {
		// Creamos un array de enteros donde almacenaremos los 4 bytes de la ip
		int[] ip = {0,0,0,0};
		// Creamos tambi�n un entero donde guardar la m�scara
		int mascara;
		// Usamos un try-catch para utilizar un objeto scanner para leer los datos de ip y m�scara
		try(Scanner sc = new Scanner(System.in);){
			sc.useDelimiter("[^0-9]"); //Con estos delimitadores solo se leer�n los numeros de 0-9, y lo contrario a ellos se ignorar�
			System.out.println("Introduzca una IP y su mascara (Formato: X.X.X.X <espacio> Mascara)(0<=X<=255)(0<=Mascara<=32): ");
			for(int i=0;i<4;i++) {
				ip[i]=sc.nextInt(); //Leemos los 4 bytes de ip
			}
			mascara = sc.nextInt(); //Leemos la mascara
			System.out.println("IP introducida: "+ ip_toString(ip)); //Comprobamos si la ip introducida es correcta
			System.out.println("Mascara introducida: "+ mascara); //Comprobamos si la mascara introducida es correcta
			System.out.println("La IP es de clase " + clase_ip(ip[0])); //Imprimimos por pantalla la clase de ip: clase_ip(ip[0])
			System.out.println("Red: " + id_red(ip,mascara)); //Imprimimos por pantalla el identificador de red: id_red(ip,mascara)
			System.out.println("Difusi�n: " + difusi�n(ip,mascara)); //Imprimimos por pantalla el broadcast de red: difusi�n(ip,mascara)
			System.out.println("N�mero de IPs para host: " + numero_ips(mascara)); //Imprimimos por pantalla las ip disponibles para hosts: numero_ips(mascara)
		}catch(Exception e) {
			//Cualquier excepcion dentro del try se deber� a un error de formato al introducir los datos de entrada
			System.out.println("Formato entrada incorrecto (Formato: X.X.X.X <espacio> Mascara): "+e);
		}
	}
}
