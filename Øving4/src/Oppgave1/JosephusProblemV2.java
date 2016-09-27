package Oppgave1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OlavH on 11-Sep-16.
 */
public class JosephusProblemV2 {

    private int size;
    private int interval;

    private NodeList nodeList;

    public JosephusProblemV2(int size, int interval, boolean zeroIndexed){

        this.size = size;
        this.interval = interval;

        nodeList = new NodeList();

        int[] ints = new int[size];

        if (zeroIndexed) {
            for (int i = 0; i < size; i++) ints[i] = i;
        }
        else {
            for (int i = 1; i < size; i++) ints[i] = i;
        }

        nodeList = NodeList.of(ints);

    }
    public JosephusProblemV2(int size, int interval){
        this(size, interval, false);
    }

    public int findSafePosition(){ // 2*O( size ) + O(interval)

        while (nodeList.size() > 1){ // O( n )

            System.out.println(nodeList);
            int counter = 1;

            while (counter < interval){ // o( n ) av intervallet
                nodeList.getNext();
                counter++;
            }

            System.out.println("\tremoving: "+nodeList.getCurrent());
            nodeList.remove(); // O( n )
            // nodeList.getNext();


        }
        return nodeList.getCurrent().getContent();
   }


    public static void main(String[] args) {

        // JosephusProblem problem = new JosephusProblem(41, 3, false);
        JosephusProblemV2 problem = new JosephusProblemV2(41 , 3);

        System.out.println(problem.findSafePosition());

    }
}
