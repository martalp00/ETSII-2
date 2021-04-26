/**
 * Created by ramosaurio on 5/06/17.
 */
package monitores;
public class main 
{
    private static final int NUM_CONSUMIDROES = 10;
    private static final int  NUM_PRODUCTORES = 10;
    private static final int SIZE = 6;

    public static void main (String[]args)
    {
        Thread[]consumidor = new Thread[NUM_CONSUMIDROES];
        Thread[]productor = new Thread[NUM_PRODUCTORES];
        SharedBuffer buffer = new SharedBuffer(SIZE);
        
        for(int i = 0; i<NUM_CONSUMIDROES; i++)
        {
            consumidor[i] = new Thread(new consumidor(i,buffer));
            consumidor[i].start();
        }
        
        for(int i = 0; i<NUM_PRODUCTORES; i++)
        {
            productor[i] = new Thread(new productor(buffer));
            productor[i].start();
        }

    }
}
