import java.util.Random;

/**
 * Created by OlavH on 8/22/2016.
 */
public class Rand {

    public static int[] getRandomValues(int size, int min, int max){

        Random random = new Random();
        int diff = max-min;

        int[] out = new int[size];

        for (int i=0; i<size; i++){

            out[i] = min + random.nextInt(diff);
        }
        return out;

    }

}
