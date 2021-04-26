package pr5;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ServidorUDP 
{	
    public static void main(String[] args) throws IOException
    {
    	try 
    	{	
	        DatagramSocket socket = new DatagramSocket(12345); // Creating Socket using an specific port
	     
	        String wrongImput = "Error de entrada";
	        String cadena = "";
	        
			while (true)
			{
				// Receiving a message
				byte [] buffer = new byte[20]; 	//Buffer de entrada
				DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
			
				System.out.println("Conectando");
				socket.receive(datagram); 
				cadena=new  String(Arrays.copyOfRange(datagram.getData(), datagram.getOffset(),  datagram.getLength() + datagram.getOffset())); //Procesamos los bytes para pasarlo a formato String
		       
				System.out.println("Mensaje recibido del cliente: " + cadena);
		        InetAddress IPcliente = datagram.getAddress(); //Cogemos la IP del cliente para enviarle la respuesta de vuelta
		        
		        int puerto = datagram.getPort();  //Cogemos el puerto del cliente para enviarle la respuesta de vuelta
		       
		        if (cadena.charAt(0)=='L') 
		        {
		        	cadena = cadena.substring(1);  //Quitamos la L de la cadena
		        	cadena=eliminarEspacios(cadena);
		    		
		        	System.out.println("Mensaje enviado al cliente:" + cadena);
		    		
		        	buffer=cadena.getBytes();
		    		datagram =new DatagramPacket(buffer, buffer.length, IPcliente, puerto); //Enviamos el mensaje procesado al cliente de vuelta
		    		socket.send(datagram);
		    	}
		    	else if (cadena.charAt(0)=='B') 
		    	{
		    		cadena = cadena.substring(1);	//Quitamos la B de la cadena
		    		cadena = eliminarEspacios(cadena);
		    		cadena = modificarPosicion(cadena);
		    		
		    		System.out.println("Mensaje enviado al cliente:" + cadena);
		    		
		    		buffer=cadena.getBytes();
		    		datagram =new DatagramPacket(buffer, buffer.length, IPcliente, puerto);//Se envía el mensaje modificado
		    		socket.send(datagram);
		    	 
		    	}
		    	else //Si no se intrudice una de las letras anteriores se muestra un mensaje de error
		    	{
		    		buffer = wrongImput.getBytes();
		    		socket.send(datagram);
		    	}
			}
    	}
    	catch (Exception e ) 
    	{
    		e.getMessage();
    		e.printStackTrace();
    		System.out.println("ERROR");
    		System.exit(-1);
    	}
    }
    
    private static String eliminarEspacios(String cadena) 
	{
		String[] string = cadena.split("[  ]+");
        String res = "";
      
        for(int i=0; i<string.length; i++) 
        {
            res += string[i] + " ";
        }
        
        return res;
	}
    
    private static String modificarPosicion(String cadena) 
	{
		List<String> nuevaCadena = new ArrayList<String>();
    	Scanner sc = new Scanner(cadena);		            	
    	String res = "";
    	
    	while(sc.hasNext())
    	{
    		nuevaCadena.add(sc.next());
    	}		           		  
    	
    	Collections.shuffle(nuevaCadena);	
    	
    	for (String string : nuevaCadena) 
    	{
			res += string + " ";
		}
    	
    	sc.close();
    	return res;
	}
}