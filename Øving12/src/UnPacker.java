import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by OlavH on 02-Nov-16.
 */
public class UnPacker {

    private byte[] compressed;

    public UnPacker(byte[] compressed) {
        this.compressed = compressed;
    }

    public byte[] unpack(){

        int originalLength = findOriginalLengthOf(compressed); // Fucks up if negative bytes in the text, ala æ ø å...

        byte[] uncompressed = new byte[originalLength];

        int uncompressedIndex = 0;
        for (int i = 0; i < compressed.length; i++) {

            byte distance = compressed[i];

            if (distance < 0){ // kode

                distance = (byte) -distance;
                //System.out.println(distance);

                int length = compressed[i+1]; // lengde på ordet man skal kopiere

                int indexOfOriginal = i-(distance);

                if (indexOfOriginal < 0 ) System.out.println(compressed[i]+" - "+compressed[i+1]+" - i: "+i);

                System.out.println("Original Index: "+indexOfOriginal+" - Length: "+length);

                for (int j = 0; j < length; j++) {

                    int newIndex = indexOfOriginal + j;

                    //if (newIndex < 0) break;

                    if (uncompressedIndex < uncompressed.length) uncompressed[uncompressedIndex++] = compressed[newIndex];

                }
                i++; // skips the other byte in the code

            }
            else if (uncompressedIndex < uncompressed.length) uncompressed[uncompressedIndex++] = compressed[i];

        }
        return uncompressed;
    }
    private int findOriginalLengthOf(byte[] compressedArray){
        int totalLength = compressed.length;
        for (int i = 0; i < compressed.length; i++) {

            byte b = compressed[i];

            if (b < 0){ // b er en referanse

                int length = compressed[i+1]-2; // kodene tar 2 plass

                totalLength+= length;


            }

        }
        return totalLength;

    }

    public static void main(String[] args) throws IOException {

        byte[] bytes = Files.readAllBytes(Paths.get("F:/IntelliJ/IntelliJ IDEA 2016.1/IntelliJ_Workspace/Semester3/Øving12/files/outputFile.txt"));

      /*  Packer packer = new Packer(bytes);
        byte[] compressed = packer.pack();*/

        UnPacker unPacker = new UnPacker(bytes);
        byte[] unpacked = unPacker.unpack();

        System.out.println(Arrays.toString(bytes));
        System.out.println(Arrays.toString(unpacked));
        System.out.println(new String(unpacked));


    }

}
