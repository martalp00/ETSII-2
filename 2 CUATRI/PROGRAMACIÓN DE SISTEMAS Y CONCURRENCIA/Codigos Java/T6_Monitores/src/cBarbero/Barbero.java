package cBarbero;

import java.util.Random;

public class Barbero extends Thread{
    private Random r = new Random();
    private Barberia barb;
    public Barbero(Barberia barb){
        this.barb = barb;
    }
   public void run(){
          while (true){
              try {
                   barb.siguiente();
                   Thread.sleep(r.nextInt(500));
                  //barbero pelando
                   barb.finPelar();
             } catch (InterruptedException e) {}
          }
    }
}
