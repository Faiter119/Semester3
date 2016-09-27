import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/*
Måter å løse kollisjoner
- Lenka liste
- Finne ledig plass // Lineær probing

 */

public class StringHashTable{

    private String[] stringKeyTable; // keys

    private Set<Integer> keySet = new HashSet<>();

    private int tableSize; // Size of stringKeyTable
    private int items; // Items in table

    private int collisions;

    public StringHashTable(int size){
        stringKeyTable = new String[size];

        tableSize = size;
    }
    public StringHashTable(){
        this((int)Math.pow(2, 24));
    }

    public int getSize(){return tableSize;}
    public int getItems(){return items;}
    public int getCollisions(){return collisions;}

    public void addAll(String ... items){ for (String item : items) add(item); }
    public void addAll(Collection<String> items){ for (String item : items) add(item); }

    public void add(String item){

        if (items == tableSize) throw new IllegalArgumentException("Table is full");

        int hash = Hash.multiplicativeHash(item, tableSize);

        //System.out.println(item+" - "+hash);

        if (stringKeyTable[hash] != null){ // Collision
            //System.out.println("\tCollision: "+item+" - "+ stringKeyTable[hash]);
            collisions++;

            int attempts = 0;

            while (stringKeyTable[hash] != null){
                hash += Hash.secondaryHash(item, tableSize); // Finner ledig plass
                if (hash > tableSize) hash = 0;
                attempts++;
                if (attempts > tableSize){
                    while (stringKeyTable[hash] != null){
                        hash++;
                        if (hash > tableSize) hash = 0;
                    }
                }
                //System.out.println("\tTrying: "+hash);
            }
            //System.out.println("\tFound space at: " + hash);
            stringKeyTable[hash] = item;
            keySet.add(hash);
        }
        else {
            stringKeyTable[hash] = item; // No collision
            keySet.add(hash);
        }
        items++;
    }

    public Optional<List<String>> getAll(){

        if (items==0) return Optional.empty();
        /*
        List<String> out = new ArrayList<>();

        for (int i = 0; i < tableSize; i++) { // tror dette er veldig feil

            String item = stringKeyTable[i];

            if (item != null) out.add(item);
        }
        return Optional.of(out);*/
        List<String> out = new ArrayList<>();
        for (Integer key : keySet) {
            out.add(stringKeyTable[key]);
        }
        return Optional.of(out);
    }
    /**
     * Warning: This kills the performance
     */
    public Optional<List<String>> search(String item){ // Finnes sikkert en smartere måte

        if (item == null || items==0) return Optional.empty();

        List<String> results = new ArrayList<>();

        for (Integer i : keySet) {
            if (stringKeyTable[i].contains(item)) results.add(stringKeyTable[i]);
        }
        return Optional.of(results);

        /*int hash = Hash.multiplicativeHash(item, tableSize);

        if (item.equals(stringKeyTable[hash])) return Optional.of(Arrays.asList(stringKeyTable[hash])); // If its the exact item

        int counter = -1;
        int matches = 0;

        while (counter++ < tableSize-1){ // rip performance

            String thisItem = stringKeyTable[counter];

            if (thisItem != null){
                // System.out.println(thisItem);
                if (thisItem.contains(item)) {
                    results.add(stringKeyTable[counter]);
                    matches++;
                }
            }
        }
        return matches==0 ? Optional.empty() : Optional.of(results);*/
    }
    /*public String get(String item){


    }*/
    public boolean contains(String item){

        int hash = Hash.multiplicativeHash(item, tableSize);

        if (stringKeyTable[hash] != null && stringKeyTable[hash].equals(item)) return true;

        else {
            int secHash = Hash.secondaryHash(item, tableSize);

            try{
                while (stringKeyTable[hash] == null || !stringKeyTable[hash].equals(item)){
                    hash += secHash;
                }
                return true;

            }
            catch (IndexOutOfBoundsException e){ // yolo
                return false;
            }
        }
    }
    public double loadFactor(){
        return (double) items/(double) tableSize;
    }

    public String toString() {
        return Arrays.toString(stringKeyTable);
    }

    public static void main(String[] args) throws IOException {

        StringHashTable stringHashTable = new StringHashTable(130); // 2^23

        List<String> lines = Files.readAllLines(Paths.get("Øving5/src/navn.txt"));

        System.out.println("Original Size: "+lines.size()+"\n");

        stringHashTable.addAll(lines);

       // System.out.println(stringHashTable.toString());
        System.out.println();
        System.out.println("Items:\t\t\t\t\t\t "+stringHashTable.getItems());
        System.out.println("Collisions:\t\t\t\t\t "+stringHashTable.getCollisions());

        System.out.println("Exists without collision:\t "+stringHashTable.contains("Nordmark, Elena Falkenberg")); // Har ikke kollisjon
        System.out.println("Exists with collision:\t\t "+stringHashTable.contains("Øien, Stine Sandvold")); // Har kollisjon
        System.out.println("Does not exist:\t\t\t\t "+stringHashTable.contains("Ost ost ost")); // finnes ikke
        System.out.println();

        // System.out.println("Husby, Olav Reppe".contains("Olav"));
        System.out.println();

        stringHashTable.search("Olav").ifPresent(System.out::println); // Herre virka jo 97*mindre effektivt
        System.out.println("Loadfactor: "+stringHashTable.loadFactor());


    }
}
