/**
 * Created by ramosaurio on 5/06/17.
 */
package monitores;

public class productor extends Thread 
{
    private SharedBuffer buffer;
    private int cPtr;
    private int length;

    public productor(SharedBuffer buffer)
    {
        this.buffer = buffer;
        length = buffer.getLength();
        cPtr = 0;
    }

    public void run()
    {
        while(true){
            buffer.producir(cPtr);
            cPtr = (cPtr + 1)%length;
        }
    }
}
