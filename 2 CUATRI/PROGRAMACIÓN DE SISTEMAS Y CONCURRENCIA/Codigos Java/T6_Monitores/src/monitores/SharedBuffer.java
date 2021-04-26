package monitores;
import java.util.Random;

/**
 * Created by ramosaurio on 5/06/17.
 */
public class SharedBuffer 
{

    private int [] buffer;
    private int [] bufferCounter;
    private int size;
    private int numTotal;
    private Random rnd;

    public SharedBuffer(int size)
    {
        this.size = size;
        buffer = new int[size];
        bufferCounter = new int[size];
        rnd = new Random();
        numTotal = 0;
    }

    /*
        @cPtr : es el puntero del buffer, si este esta usado
        se incrementará desde el método run() del productor;
     */
    public synchronized void producir(int cPtr){
        while(numTotal == size){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(buffer[cPtr]==0){
            buffer[cPtr] = (rnd.nextInt(20) + 1);
            numTotal++;
            notifyAll();
        }
    }
    /*
        @pPtr : es el puntero del buffer, del elemento a consumir
     */
    public synchronized int consumir(int pPtr){
        int valor = buffer[pPtr];
            try {
                while(buffer[pPtr] == 0)
                  wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        if(bufferCounter[pPtr]==2){
            bufferCounter[pPtr] = 0;
            numTotal--;
            notifyAll();
        }else{
            bufferCounter[pPtr]++;
        }

        return valor;
    }

    public int getLength(){
        return size;
    }

}
