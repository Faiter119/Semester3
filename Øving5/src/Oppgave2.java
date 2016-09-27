import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by OlavH on 19-Sep-16.
 */
public class Oppgave2 {

    public static int[] getRandomTable(int size, int min, int max){

        int[] out = new int[size];
        int diff = max-min;
        Random random = new Random();

        for (int i = 0; i < size; i++) {

            out[i] = min + random.nextInt(diff);

        }
        return out;
    }


    public static void main(String[] args) {

        int[] randomIntTable = getRandomTable(10000 000, Integer.MIN_VALUE/2, Integer.MAX_VALUE/2);

        // HashMap timing
        long start = System.nanoTime();
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int i : randomIntTable) {
            hashMap.put(i,i);
        }
        long end = System.nanoTime();
        System.out.println("HashMap Time: "+(end-start));
        // HashMap timing

        // My map timing
        start = System.nanoTime();
        IntegerHashTable stringHashTable = new IntegerHashTable();
        for (int i : randomIntTable) {
            stringHashTable.add(i);
        }
        end = System.nanoTime();
        System.out.println("My map Time:  "+(end-start));
        // My map timing

        System.out.println("Collisions: "+stringHashTable.getCollisions());

        System.out.println("Factor: "+stringHashTable.loadFactor());
    }
}

