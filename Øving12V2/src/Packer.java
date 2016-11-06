import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Created by OlavH on 31-Oct-16.
 */
public class Packer {

    private static final int MINIMUM_REPEATING_LENGTH = 5; // < 5 gir større, > 5 gir større. 5 er sweetspot

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
            //System.out.println("Partition: "+(i+1));
            compressedPartitions[i] = lempelZivCompress(partitions[i]);

        }

        byte[] build = ArrayUtils.build(compressedPartitions);

        System.out.println("Compressed: " + build.length + " - Old: " + allBytes.length);

        return build;
    }

    public byte[] lempelZivCompress(byte[] array) {

        byte[] compressed = new byte[array.length]; // Kan ikke bli større enn originalen...
        int compressedCounter = 0; // indexen i compressed-arrayet, oppfører seg som en stack

        byte[] completeCompressedArray = new byte[array.length*2]; // worst case nothing can be compressed it will be the length + codes
        int completeCompressedArrayCounter = 0; // this is getting stupid

        byte[] uncompressedBytes = new byte[Byte.MAX_VALUE]; // buffre bytes som ikke komprimeres
        int uncompressedCounter = 0;

        for (int i = 0; i < array.length; i++) {

            int lengthToCopy = MINIMUM_REPEATING_LENGTH <= array.length - i ? MINIMUM_REPEATING_LENGTH : array.length - i;
            byte[] search = new byte[lengthToCopy];
            System.arraycopy(array,i,search,0,lengthToCopy);

            //System.out.println("search is: "+new String(search));

            if (ArrayUtils.contains(compressed, search)) {

                // Write uncompressed bytes to result
                uncompressedBytes = ArrayUtils.trim(generateUncompressedArrayWithCode(uncompressedBytes, uncompressedCounter));
                //System.out.println("\tuncompressed: "+ Arrays.toString(uncompressedBytes));
                System.arraycopy(
                        uncompressedBytes,
                        0,
                        completeCompressedArray,
                        completeCompressedArrayCounter,
                        uncompressedBytes.length
                        );
                completeCompressedArrayCounter += uncompressedBytes.length;
                uncompressedCounter = 0;
                uncompressedBytes = new byte[Byte.MAX_VALUE];
                // Finished

                search = ArrayUtils.trim(findLargestRepeatable(array, compressed, search, i));

                //System.out.println("Largest repeat is : "+ Arrays.toString(search));

                byte[] code = generateBackwardsCode(completeCompressedArray, search, completeCompressedArrayCounter); // compression-code (compressed, search, compressedCounter)
                //System.out.println("code "+Arrays.toString(code));
                if (completeCompressedArrayCounter+1 >= completeCompressedArray.length) break;
                completeCompressedArray[completeCompressedArrayCounter++] = code[0];
                completeCompressedArray[completeCompressedArrayCounter++] = code[1];

                i += search.length-1; // Skipper forbi det du nettopp komprimerte

            }
            else if (uncompressedCounter < Byte.MAX_VALUE){ // Still space for more uncompressed bytes
                //System.out.println("\t\tStoring: "+array[i]);
                uncompressedBytes[uncompressedCounter++] = array[i];
                compressed[compressedCounter++] = array[i];
            }
            else {

                // Write uncompressed bytes to result
                uncompressedBytes = generateUncompressedArrayWithCode(uncompressedBytes, uncompressedCounter);
                //System.out.println("\t\tWriting: "+ Arrays.toString(uncompressedBytes));
                System.arraycopy(
                        uncompressedBytes,
                        0,
                        completeCompressedArray,
                        completeCompressedArrayCounter,
                        uncompressedBytes.length
                );
                completeCompressedArrayCounter += uncompressedBytes.length;
                uncompressedCounter = 0;
                uncompressedBytes = new byte[Byte.MAX_VALUE];
                // Finished

                uncompressedBytes[uncompressedCounter++] = array[i];
                compressed[compressedCounter++] = array[i];

            }

            //System.out.println("End of for\n\tCompressed: " + Arrays.toString(compressed) + "\n\tSearch: \t" + Arrays.toString(search));
        }

        if (uncompressedCounter > 0){// Still bytes in the buffer

            uncompressedBytes = generateUncompressedArrayWithCode(uncompressedBytes, uncompressedCounter);
            //System.out.println("Writing rest of buffer: "+ Arrays.toString(uncompressedBytes));
            System.arraycopy(
                    uncompressedBytes,
                    0,
                    completeCompressedArray,
                    completeCompressedArrayCounter,
                    ArrayUtils.trim(uncompressedBytes).length
            );

        }
        // write the rest of the byte-buffer
        /*uncompressedBytes = generateUncompressedArrayWithCode(uncompressedBytes, uncompressedCounter);
        System.out.println(uncompressedBytes.length +" - "+compressedWithCodesCounter+" - "+compressedWithUncompressedCodes.length);

        System.arraycopy(
                uncompressedBytes,
                0,
                compressedWithUncompressedCodes,
                compressedWithCodesCounter,
                uncompressedBytes.length
        );*/
        return ArrayUtils.trim(completeCompressedArray);
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
    public byte[] generateBackwardsCode(byte[] compressed, byte[] search, int i) {

        byte length = (byte) search.length;

        byte index = (byte) ArrayUtils.indexOf(compressed, search);

        //System.out.println("\tCompressed: "+ Arrays.toString(compressed)+" - Search: "+ Arrays.toString(search)+" - i: "+i+" - index: "+index);

        return new byte[]{(byte) -(i - (index)), length};

    }

    public byte[] generateUncompressedArrayWithCode(byte[] uncompressedBytes, int uncompressedCounter){ // [1,2,3,4] -> [4,1,2,3,4]

        final byte[] original = Arrays.copyOf(uncompressedBytes, uncompressedBytes.length);

        uncompressedBytes = new byte[uncompressedCounter+1];

        uncompressedBytes[0] = (byte) uncompressedCounter; // alltid mindre enn en byte, fordi eller skal vi skrive en ny kode også begynne å telle på nytt

        System.arraycopy(
                original,
                0,
                uncompressedBytes,
                1,
                uncompressedBytes.length-1

        );

        return uncompressedBytes;

    }

    public static void main(String[] args) throws IOException {

        byte[] bytes = Files.readAllBytes(Paths.get("F:/IntelliJ/IntelliJ IDEA 2016.1/IntelliJ_Workspace/Semester3/Øving12V2/files/diverse.txt"));

        Packer packer = new Packer(bytes);

        System.out.println("All the bytes: " + Arrays.toString(packer.allBytes));

        byte[] compressed = packer.pack(); // Krasjer med Integer.MAX, mye tregere men bedre med Short.Max, raskt med Byte.MAX, Short gir overflow i lengden

        System.out.println(Arrays.toString(compressed));

        //System.out.println(new String(compressed));

        File output = new File("F:/IntelliJ/IntelliJ IDEA 2016.1/IntelliJ_Workspace/Semester3/Øving12V2/files/outputFile.txt");
        Files.write(output.toPath(), compressed, StandardOpenOption.TRUNCATE_EXISTING);

        /*
        Komprimerte diverse.pdf: Compressed: 672175 - Old: 778424 med Short.MAX_VALUE, tok typ 3 min
         */
    }
}