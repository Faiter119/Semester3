import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

        int originalLength = findOriginalLengthOf(compressed);

        byte[] uncompressed = new byte[originalLength];

        int uncompressedIndex = 0;
        for (int i = 0; i < compressed.length; i++) {

            byte code = compressed[i];

            //System.out.println(code);

            if (code > 0){

                System.arraycopy(
                        compressed,
                        i+1, // tar ikke med koden
                        uncompressed,
                        uncompressedIndex,
                        code
                );
                uncompressedIndex += code;
                i+= code;

            }
            else {
                int length = compressed[i+1];

                System.arraycopy(
                        compressed,
                        i-(-code), // der koden peker til
                        uncompressed,
                        uncompressedIndex,
                        length
                );
                uncompressedIndex += length;
                i+= 1;
            }
        }
        return uncompressed;
    }
    public int findOriginalLengthOf(byte[] compressedArray){
        int totalLength = 0;

        for (int i = 0; i < compressedArray.length;i++) {

            byte code = compressedArray[i]; // start of code

            if (code > 0) {
                totalLength += code;
                i+= code;
            }
            else { // b er en referanse

                int length = compressedArray[i+1];

                totalLength+= length;

                i+= 1;

            }

        }
        return totalLength;

    }

    public static void main(String[] args) throws IOException {

        byte[] bytes = Files.readAllBytes(Paths.get("F:/IntelliJ/IntelliJ IDEA 2016.1/IntelliJ_Workspace/Semester3/Øving12/files/outputFile.txt"));

        UnPacker unPacker = new UnPacker(bytes);
        byte[] unpacked = unPacker.unpack();

        System.out.println(Arrays.toString(bytes));
        System.out.println(Arrays.toString(unpacked));
        //System.out.println(new String(unpacked));
        System.out.println("Packed: "+bytes.length+" - Unpacked: "+unpacked.length);

        Files.write(Paths.get("F:/IntelliJ/IntelliJ IDEA 2016.1/IntelliJ_Workspace/Semester3/Øving12/files/unpackedFile.txt"), unpacked, StandardOpenOption.CREATE);
    }
}