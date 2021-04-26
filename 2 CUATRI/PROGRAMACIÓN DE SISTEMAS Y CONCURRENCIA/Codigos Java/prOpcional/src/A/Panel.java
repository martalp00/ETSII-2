package A;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Panel extends JPanel
{
	private JTextArea areaFinal = new JTextArea(40, 55);
	private JScrollPane scrolling = new JScrollPane(areaFinal);
	
	private JLabel mensaje = new JLabel("Status:");
	private JButton empezar = new JButton("Start");
	
	private JLabel directorio = new JLabel("Selecciona un directorio");
	private JButton carpeta = new JButton("Seleccionar carpeta");
	
	public Panel() 
	{
		setLayout(new BorderLayout());
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1,2));
		panel1.add(carpeta, 0, 0);
		panel1.add(directorio, 0, 1);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,2));
		panel2.add(scrolling, 0, 0);
		panel2.add(empezar, 0, 1);
		
		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.CENTER);
		this.add(mensaje, BorderLayout.SOUTH);
		
		empezar.setEnabled(false);
	}
	
	public void controlador(ActionListener ctr) 
	{
		carpeta.addActionListener(ctr);
		carpeta.setActionCommand("DIRECTORIO");
		empezar.addActionListener(ctr);
		empezar.setActionCommand("EMPEZAR");
	}
	
	public void mensaje(String texto) 
	{
		mensaje.setText(texto);
	}
	
	public void directorio(String texto) 
	{
		directorio.setText(texto);
	}
	public void empezarEnable(boolean activado) 
	{
		empezar.setEnabled(activado);
	}
	
	public void addImagenes(List<String> imagenes) 
	{
		for(String imagen: imagenes) 
		{
			areaFinal.append(imagen.toString()+"\n");
		}
	}
	
	public void resetAreaTexto() 
	{
		areaFinal.setText("");
	}
}
