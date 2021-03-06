import java.util.Arrays;
import java.util.Random;

/**
 * Created by OlavH on 8/29/2016.
 */
public class Array {

    public static boolean isSorted(int[] array){

        for (int i=1; i<array.length; i+=2){

            int n0 = array[i-1];
            int n1 = array[i];

            if (n0 > n1) return false;
        }
        return true;
    }

    public static int[] getRandomArray(int size, int min, int max){

        Random random = new Random();
        int diff = max-min;
        int[] array = new int[size];

        for (int i = 0; i < size; i++) array[i] = min + random.nextInt(diff+1);

        return array;
    }

    public static void main(String[] args) {

        int[] array = getRandomArray(10, 0, 9);
        System.out.println(Arrays.toString(array));
        System.out.println(isSorted(array));
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));
        System.out.println(isSorted(array));

    }

}
