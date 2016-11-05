import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Created by OlavH on 31-Oct-16.
 */
public class Packer {

    private static final int MINIMUM_REPEATING_LENGTH = 3;

    private byte[] allBytes;

    public Packer(byte[] array) {

        allBytes = array;

    }

    public byte[] pack() {return pack(Byte.MAX_VALUE);}

    public byte[] pack(int partitionSize) {

        System.out.println("Compressing...");

        byte[][] partitions = ArrayUtils.partition(allBytes, partitionSize); // Søkerekkevidde

        byte[][] compressedPartitions = new byte[partitions.length][];

        for (int i = 0; i < partitions.length; i++) {

            compressedPartitions[i] = lempelZivCompress(partitions[i]);

        }

        byte[] build = ArrayUtils.build(compressedPartitions);

        System.out.println("Compressed: " + build.length + " - Old: " + allBytes.length);

        return build;
    }

    public byte[] lempelZivCompress(byte[] array) { // Lempel-ziv

        byte[] compressed = new byte[array.length]; // Kan ikke bli større enn originalen...

        int compressedCounter = 0; // indexen i compressed-arrayet, oppfører seg som en stack
        for (int i = 0; i < array.length; i++) {

            int lengthToCopy = MINIMUM_REPEATING_LENGTH > array.length - i ? array.length - i : MINIMUM_REPEATING_LENGTH;
            byte[] search = new byte[lengthToCopy];
            System.arraycopy(array, i, search, 0, lengthToCopy); // So it does not go out of bounds
            //System.out.println("search is: "+new String(search));

            if (ArrayUtils.contains(compressed, search)) {

                //System.out.println("Found "+ new String(search) + " in compressed\n\tCompressed is "+ Arrays.toString(compressed));

                search = findLargestRepeatable(array, compressed, search, i);

                // Generating and adding the code
                byte[] code = generateCode(compressed, search, compressedCounter);

                //System.out.println("Code is "+ Arrays.toString(code));
                compressed[compressedCounter++] = code[0];
                compressed[compressedCounter++] = code[1];

                i += search.length - 1; // Skipper forbi det du nettopp komprimerte
                //compressed = ArrayUtils.trim(compressed, i+1, i+search.length);

                //
            }
            else {
                compressed[compressedCounter++] = array[i];
            }

            //System.out.println("End of for\n\tCompressed: " + Arrays.toString(compressed) + "\n\tSearch: \t" + Arrays.toString(search));
        }
        return ArrayUtils.trim(compressed);
    }

    /**
     * @param all           All the bytes
     * @param compressed    The compressed bytes so far
     * @param search        The snippet found in the compressed array
     * @param indexOfSearch Index of the search in the all-array
     * @return
     */
    public byte[] findLargestRepeatable(byte[] all, byte[] compressed, byte[] search, int indexOfSearch) {
        //System.out.println("\t\t\tFinding largest repeat: " + Arrays.toString(all) + " - " + Arrays.toString(compressed) + " - " + Arrays.toString(search) + " - " + indexOfSearch);

        //System.out.println("\tindex "+index);
        while (ArrayUtils.contains(compressed, search)) {

            search = new byte[search.length + 1];
            int lengthToCopy = search.length < all.length - indexOfSearch ? search.length : (all.length - indexOfSearch);

            //System.out.println(lengthToCopy);
            System.arraycopy(all, indexOfSearch, search, 0, lengthToCopy);

            //System.out.println(lengthToCopy+"\n\tSearch: "+ Arrays.toString(search));

        }
        //System.out.println("\t\t"+ Arrays.toString(search));
        // Fjerner det elementet som terminerte while-loopen, kanskje en bedre måte å gjøre dette på
        search = new byte[search.length - 1];
        //int lengthToCopy = search.length - 1 < all.length - (indexOfSearch + search.length) ? search.length - 1 : (all.length - indexOfSearch) - 1;
        int lengthToCopy = search.length < all.length - indexOfSearch ? search.length : (all.length - indexOfSearch);

        System.arraycopy(all,
                indexOfSearch,
                search,
                0,
                lengthToCopy);

        //System.out.println("\tLargest repeat is " + new String(search)+" - "+ Arrays.toString(search));

        return search;

    }
    /**
     * @param compressed The compressed array so far
     * @param search     The array you are searching for
     * @param i          the next index in the compressed array
     * @return an array with code for length backwards at 0, and length of the word at 1
     */
    public byte[] generateCode(byte[] compressed, byte[] search, int i) {

        byte length = (byte) search.length;

        byte index = (byte) ArrayUtils.indexOf(compressed, search);

        return new byte[]{(byte) -(i - (index)), length};

    }

    public static void main(String[] args) throws IOException {

        byte[] bytes = Files.readAllBytes(Paths.get("F:/IntelliJ/IntelliJ IDEA 2016.1/IntelliJ_Workspace/Semester3/Øving12/files/opg12.txt"));

        Packer packer = new Packer(bytes);

        System.out.println("All the bytes: " + Arrays.toString(packer.allBytes));

        byte[] compressed = packer.pack(); // Krasjer med Integer.MAX, mye tregere men bedre med Short.Max, raskt med Byte.MAX

        System.out.println(Arrays.toString(compressed));
        //System.out.println(new String(compressed));

        File output = new File("F:/IntelliJ/IntelliJ IDEA 2016.1/IntelliJ_Workspace/Semester3/Øving12/files/outputFile.txt");
        Files.write(output.toPath(), compressed, StandardOpenOption.TRUNCATE_EXISTING);

        /*
        Komprimerte diverse.pdf: Compressed: 672175 - Old: 778424 med Short.MAX_VALUE, tok typ 3 min
         */
    }
}