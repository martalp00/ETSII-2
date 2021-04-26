package pr4;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

class ServidorTCP
{
	public static void main(String[] args) throws IOException 
	{
		try 
		{
			ServerSocket server = new ServerSocket(12345,1);
			
			while(true) 
			{
		    	System.out.println("Conectando");
		    	Socket client = server.accept();
		    	System.out.println("Cliente aceptado");
		    	
		    	BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		        out.println("Bienvenido al servicio de manipulacion de textos");
		        String imput = in.readLine();
		       
		        while(!imput.equals("F")) 
		        {		    
		        	String cadena = in.readLine();
		        	System.out.println("Enviando");
		            
		        	if(imput.equals("B")) 
		        	{
		            	cadena = eliminarEspacios(cadena);	            	  
		            	out.println(modificarPosicion(cadena));	
		            	
		            }
		        	else if(imput.equals("L"))
		        	{
		        		out.println(eliminarEspacios(cadena));
		            } 
		        	else 
		        	{
		            	 System.out.println("Opcion invalida");
		            }
		        	imput = in.readLine();
		        }
		       
		        out.println("VALE");
		        in.close();
		        out.close();
		        server.close();
		       
		     
		        client.close();
			}
		}
		catch (Exception e)
		{
			e.getMessage();
    		e.printStackTrace();
			System.out.println("ERROR No se puede acceder al servidor");
            System.exit(-1);
		}
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
}
