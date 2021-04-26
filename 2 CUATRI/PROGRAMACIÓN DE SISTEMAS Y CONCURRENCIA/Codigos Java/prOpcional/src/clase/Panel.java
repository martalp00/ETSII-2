package clase;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Panel extends JPanel
{
	private JButton si = new JButton("sí");
	private JButton no = new JButton("no");
	private JLabel ms = new JLabel("GUI creada");
	private JTextArea area = new JTextArea(20,40);
	private JScrollPane scrol = new JScrollPane(area);
	private JLabel primos = new JLabel("Primos?");
	private JTextField nprimos = new JTextField(10);
	private int cont = 0;
	private JButton cancelar = new JButton("Cancelar");
	
	public Panel(){
		this.setLayout(new BorderLayout());
		JPanel pnorte1 = new JPanel();
		pnorte1.add(si);pnorte1.add(no);
		JPanel pnorte2 = new JPanel();
		pnorte2.add(primos);pnorte2.add(nprimos);
		pnorte2.add(cancelar);
		JPanel pnorte = new JPanel();
		pnorte.setLayout(new GridLayout(2,1));
		pnorte.add(pnorte1);
		pnorte.add(pnorte2);
		this.add(pnorte,BorderLayout.NORTH);
		this.add(scrol,BorderLayout.CENTER);
		this.add(ms,BorderLayout.SOUTH);
	}
	
	public void controlador(ActionListener ctr){
		si.addActionListener(ctr);
		si.setActionCommand("SI");
		no.addActionListener(ctr);
		no.setActionCommand("NO");
		nprimos.addActionListener(ctr);
		nprimos.setActionCommand("PRIMOS");
		cancelar.addActionListener(ctr);
		cancelar.setActionCommand("CANCELAR");
	}
	
	public void siPulsado(){
		ms.setText("Sí pulsado");
	}

	public void noPulsado(){
		ms.setText("No pulsado");
	}
	public void mensaje(String mens){
		ms.setText(mens);
	}
	public int numero(){
		return Integer.parseInt(nprimos.getText());
	}
	
	public void listaPrimos(List<Integer> lista){
		for (int i:lista){
			area.append(Integer.toString(i)+" ");
			cont++;
			if (cont%20==0){
				area.append("\n");
			}
		}
	}
	public void limpiar(){
		area.setText("");
		cont = 0;
	}
}
