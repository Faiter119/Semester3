import java.util.HashMap;
import java.util.Map;

public class StochasticVariable {

    private double[] functionValues;
    private double[] probabilities;

    private int values;

    private Map<Double, Double> fxMap;

    public StochasticVariable(double[] functionValues, double[] probabilities){

        if (functionValues.length != probabilities.length) throw new IllegalArgumentException("Length of tables does not match");

        this.functionValues = functionValues;
        this.probabilities = probabilities;

        values = functionValues.length;

        double diff = 1d-totalProbability();
        if ( diff > (0.01) || diff < (-0.01)) throw new IllegalArgumentException("Total Probability not 1.0");

        fxMap = new HashMap<>();
        for (int i=0; i<values; i++) {
            fxMap.put(functionValues[i], probabilities[i]); // autobox
        }
    }

    public double totalProbability(){
        double out = 0;

        for (double d : probabilities) {
            out += d;
        }
        return out;
    }

    /**
     * Gets the element at the INDEX in the array
     */
    public double P(int x){
        if (x >= values) throw new IllegalArgumentException();

        return probabilities[x];
    }

    /**
     * Gets the element at the specified x
     */
    public double P(double x){
        if (!fxMap.containsKey(x)) throw new IllegalArgumentException();
        return fxMap.get(x);
    }

    /**
     * Probability of the outcomes in the specified range, both inclusive
     */
    public double P(double from, double to){
        int start = findMaxIndexOfFunctionValue(from)-1; // -1 because the method finds the index the from is bigger than, not in
        int end = findMaxIndexOfFunctionValue(to);

        double sum = 0;

        for (int i = start; i < end; i++) {

            sum += probabilities[i];
        }
        return sum;
    }

    /**
     * Gets the total probability from 0 -> x
     * Sannsynighetsfordeling
     */
    public double distribution(double x){

        int maxIndexInclusive = findMaxIndexOfFunctionValue(x);
        double sum = 0;

        for (int i = 0; i < maxIndexInclusive; i++) {
            sum += probabilities[i];
        }
        return sum;
    }

    /**
     * Gets the combined "most probable" outcome
     */
    public double expectation(){
        // μ = E( X ) = SUM( g(xi)*P(X=xi) )

        double sum = 0;

        for (int i = 0; i < values; i++) {

            sum += probabilities[i]*functionValues[i];

        }
        return sum;

    }

    /**
     * Gets the combined most probable deviance from the expected result
     */
    public double deviance(){
        // Var( X ) = SUM( ( xi - μ )^2 * P( X = xi ) )
        double μ = expectation();

        double sum = 0;

        for (int i = 0; i < values; i++) {

            sum += probabilities[i] * ( Math.pow(functionValues[i]-μ, 2) ); // not sure why ^2
        }

        return sum;
    }

    /**
     * Not sure how this is different from deviance.. it's smaller I suppose
     */
    public double standardDeviance(){
        return Math.sqrt(deviance());
    }

    /**
     * Finds in which index the specified x-value is stored, help method
     */
    private int findMaxIndexOfFunctionValue(double x){

        if ( x > functionValues[values-1] ) return values;

        for (int i = 0; i < values; i++) {
            //System.out.println(i);
            if( functionValues[i] > x ) return i;

        }
        throw new IllegalArgumentException("x not in range");
    }


    public static void main(String[] args) {

        StochasticVariable variable = new StochasticVariable(
                new double[]{     0d,   0.3d,   0.5d,   0.6d,   0.7d,   0.8d,   1.0d,   1.2d,   1.6d }, // Function Values
                new double[]{ 1d/28d, 2d/28d, 5d/28d, 8d/28d, 5d/28d, 3d/28d, 2d/28d, 3d/56d, 1d/56d }  // Probabilities
        );

        System.out.println("Total Probability: \t"    +variable.totalProbability());
        System.out.println("Distribution: \t\t"       +variable.distribution(0d));
        System.out.println("Expectation: \t\t"        +variable.expectation());
        System.out.println("Deviance: \t\t\t"         +variable.deviance());
        System.out.println("Standard Deviance: \t"    +variable.standardDeviance());

        System.out.println("P(0.7 <= X <= 1.2): "    +variable.P(0.7d, 1.2d));
    }
}
