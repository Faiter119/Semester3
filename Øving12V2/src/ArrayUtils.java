import java.util.Arrays;

/**
 * Created by OlavH on 31-Oct-16.
 */
public class ArrayUtils {

    public static boolean contains(byte[] source, byte[] search) {
        return indexOf(source, search) > -1;
    }

    /**
     * Den enkle, naive måten. TODO implemente Boyer Moore, upassende tegn, og passende endelse
     */
    public static int indexOf(byte[] source, byte[] search) {

        if (source.length <= 0 || search.length <= 0) return -1;//throw new IllegalArgumentException("<= 0");

        byte[] subSource = new byte[search.length];

        for (int i = 0; i <= source.length-search.length; i++) { // <= for ellers får man ikke med den siste

            System.arraycopy(source, i, subSource, 0, search.length);

            if (equals(subSource, search)) return i;

        }

        return -1;
    }

    public static boolean equals(byte[] source, byte[] search) {
        return Arrays.equals(source, search);
    }

    public static byte[][] partition(byte[] array, int size){

        int parts = (int)Math.ceil((double) array.length / (double) size); // Runder opp for å få plass til alt

        byte[][] partitions = new byte[parts][size];

        int remaining = array.length;
        int counter = 0;
        for (int i = 0; i < array.length; i+= size) {

            byte[] part = new byte[remaining > size ? size : remaining]; // hvis det er igjen færre elementer enn size, bruk remaining istedet
            System.arraycopy(array, i, part,0,remaining > size ? size : remaining); // -||-
            partitions[counter++] = part;

            remaining -= size;

        }

        return partitions;

    }

    public static byte[] build(byte[][] partitions){

        int size = 0;

        for (byte[] partition : partitions) {
            size += partition.length;
        }

        byte[] out = new byte[size];

        int elements = 0;
        for (byte[] partition : partitions) {

            System.arraycopy(partition,0,out,elements,partition.length);
            elements += partition.length;

        }



        return out;

    }
    public static byte[] trim(byte[] array){

        int nonZeros = 0;

        for (byte b : array) {

            if ( b != 0) nonZeros++;

        }

        byte[] out = new byte[nonZeros];

        for (int i = 0, element = 0; i < array.length; i++) {

            if (array[i] != 0)

            out[element++] = array[i];

        }
        return out;

    }

    public static byte[] trim(byte[] array, int startInclusive, int endInclusive){

        int toTrim = endInclusive-startInclusive + 1;
        System.out.println(toTrim);

        byte[] out = new byte[array.length - toTrim];


        int counter = 0;
        for (int i = 0; i < array.length; i++) {

            if (i < startInclusive || i > endInclusive){

                out[counter++] = array[i];

            }

        }

        return out;

    }

    public static void main(String[] args) {

        byte[] largeArray = new byte[]{0,1,2,3,4};

        byte[] search = new byte[]{3};

       /* System.out.println(indexOf(largeArray, search));

        System.out.println(Arrays.deepToString(partition(largeArray, 2)));

        byte[][] partitions = partition(largeArray,2);

        System.out.println(Arrays.toString(build(partitions)));*/

        System.out.println(Arrays.toString(trim(largeArray, 1, 3)));

    }

}
