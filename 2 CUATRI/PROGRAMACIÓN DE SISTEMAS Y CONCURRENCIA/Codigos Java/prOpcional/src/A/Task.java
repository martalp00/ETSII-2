package A;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;


public class Task extends SwingWorker<List<String>, Void>
{	
	private File directorio;
	private Panel panel;
	
	public Task(File f, Panel p) 
	{
		directorio = f;
		panel = p;
	}
	
	private static double getTotalLuminance(BufferedImage image) 
	{
		final double GAMMA = 2.2;
		double ret = 0.0;
		for (int x = 0; x < image.getWidth(); x++) 
		{
			for (int y = 0; y < image.getHeight(); y++) 
			{
				final int rgb = image.getRGB(x, y);
				final double red = ((rgb & 0x00ff0000) >> 16) / 255;
				final double green = ((rgb & 0x0000ff00) >> 8) / 255;
				final double blue = (rgb & 0x000000ff) / 255;
				final double linearGray = .2126 * Math.pow(red, GAMMA)	+ .7152 * Math.pow(green, GAMMA)	+ .0722 * Math.pow(blue, GAMMA);
				ret += 100.0 * linearGray;
			}
		}
		return ret;
	}

	@Override
	protected List<String> doInBackground() throws Exception //Del campus
	{
		List<String> lista = new ArrayList<>();
		BufferedImage image;
		File[] files = directorio.listFiles();
		int numFilesToManage = files.length;
		// Muestra el nombre de los ficheros y los procesa
		
		for (int i=0; i<numFilesToManage; i++)
		{
			try 
			{
				image = ImageIO.read(files[i]);
				double luminance = getTotalLuminance(image);
				double percentageGray = ( 100 - (luminance / (image.getWidth() * image.getHeight())));
				String data = files[i].getName()	+" "+image.getWidth()+"x"+image.getHeight()	+": "+String.format("%.2f", percentageGray)+"%";
				lista.add(data);
			}
			catch (Exception x) // Por si el fichero no es una imagen
			{ 
				numFilesToManage--;
				continue;
			}
		}
		return lista;
	}
	
	public void done() //Del campus
	{
		try 
		{
			panel.addImagenes(this.get());
			panel.mensaje("Imagenes procesadas correctamente");
		} 
		catch (InterruptedException | ExecutionException e) 
		{
			e.printStackTrace();
		}
	}
	
}
