package Oppgave1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by OlavH on 14-Sep-16.
 */
public class NodeList{

    private Node head;
    private Node current;

    public NodeList(Node ... nodes){

        head = nodes[0];
        current = nodes[0];

        addAll(nodes);

    }

    public NodeList(){
        head = new Node(1);
    }
    public Node getHead(){return head;}
    public Node getCurrent(){return current;}
    public Node getNext(){
        current = current.getNext();
        return current;
    }

    public int size(){
        if (head == null) return 0;

        int size = 1;

        Node node = head.getNext();

        while(node != head){
            size++;
            node = node.getNext();
        }
        return size;
    }

    public void addAll(Node ... nodes){

        for (Node node : nodes) {
            add(node);
        }

    }
    public void add(Node node){

        node.setNext(head); // At the end

        Node h = head;
        while (h.getNext() != head){ // Finner den siste noden
            h = h.getNext();
        }
        h.setNext(node);
    }
    public void remove(){

        //current.setNext(current.getNext());

        if (size() == 1) return;

        Node next = current.getNext();

        Node node = current.getNext();

        while (node.getNext() != current){ // O( n )
            node = node.getNext();
        }

        node.setNext(next);

        if (current == head){
            head = node;
            current = node.getNext();
            return;
        }

        current = node.getNext(); // ellers er du på den noden før den du slettet, som ikke er tenkt funksjonalitet
    }

    public static NodeList of(int ... ints){

        for (int i = 0; i < ints.length; i++) {
            ints[i] = i;
        }

        Node[] nodes = new Node[ints.length];
        for (int i = 1; i < ints.length+1; i++) {
            nodes[i-1] = new Node(i);
        }
        return new NodeList(nodes);

    }

    @Override
    public String toString() {
        String out = head.toString();
        Node node = getHead().getNext();

        while (head != node){
            out += " "+node;
            node = node.getNext();
        }
        return out;
    }

    public static void main(String[] args) {

        NodeList nodeList = NodeList.of(1,2,3,4,5,6,7,8,9,10);


        System.out.println("Size: "+nodeList.size());
        System.out.println("Current: "+nodeList.getCurrent() );
        System.out.println("Head: "+nodeList.getHead());
        for (int i = 1; i < nodeList.size(); i++) {

            if (i == 5 ){
                System.out.println("removing: "+i);
                nodeList.remove();
            }
            System.out.println(nodeList.getNext());

        }
        System.out.println(nodeList.toString());

        /*while (head.hasNext()){
            head = head.getNext();
            System.out.println(head);
        }*/

    }
}
