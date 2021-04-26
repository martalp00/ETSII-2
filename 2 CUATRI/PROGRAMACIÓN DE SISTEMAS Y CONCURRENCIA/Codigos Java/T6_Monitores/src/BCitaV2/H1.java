package BCitaV2;

import java.util.Random;

public class H1 extends Thread{
    private Random r = new Random();
    private Cita sinc = new Cita();
    public H1(Cita sinc){
        this.sinc = sinc;
    }
    public void run(){
           while (true){
           try {
                   Thread.sleep(r.nextInt(200));
                   int v1 = r.nextInt(100);
                   int v2 = sinc.intercambiaV1(v1);
                   System.out.println("H1. v1 = " + v1 + " v2 = " + v2);
             } catch (InterruptedException e) {}
       }
    }
}