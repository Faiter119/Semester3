package Oppgave1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OlavH on 11-Sep-16.
 */
public class JosephusProblem {

    private int size;
    private int interval;

    private CircularList<Integer> circularList;

    public JosephusProblem(int size, int interval, boolean zeroIndexed){

        this.size = size;
        this.interval = interval;

        List<Integer> integerList = new ArrayList<>(size);

        if (zeroIndexed) {
            for (int i = 0; i < size; i++) integerList.add(i);
        }
        else {
            for (int i = 1; i < size+1 ; i++) integerList.add(i);
        }

        System.out.println(integerList);
        circularList = new CircularList<>(integerList);

    }
    public JosephusProblem(int size, int interval){
        this(size, interval, true);
    }

    public int findSafePosition(){

        CircularList<Integer> list = circularList.copy();

        Integer current = list.current();
        System.out.println("first "+current);

        while (list.size() > 1){

            int counter = 0;

            while (counter < interval){
                System.out.println("Current is: "+list.current());
                current = list.next();
                counter++;
            }

            System.out.println("Removing: "+current);
            list.remove();

        }
        System.out.println(list.toString());
        return list.current();
    }


    public static void main(String[] args) {

        // JosephusProblem problem = new JosephusProblem(41, 3, false);
        JosephusProblem problem = new JosephusProblem(10, 4, false);

        System.out.println(problem.findSafePosition());


    }
}
