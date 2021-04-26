package lectoresEscritoresJustoEficiente;

public class Condicion {
	public synchronized void cwait() throws InterruptedException{
		this.wait();
	}
	
	public synchronized void cnotify(){
		this.notify();
	}
	
	public synchronized void cnotifyAll(){
		this.notifyAll();
	}
}
