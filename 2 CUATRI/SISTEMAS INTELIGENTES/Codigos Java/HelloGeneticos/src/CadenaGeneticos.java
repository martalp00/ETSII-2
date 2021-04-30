import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.StringGene;

public class CadenaGeneticos{

    private static final String MESSAGE = "Isco, bota de oro!";
    private static final String ALPHABET =   "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ,!";

    private static final int POBLACION = 10;//<---- Poblacion 
  /******************************************************
   *   
   * @param args
   * @throws Exception
   */
    public static void main(String[] args) throws Exception {
        long startTime = 0;
        long endTime = 0;
        CadenaGeneticos hello = new CadenaGeneticos();

        Genotype population = hello.create(POBLACION); 

        startTime = System.currentTimeMillis();
        IChromosome solution = null;
        int popIdx = 0;
        do {
            population.evolve(); //<---  Evolucion
            popIdx++;
            solution = population.getFittestChromosome();
            
            outputNotSolution(population, startTime, endTime, popIdx);     
            outputNotSolutionPopulation(population);
                
            
        } while (solution.getFitnessValue() != MESSAGE.length());
        endTime = System.currentTimeMillis();

        outputSolution(population, startTime, endTime, popIdx);
    }
/*******************************************
 * 
 * @param popSize
 * @return
 * @throws InvalidConfigurationException
 */
    private Genotype create(int popSize) throws InvalidConfigurationException {
        Configuration conf = new DefaultConfiguration();
      //  conf.setPreservFittestIndividual(true); //Determines whether to keep the fittest individual.

        FitnessFunction myFunc = new CadenaFitnessFunction(MESSAGE); //Funcion utilizada 
        conf.setFitnessFunction(myFunc);

        Gene stringGene = new StringGene(conf, 1, 1, ALPHABET); 
        //Creo un gen que sera una cadena con longitud minima y maxima de 1, y los valores validos son
        // los del alfabeto. 
        
        IChromosome sampleChromosome = new Chromosome(conf, stringGene, MESSAGE.length());
        // Creo un cromosoma de ejemplo de longitud igual que el mensaje solucion, 
        // con los genes que he creado antes.
        conf.setSampleChromosome(sampleChromosome);
        // Establablezo el cromosoma de ejemplo, como el inicial
        conf.setPopulationSize(popSize);
        // Establezo el tamanho de la poblacion
        
        

        modifyConfiguration (conf); // Modifica la configuracion crossover y mutation
        
        Genotype population;
        population = Genotype.randomInitialGenotype(conf);
        
       
        
        return population;
    }
 
   /**
 * @throws InvalidConfigurationException *******************
    * 
    */

    private void modifyConfiguration (Configuration conf) throws InvalidConfigurationException 
    {
        //DEFAULT    org.jgap.impl.CrossoverOperator;  org.jgap.impl.MutationOperator
    	
    	System.out.println("\n\n****CONFIGURACION INICIAL******************************");
    	System.out.println(conf);
    	System.out.println("\n\n**************************************");
        
    	conf.getGeneticOperators().clear();

        // org.jgap.impl.CrossoverOperator; 
       // CrossoverOperator cross = new CrossoverOperator(conf);
       // conf.addGeneticOperator(cross);
        
       
       
      // org.jgap.impl.MutationOperator
      
           MutationOperator mutation  = new MutationOperator(conf);
           conf.addGeneticOperator(mutation);
      
        
        	System.out.println("\n\n****CONFIGURACION FINAL******************************");
        	System.out.println(conf);
        	System.out.println("\n\n**************************************");
      
    
    }
/*****************************************
 * 
 * @param population
 * @param startTime
 * @param endTime
 * @param evolutionIdx
 */
    private static void outputSolution(Genotype population, long startTime,
            long endTime, int evolutionIdx) {
    	System.out.println("\n\n**************************************");
    			
    	System.out.println("Finalizado en la generacion " + evolutionIdx);

        long totalSeconds = (endTime - startTime);
        System.out.println("Tiempo Total evoucion: " + totalSeconds+ " milisegundos.");

        IChromosome solution = population.getFittestChromosome();
        int fitnessValue = (int) solution.getFitnessValue();
        System.out.println("La mejor solucion tiene valor fitness de " + fitnessValue);
        
        String message = getMessage(solution);
        System.out.println("Mensaje Objetivo para '" + MESSAGE + "':\t '" + message);
        
        
    }
/**************************
 *     
 * @param population
 * @param startTime
 * @param endTime
 * @param evolutionIdx
 */
    private static void outputNotSolution(Genotype population, long startTime,
            long endTime, int evolutionIdx) {
     
    	    	
    	IChromosome solution = population.getFittestChromosome();
        int fitnessValue = (int) solution.getFitnessValue();
                
    	String message = getMessage(solution);
        System.out.println("\n ***********************\n Generacion "+evolutionIdx+": Obtenido " + message+ " fitness: "+ fitnessValue+"\n ***********************");
        
    }
 /******************************
     * 
     * @param population
     * @param startTime
     * @param endTime
     * @param evolutionIdx
     */
   
    private static void outputNotSolutionPopulation(Genotype population) {
     
    	int size=population.getPopulation().size();
    	String message;
    	int fitnessValue;
    	int pop=0;
    	
    	List <IChromosome> ListSolution = population.getPopulation().getChromosomes();
    	for (IChromosome tempChr : ListSolution) {
    		message = getMessage(tempChr);
    	    fitnessValue = (int) tempChr.getFitnessValue();
    	    System.out.println("\t\tChromosoma "+pop+" : " + message+ " fitness: "+ fitnessValue);
    	    pop++;
		}
    	
    	
        
    }
/*********************************************************
 * 
 * @param solution
 * @return
 */
    private static String getMessage(IChromosome solution) {
        String result = ""; 

        int length = solution.size();
        for (int i = 0; i < length; i++) {
            result += (String) solution.getGene(i).getAllele();
        }

        return result;
    }
}