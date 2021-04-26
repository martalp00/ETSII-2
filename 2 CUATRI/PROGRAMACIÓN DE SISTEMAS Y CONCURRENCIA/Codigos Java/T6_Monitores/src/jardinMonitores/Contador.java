package jardinMonitores;

public class Contador {
    private int cont = 0;
    
    public synchronized void inc() throws InterruptedException{
        cont++;
    }

    public int valor(){
          return cont;
    }
}
