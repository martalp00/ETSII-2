package es.uma.rysd.app;

import javax.net.ssl.HttpsURLConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.google.gson.Gson;

import es.uma.rysd.entities.*;

public class SWClient 
{
	// TODO: Complete el nombre de la aplicaci�n
    private final String app_name = "";
    
    private final String url_api = "https://swapi.dev/api/";

    // M�todos auxiliares facilitados
    
    // Obtiene la URL del recurso id del tipo resource
	public String generateEndpoint(String resource, Integer id){
		return url_api + resource + "/" + id + "/";
	}
	
	// Dada una URL de un recurso obtiene su ID
	public Integer getIDFromURL(String url){
		String[] parts = url.split("/");

		return Integer.parseInt(parts[parts.length-1]);
	}
	
	// Consulta un recurso y devuelve cu�ntos elementos tiene
	public int getNumberOfResources(String resource)
	{
		String nombre = url_api + resource + "/";
		int num = 0;
		
		try	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
		{
			// TODO: Cree la URL correspondiente: https://swapi.dev/api/{recurso}/ reemplazando el recurso por el par�metro �
			URL dir = new URL(nombre);
			
			// TODO: Cree la conexi�n a partir de la URL
			HttpsURLConnection conexion = (HttpsURLConnection)dir.openConnection();
			
			// TODO: A�ada las cabeceras User-Agent y Accept (vea el enunciado)
	    	
	    	conexion.addRequestProperty("User-Agent", app_name);
	    	conexion.addRequestProperty("Accept", "application/json");
	    	
	    	// TODO: Indique que es una petici�n GET
	    	conexion.setRequestMethod("GET");
	    	
	    	// TODO: Compruebe que el c�digo recibido en la respuesta es correcto
	    	// TODO: Deserialice la respuesta a CountResponse
	    	Gson parser = new Gson();
	        InputStream in = conexion.getInputStream(); // TODO: Obtenga el InputStream de la conexi�n
	        CountResponse c = parser.fromJson(new InputStreamReader(in), CountResponse.class);
	        num = c.count;
	        conexion.disconnect();
		}
    	catch(MalformedURLException e)
		{
    		System.err.println("URL incorrecta");
    		return 0;
		}
		catch(IOException e)
		{
			System.err.println(e);
			System.err.println("No se puede conectar a la URL");
			return 0;
		}
    	 
        // TODO: Devuelva el n�mero de elementos
        return num;
	}
	
	public int getHeightOfPerson(String people)
	{
		String nombre = url_api + people + "/";
		int num = 0;
		
		try	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
		{
			// TODO: Cree la URL correspondiente: https://swapi.dev/api/{recurso}/ reemplazando el recurso por el par�metro �
			URL dir = new URL(nombre);
			
			// TODO: Cree la conexi�n a partir de la URL
			HttpsURLConnection conexion = (HttpsURLConnection)dir.openConnection();
			
			// TODO: A�ada las cabeceras User-Agent y Accept (vea el enunciado)
	    	conexion.addRequestProperty("User-Agent", app_name);
	    	conexion.addRequestProperty("Accept", "application/json");
	    	
	    	// TODO: Indique que es una petici�n GET
	    	conexion.setRequestMethod("GET");
	    	
	    	// TODO: Compruebe que el c�digo recibido en la respuesta es correcto
	    	// TODO: Deserialice la respuesta a CountResponse
	    	Gson parser = new Gson();
	        InputStream in = conexion.getInputStream(); // TODO: Obtenga el InputStream de la conexi�n
	        CountResponse c = parser.fromJson(new InputStreamReader(in), CountResponse.class);
	        num = c.count;
	        conexion.disconnect();
		}
    	catch(MalformedURLException e)
		{
    		System.err.println("URL incorrecta");
    		return 0;
		}
		catch(IOException e)
		{
			System.err.println(e);
			System.err.println("No se puede conectar a la URL");
			return 0;
		}
    	 
        // TODO: Devuelva el n�mero de elementos
        return num;
	}
	
	public People getPerson(String urlname)
	{
    	People p = null;
    	// Por si acaso viene como http la pasamos a https
    	urlname = urlname.replaceAll("http:", "https:");

    	try	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
		{
			// TODO: Cree la URL correspondiente: https://swapi.dev/api/{recurso}/ reemplazando el recurso por el par�metro �
			URL dir = new URL(urlname);
			
			// TODO: Cree la conexi�n a partir de la URL recibida
			HttpsURLConnection conexion = (HttpsURLConnection)dir.openConnection();
			
			// TODO: A�ada las cabeceras User-Agent y Accept (vea el enunciado)
			// TODO: Indique que es una petici�n GET
	    	conexion.setRequestMethod("GET");
	    	conexion.addRequestProperty("User-Agent", app_name);
	    	conexion.addRequestProperty("Accept", "application/json");
	    
	    	// TODO: Compruebe que el c�digo recibido en la respuesta es correcto
	    	// TODO: Deserialice la respuesta a People
	    	Gson parser = new Gson();
	        InputStream in = conexion.getInputStream(); // TODO: Obtenga el InputStream de la conexi�n
	        People people = parser.fromJson(new InputStreamReader(in), People.class);
	        p = people;
	   	        
	        // TODO: Para las preguntas 2 y 3 (no necesita completar esto para la pregunta 1)
	    	// TODO: A partir de la URL en el campo homreworld obtenga los datos del planeta y almac�nelo en atributo homeplanet
	    	p.homeplanet = getPlanet(p.homeworld);
	    	conexion.disconnect();
		}
    	catch(MalformedURLException e)
		{
    		System.err.println("URL incorrecta");
    		return null;
		}
		catch(IOException e)
		{
			System.err.println(e);
			System.err.println("No se puede conectar a la URL");
			return null;
		}
    	
        
    	return p;
	}

