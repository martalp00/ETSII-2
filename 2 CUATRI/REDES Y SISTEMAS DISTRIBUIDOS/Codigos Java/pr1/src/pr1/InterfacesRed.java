package pr1;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class InterfacesRed 
{
	public static void main(String[] args) 
	{
		Enumeration<NetworkInterface> listaInterfaz;
		
		try 
		{							            
			listaInterfaz = NetworkInterface.getNetworkInterfaces(); 	
			
			while(listaInterfaz.hasMoreElements()) 
			{
				NetworkInterface interfaz = listaInterfaz.nextElement();  
				StringBuilder sb = new StringBuilder();	
				
				if(interfaz.isUp() && interfaz != null)
				{
					byte[] dirMac=interfaz.getHardwareAddress();   
						
					if(dirMac!=null) 
					{
						int i = 0, longitud = dirMac.length;
						
						while(i < longitud)
						{
							if(i != (longitud - 1))
							{
								sb.append(String.format("%02X:", dirMac[i]));  
							}
							else 
							{
								sb.append(String.format("%02X", dirMac[i])); 
							}
							
							i++;
						}
						System.out.println("Interfaz " + interfaz.getName()+ ": MAC = " + sb.toString());
					}
				}
			}
		} 
		catch (SocketException se) 
		{
			System.out.println(se.toString());  
		}
	}
}
