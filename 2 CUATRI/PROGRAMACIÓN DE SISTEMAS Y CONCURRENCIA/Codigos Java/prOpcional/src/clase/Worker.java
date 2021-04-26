package clase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class Worker extends SwingWorker<List<Integer>,Void>{
	private int n;
	private Panel panel;
	public Worker(int n,Panel panel){
		this.n = n;
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
	protected List<Integer> doInBackground() throws Exception {
		// TODO Auto-generated method stub
		List<Integer> lista = new ArrayList<Integer>();
		int c = 0;
		int pprimo = 1;
		while (!this.isCancelled() && c<n){
			if (esPrimo(pprimo)){
				c++;
				lista.add(pprimo);
			}
			pprimo++;
		}
		return lista;
	}
	
	public void done(){
		try {
			panel.listaPrimos(this.get());//no bloquea a la dispatcher
			panel.mensaje(n+" primos calculados");
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CancellationException ex){
			panel.mensaje("Worker cancelado");
		}
	}
}
