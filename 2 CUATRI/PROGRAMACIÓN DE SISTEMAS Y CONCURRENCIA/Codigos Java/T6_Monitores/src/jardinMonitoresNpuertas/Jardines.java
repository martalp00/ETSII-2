package jardinMonitoresNpuertas;

public class Jardines {
	private static int VISITANTES = 2000;
	private static int NPUERTAS = 10;
    public static void main(String[] args){
      Contador visitantes = new Contador();   //Entidad pasiva. Recurso compartido
      Puerta [] puertas = new Puerta[NPUERTAS]; //Array de puertas 
      for (int i=0; i<NPUERTAS; i++){
    	  puertas[i] = new Puerta("P"+i,visitantes,VISITANTES);
      }
      for (int i=0; i<NPUERTAS; i++){
    	  puertas[i].start();
      }
      for (int i=0; i<NPUERTAS; i++){
    	  try {
			puertas[i].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      /*Puerta p1 = new Puerta("P1",visitantes,VISITANTES); 
      Puerta p2 = new Puerta("P2",visitantes,VISITANTES);

         p1.start(); //Entidad activa
         p2.start(); //Entidad activa

         try{
            p1.join();
            p2.join();
         } catch (InterruptedException e){
                  System.out.println("La hebra ha sido interrumpida");
         }*/
         System.out.println("\nEl numero de visitantes contabilizado es " + visitantes.valor());
         System.out.println("\nDeberian ser " + (VISITANTES*NPUERTAS));
         System.out.println("La diferencia es: " + (VISITANTES*NPUERTAS - visitantes.valor()));
   }
}
