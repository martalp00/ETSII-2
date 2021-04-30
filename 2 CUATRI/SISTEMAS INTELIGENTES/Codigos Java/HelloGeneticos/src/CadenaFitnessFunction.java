import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class CadenaFitnessFunction extends FitnessFunction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char[] _expected;

    public CadenaFitnessFunction(String desiredMessage) {
        _expected = desiredMessage.toCharArray();
    }

    /***********************************************************
     *  
     */
    @Override
    protected double evaluate(IChromosome aSubject) {
        int fitnessValue = 0;

        int length = aSubject.size();
        for (int i = 0; i < length; i++) {
            String actual = (String) aSubject.getGene(i).getAllele();
            if (actual.toCharArray()[0] == _expected[i]) {
                fitnessValue += 1;
            }
        }

        return fitnessValue;
    }

}