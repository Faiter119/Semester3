/**
 * Created by OlavH on 19-Sep-16.
 */
public class Hash {

    private static int unicodeTotal(String string){
        int total = 0;

        for (int i = 0; i < string.length(); i++) {
            total += i*i*string.codePointAt(i); // char < unicode verdier
        }
        return total;
    }

    public static int multiplicativeHash(String string, int tableSize){ // H1
        // hash [0, tablesize]
        int total = unicodeTotal(string);
        double A = 0.61803398874989484820458683436564; //((Math.sqrt(5)-1)/(2));

        return (int)(tableSize*(((A*total)-(int)(A*total))));
    }
    public static int secondaryHash(String string, int tableSize){

        int total = unicodeTotal(string);

        return ((2*total)+1) % (tableSize);
    }
}