	public Planet getPlanet(String urlname) 
	{
    	Planet p = null;
    	// Por si acaso viene como http la pasamos a https
    	urlname = urlname.replaceAll("http:", "https:");

    	try	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
		{
			// TODO: Cree la URL correspondiente: https://swapi.dev/api/{recurso}/ reemplazando el recurso por el par�metro �
			URL dir = new URL(urlname);
			
			// TODO: Cree la conexi�n a partir de la URL recibida
			HttpsURLConnection conexion = (HttpsURLConnection)dir.openConnection();
			
			// TODO: A�ada las cabeceras User-Agent y Accept (vea el enunciado)
			// TODO: Indique que es una petici�n GET
	    	conexion.setRequestMethod("GET");
	    	conexion.addRequestProperty("User-Agent", app_name);
	    	conexion.addRequestProperty("Accept", "application/json");
	    	
	    	// TODO: Compruebe que el c�digo recibido en la respuesta es correcto
	    	// TODO: Deserialice la respuesta a People
	    	
	    	System.out.println(urlname);
	    	Gson parser = new Gson();
	        InputStream in = conexion.getInputStream(); // TODO: Obtenga el InputStream de la conexi�n
	        Planet planet = parser.fromJson(new InputStreamReader(in), Planet.class);
	        p = planet;
	        conexion.disconnect();
		}
    	catch(MalformedURLException e)
		{
    		System.err.println("URL incorrecta");
    		return null;
		}
		catch(IOException e)
		{
			System.err.println(e);
			System.err.println("No se puede conectar a la URL");
			return null;
		}
    	
        // TODO: Para las preguntas 2 y 3 (no necesita completar esto para la pregunta 1)
    	// TODO: A partir de la URL en el campo homeworld obtenga los datos del planeta y almac�nelo en atributo homeplanet

        return p;
	}

	public People search(String name)
	{
    	People p = null;
    	
    	try	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
		{
			// TODO: Cree la URL correspondiente: https://swapi.dev/api/{recurso}/ reemplazando el recurso por el par�metro �
			URL dir = new URL(url_api + "people/?search=" + URLEncoder.encode(name, "utf-8"));
			
			// TODO: Cree la conexi�n a partir de la URL(url_api + name tratado - vea el enunciado)
			HttpsURLConnection conexion = (HttpsURLConnection)dir.openConnection();
			
			// TODO: A�ada las cabeceras User-Agent y Accept (vea el enunciado)
	    	conexion.addRequestProperty("User-Agent", app_name);
	    	conexion.addRequestProperty("Accept", "application/json");
	    	
	    	// TODO: Indique que es una petici�n GET
	    	conexion.setRequestMethod("GET");
	    	
	    	// TODO: Compruebe que el c�digo recibido en la respuesta es correcto
	    	// TODO: Deserialice la respuesta a SearchResponse -> Use la primera posici�n del array como resultado
	    	int responseCode = conexion.getResponseCode();
	    	
	    	if(responseCode/100 != 2)
	    	{
	    		System.err.println("Incorrecto: " + conexion.getResponseCode() + conexion.getResponseMessage());
	    		return null;
	    	}
	    	
	    	Gson parser = new Gson();
	    	InputStream in = conexion.getInputStream();
	        SearchResponse s = parser.fromJson(new InputStreamReader(in), SearchResponse.class);
	        
	        if(s.count == 0)
	        {
	        	return null;
	        }
	        
	        p = (People) s.results[0];
	        
	        // TODO: Para las preguntas 2 y 3 (no necesita completar esto para la pregunta 1)
	    	// TODO: A partir de la URL en el campo homreworld obtenga los datos del planeta y almac�nelo en atributo homeplanet
	        
	        p.homeplanet = getPlanet(p.homeworld);
	        conexion.disconnect();
	        
		}
    	catch(MalformedURLException e)
		{
    		System.err.println("URL incorrecta");
    		return null;
		}
		catch(IOException e)
		{
			System.err.println(e);
			System.err.println("No se puede conectar a la URL");
			return null;
		}
    	
        return p;
    }
}
