package A;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

public class Controller implements ActionListener
{
	private Panel panel;
	private Task task;
	private File directorio = null;
	
	public Controller(Panel p) 
	{
		panel = p;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("DIRECTORIO")) 
		{
			JFileChooser folder = new JFileChooser();
			int opcionEscogida = folder.showOpenDialog(panel); 
			folder.setFileSelectionMode(1);
	
			if(opcionEscogida == JFileChooser.APPROVE_OPTION) 
			{
				directorio = folder.getSelectedFile();
				panel.directorio(directorio.getAbsolutePath());
				panel.empezarEnable(true);
			}	
		}
		else if(e.getActionCommand().equals("EMPEZAR")) 
		{
			panel.resetAreaTexto();
			
			if(directorio != null) 
			{
				task = new Task(directorio, panel);
				task.execute();
				panel.mensaje("Procesando imagenes...");
			}
			else 
			{
				panel.mensaje("Directorio no seleccionado");
			}
		}
	}
	
}
