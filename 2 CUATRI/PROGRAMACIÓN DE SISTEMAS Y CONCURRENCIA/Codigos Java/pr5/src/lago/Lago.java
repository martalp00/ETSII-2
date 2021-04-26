package lago;

public class Lago 
{
	private volatile int  nivel = 0;
	private volatile boolean brio = false,bpresa=false;
	private volatile int turnopr = 0; //0-rio, 1-presa
	
	private volatile boolean bpresa0 = false, bpresa1=false;
	private volatile int turnop=0;
	
	public void incRio()
	{
		brio =true;
		turnopr = 1;
		while (bpresa && turnopr == 1) Thread.yield();
		
		nivel++;
		
		brio =false;
	}
	
	public void decPresa0()
	{
		//nivel = 1
		
		
		
		bpresa0=true;
		turnop = 1;
		while (bpresa1 && turnop==1) Thread.yield();
		
		while (nivel==0) Thread.yield(); //CS-Presa
		
		bpresa = true;
		turnopr = 0;
		while (brio && turnopr==0) Thread.yield();
		
		nivel--;
		
		bpresa = false;
		bpresa0 = false;
	}
	
	public void decPresa1(){
		
		
		bpresa1 = true;
		turnop = 0;
		while (bpresa0 && turnop==0) Thread.yield();
		
		while (nivel==0) Thread.yield(); //CS-Presa
		
		bpresa = true;
		turnopr = 0;
		while (brio && turnopr==0) Thread.yield();
		
		nivel--;
		
		bpresa = false;
		bpresa1 = false;
	}
	
	public int nivel(){
		return nivel;
	}
}

//exclusion mutua Peterson
//CS-presa
