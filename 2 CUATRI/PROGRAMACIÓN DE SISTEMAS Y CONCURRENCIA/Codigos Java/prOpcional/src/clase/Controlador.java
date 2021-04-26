package clase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controlador implements ActionListener{

	private Panel panel;
	Worker w;
	public Controlador(Panel panel){
		this.panel = panel;
	}
	private boolean esPrimo(int n){
		if (n<=2) return true;
		else {
			int div = 2;
			boolean esprimo = true;
			while (div*div<=n && esprimo){
				if (n%div == 0) esprimo = false;
				else div++;
			}
			return esprimo;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("SI")){
			panel.siPulsado();
		} else if (e.getActionCommand().equals("NO")){
			panel.noPulsado();
		} else if (e.getActionCommand().equals("PRIMOS")){
			panel.limpiar();
			try{
				int n = panel.numero();
				w = new Worker(n,panel);
				w.execute();		
			}catch (IllegalArgumentException ex){
				panel.mensaje("Número incorrecto");
			}
		} else if (e.getActionCommand().equals("CANCELAR")){
			if (w!=null){
				w.cancel(true);
				panel.mensaje("Worker cancelado");
			} else {
				panel.mensaje("No se puede cancelar");
			}
		}
	}

}
