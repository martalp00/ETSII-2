package pr3;

import java.util.Scanner;

public class jose 
{
	// Precondición: array int ip que representa la ip.
	// Poscondición: devuelve un String que representa la ip (Formato: X.X.X.X).
	private static String ip_toString(int[] ip) 
	{
		return ip[0]+"."+ip[1]+"."+ip[2]+"."+ip[3];
	}
	
	
	
	// Precondición: int mascara (cuyo valor debe estar entre 0 y 32).
	// Poscondición: devuelve un int con el número de ips disponibles para un host.
	private static int numero_ips(int mascara) {
		if(mascara<0 || mascara>32) { //Si el parametro mascara no respeta su rango, lanzará una excepción
			throw new RuntimeException("Rango de mascara invalido");
		}
		//El número de host disponibles será las IPs disponibles en funcion de la máscara
		//menos 2 direcciones especiales (ID_red y Broadcast): ((32-máscara)^2)-2
		return (int) (Math.pow(2, 32-mascara) -2);
	}
	
	

	// Precondición: array int ip que representa la ip y int mascara (cuyo valor debe estar entre 0 y 32)
	// Poscondición: devuelve un String con la dirección de difusión de la red
	private static String difusión(int[] ip, int mascara) {
		int[] broadcast = {0,0,0,0}; //broadcast guardará la ip que se utilizará de difusión
		int potencia2; //potencia2 almacenará el valor decimal que representa cada bit en cada byte (128, 64, 32, 16, 8, 4, 2 o 1)
		int copiaByte; //copiaByte es una variable auxiliar donde copiaremos el byte donde estemos trabajando
		boolean copiamosElBit; //copiamosElBit es un tipo boolean que nos ayudará a hacer una AND bit a bit entre ip y la mascara
		
		for(int numByte=0;numByte<4;numByte++) { // Recorremos los 4 byte de la dirección ip
			copiaByte=ip[numByte]; // Realizamos una copia del byte para luego
			
			for(int i=1;i<9;i++) { // Recorremos los 8 bits de cada byte
				potencia2=(int) Math.pow(2, 8-i); //Calculamos el valor del bit en el que nos encotramos
				//Si el bit en el que estamos está detro de la mascara: debemos copiar el valor de ese bit
				//(otra forma de verlo es si el bit de la máscara es 1, hacemos una AND con el bit de ip; si es 0, ponemos todos los bits a 1)
				if(numByte*8+i<=mascara){
					copiamosElBit=(copiaByte>=potencia2);			 //Si el valor de copiaByte es mayor que el bit en el que nos encontramos: copiamos el bit (1 AND 1 = 1)
					broadcast[numByte]+=(copiamosElBit)?potencia2:0; //Si copiamosElBit==True, le sumamos al byte de broadcast el valor de dicho bit.
					copiaByte-=(copiamosElBit)?potencia2:0;			 //Si copiamosElBit==True, le restamos a la copia del byte el valor de dicho bit para la siguiente comparación.
				}else {
					broadcast[numByte]+=potencia2;				   	 //Si el bit está fuera de la máscará, lo ponemos a 1 (sumamos el valor decimal que representa dicho bit)
				}
			}
		}
		return ip_toString(broadcast); //Devolvemos el valor en forma de String
	}
	
