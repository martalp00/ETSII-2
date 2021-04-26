package BCitaV2;

import java.util.Random;

public class H2 extends Thread{
    private Random r = new Random();
    private Cita sinc = new Cita();
    public H2(Cita sinc){
        this.sinc = sinc;
    }
    public void run(){
           while (true){
           try {
                   Thread.sleep(r.nextInt(100));
                   Thread.sleep(r.nextInt(100));
                   int v2 = r.nextInt(100);
                   int v1 = sinc.intercambiaV2(v2);
                   System.out.println("H2. v1 = " + v1 + " v2 = " + v2);
             } catch (InterruptedException e) {}
       }
    }
}