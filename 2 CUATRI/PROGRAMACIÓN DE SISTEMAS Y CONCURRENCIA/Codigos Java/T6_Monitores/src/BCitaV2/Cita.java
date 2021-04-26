package BCitaV2;

public class Cita {
	int v1, v2;
	boolean fin1= false, fin2 = false;

    public synchronized int intercambiaV1(int v) throws InterruptedException{
    	System.out.println("Llega H1");
        v1 = v;
        fin1 = true;
        notify(); //avisa por si la otra hebra esta esperando
        
        while (!fin2) {
    	   	System.out.println("	Espera a H2");
    		wait();
    	}
        fin2 = false;
        return v2;
    }
    public synchronized int intercambiaV2(int v) throws InterruptedException{
    	System.out.println("Llega H2");
    	v2 = v;
        fin2 = true;
        notify();
        
        while (!fin1) {
        	System.out.println("	Espera a H1");
    		wait();
    	}
        fin1 = false;
        return v1;
    }
}
