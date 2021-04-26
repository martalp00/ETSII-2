package cBarbero;

import java.util.Random;

public class Cliente extends Thread{
    private Random r = new Random();
    private Barberia barb;
    private int id;
    public Cliente(Barberia barb,int id){
              this.barb = barb;
              this.id = id;
     }
    public void run(){
           try {
               barb.cortar(id);
           } catch (InterruptedException e) {}
     }
}
