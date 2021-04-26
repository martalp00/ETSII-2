package A;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Driver //Del campus
{
	public static void crearGUI(JFrame ventana)
	{
		Panel panel = new Panel();
		Controller ctr = new Controller(panel);
		panel.controlador(ctr);
		ventana.setContentPane(panel);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.pack();
		ventana.setVisible(true);
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame ventana = new JFrame("IMPRESORA");
				crearGUI(ventana);
			}
		});
	}
}
