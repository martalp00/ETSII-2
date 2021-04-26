package pr4;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP 
{
	public static void main(String[] args) throws IOException 
	{
		try 
		{
			Socket echoSocket = new Socket("127.0.0.1", 12345);
			System.out.println("Conexion establecida con el servidor");
			
			
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			System.out.println("Soy el cliente y esto es un envio");
			System.out.println("Mensaje del servidor: " + in.readLine());
			System.out.println();
			System.out.println("Introduzca una de las tres opciones: B, L o F: ");
	    	Scanner sc = new Scanner(System.in);
	    	String imput = sc.nextLine();
	    	
	    	while(!imput.equals("F")) 
	    	{
	    		out.print(imput+"\r\n");
	    		System.out.println("Introduzca cadena a modificar: ");
	    		out.println(sc.nextLine());
	    		System.out.println("...Enviando mensaje...");
	    		System.out.println("...Esperando respuesta...");
	    		String mensaje = in.readLine();       		
	    		System.out.println();
	    		System.out.println("Mensaje modificado: " + mensaje);
	    		System.out.println();
	    		System.out.println("Introduzca una de las tres opciones: B, L o F: ");
	    		imput = sc.nextLine();
	    	}
	    	out.println(imput);
			System.out.println("...Esperando respuesta...");
			System.out.println("VALE");
			sc.close();
			in.read();
			out.close();
			in.close();
			echoSocket.close();
		}
		catch (Exception e)
		{
			System.err.println("ERROR");
            System.exit(1);
		}
	}
}
