package Oppgave1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OlavH on 12-Sep-16.
 */

/**
 * Navigable circular list
 */
public class CircularList<T extends Comparable<T>> {

    private List<T> list;
    private static int counter = 0;

    private final List<T> initialList; // saves the initial list for copy

    public CircularList(List<T> initialList){

        this.initialList = initialList;

        this.list = new ArrayList<>(initialList);
    }

    public int size(){return list.size();}

    /**
     * Element in the next position
     */
    public T next(){

        if (counter == list.size()) counter=0;

        return list.get(counter++);
    }

    /**
     * Element in the previous position
     */
    public T prev(){
        if (counter == 0) counter = list.size();

        return list.get(counter--);
    }

    /**
     * Element in the current position
     */
    public T current(){
        if (counter == list.size()) counter=0;

        return list.get(counter);
    }

    /**
     * removes current element
     */
    public void remove(){
        list.remove(--counter);
    }

    public CircularList<T> copy(){
        return new CircularList<>(initialList);
    }

    public String toString() {
        return list.toString();
    }
}
