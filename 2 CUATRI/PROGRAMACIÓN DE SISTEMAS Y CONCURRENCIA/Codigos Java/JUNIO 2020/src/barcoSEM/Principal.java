package barcoSEM;

public class Principal 
{

	public static void main(String[] args) throws InterruptedException 
	{
		int N = 5;
		Meta meta = new Meta(N);
		Arbitro a = new Arbitro(meta);
		Veleros[] vel = new Veleros[N];
		
		for (int i=0; i<vel.length; i++)
		{
			vel[i] = new Veleros(i,meta);
		}
		a.start();
		for (int i=0; i<vel.length; i++)
		{
			vel[i].start();
		}
		for (int i=0; i<vel.length; i++)
		{
			vel[i].join();
		}
		
		a.join();
		meta.resultados();
	}

}
