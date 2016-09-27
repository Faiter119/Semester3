package Oppgave2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by OlavH on 06-Sep-16.
 */
public class Regex {


    public static void main(String[] args) {

        String test =
                "public static int[] shellSortVariableSplit(int[] array, double split){\n"
                + "\n"
                + "        int size = array.length;\n"
                + "        int start = size/2;\n"
                + "\n"
                + "        while (start > 0){ \n"
                + "\n"
                + "            for (int i=start; i<size; i++){\n"
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
                + "        return array;\n"
                + "    }";

        Pattern pattern = Pattern.compile(".*\\([^{}]*\\).*");
        Matcher matcher = pattern.matcher(test);

        Pattern parenthesises = Pattern.compile("\\(.*\\)");
        Matcher parenthesisMatcher = parenthesises.matcher(test);

        while (parenthesisMatcher.find()){

            String group = parenthesisMatcher.group();
            boolean matches = pattern.matcher(group).matches();

            System.out.println(group+"\n\t"+matches);
        }


        // System.out.println("No errors: "+!matcher.matches());


    }

}
