package pr5;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class ClienteUDP 
{
    public static void main(String[] args) throws IOException 
    {
    	int puerto = 12345;  //Recibe el puerto
        
    	// Creating Socket using an temporal port
        try (Scanner scan = new Scanner(System.in))	//Leemos de entrada una cadena con la ip y el puerto
        {
        	System.out.println("Introduce IP para establecer conexion: ");
        	InetAddress IPdefault = InetAddress.getByName(scan.next()); // Recibe la IP del servidor
        	
	        DatagramSocket socket = new DatagramSocket();  
	        System.out.println("Conexion establecida con el cliente UDP");
	        DatagramPacket datagram;
	        String cadena="", respuesta="";  //Inicializamos la cadena y el mensaje respuesta a un String vacío
	        Scanner lee = new Scanner(System.in);
	      
	        System.out.println("Introduzca una de las tres opciones: B, L o F: ");     
	        String imput= lee.nextLine();  //Leemos la opcion elegida
	      
	        while (!imput.equals("F"))		//Mientras la entrada no sea F
	        { 
	        	System.out.println("Introduzca cadena a modificar: ");
	        	respuesta = imput + lee.nextLine(); //Mandamos al servidor la entrada junto con la cadena
	        	
	        	datagram = new DatagramPacket(respuesta.getBytes(), respuesta.getBytes().length, IPdefault, puerto); 
	        	socket.send(datagram);      //Enviamos el mensaje
	        	socket.receive(datagram);  // Espero el siguiente mensaje
	        	cadena=new  String(Arrays.copyOfRange(datagram.getData(), datagram.getOffset(),  datagram.getLength() + datagram.getOffset()));  //Montamos la respuesta del servidor
		       
	        	System.out.println("Mensaje recibido del servidor:  " + cadena);      
		        System.out.println("Introduzca una de las tres opciones: B, L o F: ");
		       
		        imput= lee.nextLine(); 
	        }
		   
		    socket.close(); //Cerramos todo
		    lee.close(); 
		    System.out.println("Conexión finalizada");
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
            System.err.println("ERROR");
            System.exit(1);
        } 
    }
}

