/**
 * Created by ramosaurio on 5/06/17.
 */
package monitores;

public class consumidor extends Thread {

    private SharedBuffer buffer;
    private int cPtr;
    private int id;
    private int length;

    public consumidor(int id,SharedBuffer buffer){
        this.id = id;
        this.buffer = buffer;
        length = buffer.getLength();
        cPtr = 0;

    }

    public void run(){
        int valor;
        while(true){
            valor = buffer.consumir(cPtr);
            System.out.println("Consumidor "+id+" consume : "+valor);
            cPtr = (cPtr + 1)%length;
        }

    }
}
