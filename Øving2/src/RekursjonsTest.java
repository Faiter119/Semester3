
public class RekursjonsTest {


    public static int fact(int nr){

        if (nr  == 1) return 1;

        return nr * fact(nr-1); // nr * nr-1 * nr-2 * nr-3 * nr-4 ... * 1

    }

    public static void main(String[] args) {


        System.out.println(fact(5));
    }
}

