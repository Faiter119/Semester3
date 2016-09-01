import java.util.Arrays;

/**
 * Created by OlavH on 8/29/2016.
 */
public class Sorting {

    public static int[] shellSortOriginal(int[] array){

        int size = array.length;
        int start = size/2;

        while (start > 0){

            for (int i=start; i<size; i++){

                int j = i;
                int temp = array[i];

                while ( (j >= start) && (temp < array[j - start]) ){

                    array[j] = array[j-start];
                    j -= start;
                }
                array[j] = temp;
            }
            start = (start == 2) ? 1 : (int) (start / 2.2);
        }
        return array;
    }
    public static int[] shellSortVariableSplit(int[] array, double split){

        int size = array.length;
        int start = size/2; // På midten

        while (start > 0){ //O( (n/2)/split )

            for (int i=start; i<size; i++){ // *n

                int j = i;
                int temp = array[i];

                while ( (j >= start) && (temp < array[j - start]) ){

                    array[j] = array[j-start];
                    j -= start;
                }
                array[j] = temp;
            }
            start = (start == 2) ? 1 : (int) (start / split);
        }
        return array;
    }

    public static void main(String[] args) {

        int LOOPS = 10;
        int SIZE = 3000000;
        int MIN = 0;
        int MAX = 99;
        long start, end, diff;

        int[] randomArray = Array.getRandomArray(SIZE, MIN, MAX);

        // Variable Split ShellSort
        System.out.println("-- Shellsort Variable Split --");
        start = System.nanoTime();
        for (int i = 0; i < LOOPS; i++) {
            shellSortVariableSplit(Arrays.copyOf(randomArray, SIZE), 2.2    ); // bare 2.2[1-9] som var raskere, alle andre var tregere
        }
        end = System.nanoTime();
        diff = end-start;
        System.out.println("Time Total: "+diff + " - Time Per Loop: "+ diff/LOOPS);
        System.out.println("sorted: "+Array.isSorted(shellSortVariableSplit(Arrays.copyOf(randomArray, SIZE), 2.2)));
        // End

        // Original ShellSort
        System.out.println("-- Original Shellsort --");
        start = System.nanoTime();
        for (int i = 0; i < LOOPS; i++) {
            shellSortOriginal(Arrays.copyOf(randomArray, SIZE));
        }
        end = System.nanoTime();
        diff = end-start;
        System.out.println("Time Total: "+diff + " - Time Per Loop: "+ diff/LOOPS);
        System.out.println("sorted: "+Array.isSorted(shellSortOriginal(Arrays.copyOf(randomArray, SIZE))));
        // End



        // Arrays.sort
        System.out.println("-- Arrays.sort --");
        start = System.nanoTime();
        for (int i = 0; i < LOOPS; i++) {
            Arrays.sort(Arrays.copyOf(randomArray, SIZE));
        }
        end = System.nanoTime();
        diff = end-start;
        System.out.println("Time Total: "+diff + " - Time Per Loop: "+ diff/LOOPS);
        Arrays.sort(randomArray);
        System.out.println("sorted: "+Array.isSorted(randomArray));
        // End

    }
}