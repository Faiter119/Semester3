package Oppgave1;

/**
 * Created by OlavH on 14-Sep-16.
 */
public class Node {

    private Node next;
    private int content;

    public Node(int content) {
        this.content = content;
    }
    public int getContent(){return content;}
    public Node getNext(){return next;}
    public boolean hasNext(){return next != null;}

    public void setNext(Node next){
        this.next = next;
    }

    public Node add(Node node){

        this.next = node;
        return node;

    }

    @Override
    public String toString() {
        return ""+content;
    }

    public static void main(String[] args) {

        for (int i = 1; i < 9; i++) {



        }





    }
}
