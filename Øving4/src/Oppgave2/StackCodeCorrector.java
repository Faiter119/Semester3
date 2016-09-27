package Oppgave2;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackCodeCorrector {

    private final List<String> lines;
    private LinkedList<String> stack = new LinkedList<>(); // Used as stack, could use Stack, or Deque

    private final List<String> openControlCharsList = Arrays.asList("{","[","(");

    private final List<String> closedControlCharsList = Arrays.asList("}","]",")");

    public StackCodeCorrector(List<String> lines){

        this.lines = removeComments(lines);

    }

    public boolean check(){

        for (String line : lines) {

            for (int i = 0; i < line.length(); i++) {
                String c = Character.toString(line.charAt(i));
                if (openControlCharsList.contains(c)){
                    //System.out.println(line);
                    stack.push(c);
                }
                if (closedControlCharsList.contains(c)){

                    String topOfStack = stack.peek();

                    if (closedControlCharsList.indexOf(c) != openControlCharsList.indexOf(topOfStack)){
                        System.out.println("FALSE: "+c+" "+topOfStack);
                        System.out.println("\t"+line+" at line nr: "+(lines.indexOf(line)+1));
                        return false;
                    }
                    else{
                        System.out.println("Correct: "+stack.pop()+" "+c);
                    }
                }
            }


        }

        return true;
    }
    public static List<String> removeComments(List<String> lines){

        List<String> newList = new ArrayList<>();

        Pattern singleLineCommentPattern = Pattern.compile(Pattern.quote("//")+".*");
        Pattern multiLineCommentPattern = Pattern.compile(Pattern.quote("/*")+".*"+Pattern.quote("*/"));
        Pattern stringPattern = Pattern.compile(Pattern.quote("\"")+".*"+Pattern.quote("\""));

        for (String line : lines) {

            line = multiLineCommentPattern.matcher(line).replaceAll("");
            line = singleLineCommentPattern.matcher(line).replaceAll("");
            line = stringPattern.matcher(line).replaceAll("");

            newList.add(line);
        }

        // lines.stream(). // dropwhile har ikke kommet

        return newList;


    }

    public static void main(String[] args) throws IOException{

        Path path = Paths.get("Øving4","src","Oppgave2","Array.txt");

        List<String> fileLines = Files.readAllLines(path);

        // System.out.println(fileLines);

        StackCodeCorrector corrector = new StackCodeCorrector(fileLines);

        System.out.println(corrector.check());

    }
}