	// Precondición: array int ip que representa la ip y int mascara (cuyo valor debe estar entre 0 y 32)
	// Poscondición: devuelve un String con el identificador de red y su mascara	
	private static String id_red(int[] ip, int mascara) {
		int[] ip_id = {0,0,0,0}; //ip_id guardará la ip que identifica la red
		int potencia2; //potencia2 almacenará el valor decimal que representa cada bit en cada byte (128, 64, 32, 16, 8, 4, 2 o 1)
		int copiaByte; //copiaByte es una variable auxiliar donde copiaremos el byte donde estemos trabajando
		boolean copiamosElBit; //copiamosElBit es un tipo boolean que nos ayudará a hacer una AND bit a bit entre ip y la mascara
		
		for(int numByte=0;numByte<4;numByte++) { // Recorremos los 4 byte de la dirección ip
			copiaByte=ip[numByte]; // Realizamos una copia del byte para luego
			
			for(int i=1;i<9;i++) { // Recorremos los 8 bits de cada byte
				//Si el bit en el que estamos está detro de la mascara: debemos copiar el valor de ese bit
				//(otra forma de verlo es si el bit de la máscara es 1, hacemos una AND con el bit de ip; si es 0, no hacemos nada)
				if(numByte*8+i<=mascara){
					potencia2=(int) Math.pow(2, 8-i);			 //Calculamos el valor del bit en el que nos encotramos
					copiamosElBit=(copiaByte>=potencia2); 		 //Si el valor de copiaByte es mayor que el bit en el que nos encontramos: copiamos el bit (1 AND 1 = 1)
					ip_id[numByte]+=(copiamosElBit)?potencia2:0; //Si copiamosElBit==True, le sumamos al byte de ip_id el valor de dicho bit.
					copiaByte-=(copiamosElBit)?potencia2:0;		 //Si copiamosElBit==True, le restamos a la copia del byte el valor de dicho bit para la siguiente comparación.
				}
			}
		}
		return ip_toString(ip_id)+"/"+mascara;	//Devolvemos el valor en forma de String
	}
	
	// Precondición: int primerByte que representa el primer byte de ip (el valor debe estar entre 0-255, ambos incluidos).
	// Poscondición: devuelve un tipo char con la clase de la ip (A, B, C, D o E).
	private static char clase_ip(int primerByte) {
		char res;
		if(primerByte<128) { 		//Si el primer byte está entre   0 y 127 (ambos incluidos): A
			res='A';
		}else if(primerByte<192) { 	//Si el primer byte está entre 128 y 191 (ambos incluidos): B
			res='B';
		}else if (primerByte<224) { //Si el primer byte está entre 192 y 223 (ambos incluidos): C
			res='C';
		}else if (primerByte<240) { //Si el primer byte está entre 224 y 239 (ambos incluidos): D
			res='D';
		}else { 					//Si el primer byte está entre 240 y 255 (ambos incluidos): E
			res='E';
		}
		return res;
	}

	
	public static void main(String[] args) {
		// Creamos un array de enteros donde almacenaremos los 4 bytes de la ip
		int[] ip = {0,0,0,0};
		// Creamos también un entero donde guardar la máscara
		int mascara;
		// Usamos un try-catch para utilizar un objeto scanner para leer los datos de ip y máscara
		try(Scanner sc = new Scanner(System.in);){
			sc.useDelimiter("[^0-9]"); //Con estos delimitadores solo se leerán los numeros de 0-9, y lo contrario a ellos se ignorará
			System.out.println("Introduzca una IP y su mascara (Formato: X.X.X.X <espacio> Mascara)(0<=X<=255)(0<=Mascara<=32): ");
			for(int i=0;i<4;i++) {
				ip[i]=sc.nextInt(); //Leemos los 4 bytes de ip
			}
			mascara = sc.nextInt(); //Leemos la mascara
			System.out.println("IP introducida: "+ ip_toString(ip)); //Comprobamos si la ip introducida es correcta
			System.out.println("Mascara introducida: "+ mascara); //Comprobamos si la mascara introducida es correcta
			System.out.println("La IP es de clase " + clase_ip(ip[0])); //Imprimimos por pantalla la clase de ip: clase_ip(ip[0])
			System.out.println("Red: " + id_red(ip,mascara)); //Imprimimos por pantalla el identificador de red: id_red(ip,mascara)
			System.out.println("Difusión: " + difusión(ip,mascara)); //Imprimimos por pantalla el broadcast de red: difusión(ip,mascara)
			System.out.println("Número de IPs para host: " + numero_ips(mascara)); //Imprimimos por pantalla las ip disponibles para hosts: numero_ips(mascara)
		}catch(Exception e) {
			//Cualquier excepcion dentro del try se deberá a un error de formato al introducir los datos de entrada
			System.out.println("Formato entrada incorrecto (Formato: X.X.X.X <espacio> Mascara): "+e);
		}
	}
}
