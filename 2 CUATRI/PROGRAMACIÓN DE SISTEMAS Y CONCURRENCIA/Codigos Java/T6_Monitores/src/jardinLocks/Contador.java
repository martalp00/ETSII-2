package jardinLocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Contador {
    private int cont = 0;
    private Lock l = new ReentrantLock();
    
    public void inc() throws InterruptedException{
        l.lock();
    	cont++;
    	l.unlock();
    }

    public int valor(){
          return cont;
    }
}
