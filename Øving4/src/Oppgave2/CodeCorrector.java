package Oppgave2;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by OlavH on 06-Sep-16.
 */

// Task 2: 5-2
public class CodeCorrector {

    public enum Code{
        CORRECT,
        ERROR_BRACKET_COUNT,
        ERROR_PARENTHESIS_COUNT,
        ERROR_BRACE_COUNT,
        ERROR_BETWEEN,
        ERROR_BRACES_IN_PARENTHESISES,
        ERROR_KEYWORD_PARENTHESESES;
    }

    private String code;

    public CodeCorrector(String code){
        this.code = code;
        removeComments();
    }
    public String getCode(){return code;}
    private void removeComments(){

        Pattern singleLineCommentPattern = Pattern.compile("//.*\n");
        Pattern multiLineCommentPattern = Pattern.compile(Pattern.quote("/*")+".*"+Pattern.quote("*/"));

        code = multiLineCommentPattern.matcher(code).replaceAll("");
        code = singleLineCommentPattern.matcher(code).replaceAll("");

    }

    private int count(String quotedCharacter){

        if (quotedCharacter.length() != 1) throw new IllegalArgumentException();

        Pattern pattern = Pattern.compile("["+Pattern.quote(quotedCharacter)+"]");
        Matcher matcher = pattern.matcher(code);
        int count = 0;
        while (matcher.find()) count++;
        return count;
    }


    public Code check(){

        int openBraces = count("{");
        int closeBraces = count("}");
        int openBrackets = count("[");
        int closeBrackets = count("]");
        int openParentheses = count("(");
        int closeParentheses = count(")");

        if (openBraces        != closeBraces)          return Code.ERROR_BRACE_COUNT;
        if (openBrackets      != closeBrackets)        return Code.ERROR_BRACKET_COUNT;
        if (openParentheses   != closeParentheses)     return Code.ERROR_PARENTHESIS_COUNT;


        // Check for braces inside parenthesises
        Pattern parenthesisPattern = Pattern.compile("\\(.*\\)"); // Finds all matching parentheses
        Matcher parenthesisMatcher = parenthesisPattern.matcher(code);

        Pattern noBracesInsideParenthesisesPattern = Pattern.compile("\\([^{}]*\\)"); // Checks for braces inside the parentheses

        while (parenthesisMatcher.find()){

            String group = parenthesisMatcher.group();

            if (!noBracesInsideParenthesisesPattern.matcher(group).matches()) return Code.ERROR_BRACES_IN_PARENTHESISES;

            // System.out.println(group + "\n\t"+noBracesInsideParenthesisesPattern.matcher(group).matches());

        }
        // Checks for ('s and )'s after keywords
        Pattern keywordsParenthesesCheck = Pattern.compile("(if|for|while).*\\n");
        Matcher keywordsParenthesesMatcher = keywordsParenthesesCheck.matcher(code);

        while (keywordsParenthesesMatcher.find()){

            String group = keywordsParenthesesMatcher.group();

            if (!group.matches(".*(if|for|while)\\s??\\(.*\\).*\\n")) return Code.ERROR_KEYWORD_PARENTHESESES;
        }
        // Checks for ('s and )'s after keywords

        // Check for illegal closecharacters after opencharacter

        List<String> controlChars = Arrays.asList("{","}","[","]","(",")");
        List<String> openControlChars = Arrays.asList("{","[","(");
        List<String> closeControlChars = Arrays.asList("}","]",")");

        // Ikke utrykk av typen ([)]

        for (String openChar : openControlChars){

            String rest ="";

            for (int i = 0; i < openControlChars.size(); i++) {

                String thisChar = openControlChars.get(i);
                System.out.println(thisChar);

                if (!openChar.equals(thisChar)/* && i != openControlChars.size()-1*/) rest +=thisChar+"|";                if (!openChar.equals(thisChar) && i!= openControlChars.size()-1) rest +=thisChar+"|";

            }
            System.out.println(rest);

            Pattern pattern = Pattern.compile(openChar+"(^"+rest+")*"+closeControlChars.get(openControlChars.indexOf(openChar)));
            Matcher matcher = pattern.matcher(code);

            while (matcher.find()) System.out.println(matcher.group());

        }

        //


        return Code.CORRECT;
    }

    public static void main(String[] args) {

        // Shellsort from last assignment
        CodeCorrector codeCorrector = new CodeCorrector(

                "public static int[] shellSortVariableSplit(int[] array, double split){ // oih(sejhsdiofh\n"
                        + "\n"
                        + "        int size = array.length;\n"
                        + "        int start = size/2; // () {sdkjsfds]} sdfkjbfbd{[](((}\n"
                        + "\n"
                        + "        while (start > 0){\n"
                        + "\n"
                        + "            for (int i=start; i<size; i++){/*()sdshksdkj{sdjbkdfkj}}*/\n"
                        + "\n"
                        + "                int j = i;\n"
                        + "                int temp = array[i];\n"
                        + "\n"
                        + "                while ( (j >= start) && (temp < array[j - start]) ){\n"
                        + "\n"
                        + "                    array[j] = array[j-start];\n"
                        + "                    j -= start;\n"
                        + "                }\n"
                        + "                array[j] = temp;\n"
                        + "            }\n"
                        + "            start = (start == 2) ? 1 : (int) (start / split);\n"
                        + "        }\n"
                        + "        return array ([)];\n"
                        + "    }"

        );
        // System.out.println(codeCorrector.getCode());
        System.out.println(codeCorrector.check());
    }


}
