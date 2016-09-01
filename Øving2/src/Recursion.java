/**
 * Created by OlavH on 8/24/2016.
 */
public class Recursion {

    public static double pow(int base, int power){ // Må bruke double fordi long har for lav rekkevidde // Skulle egentlig brukt BigInteger

        if (power == 0) return 1;

        return base * pow(base, power-1);
    }

    public static double powLoop(int base, int power){

        double answer = base;

        while (power --> 1){
            answer *= base;
        }
        return answer;

    }

    public static double powAdvanced(int base, int power){

        if (power == 0) return 1;

        if (power % 2 == 0){ // partall

            return powAdvanced(base*base, power/2);

        }
        else {

            return base*powAdvanced(base*base, (power-1)/2);

        }
    }



    public static void main(String[] args) {

        int LOOPS = 500000;
        int BASE = 2;
        int POWER = 9;

        // Recursion
        long s = System.nanoTime();
        double res = 0;
        for(int i=0; i<LOOPS; i++) {
            res = pow(BASE, POWER);
        }
        long e = System.nanoTime();
        System.out.println("Recursion:          "+(e-s)/LOOPS+" res: "+res);

        // Advanced Recursion
        s = System.nanoTime();
        for(int i=0; i<LOOPS; i++) {
            res = powAdvanced(BASE, POWER);
        }
        e = System.nanoTime();
        System.out.println("Advanced Recursion: "+(e-s)/LOOPS+" res: "+res);

        // Loop
        s = System.nanoTime();
        for(int i=0; i<LOOPS; i++) {
            res = powLoop(BASE, POWER);
        }
        e = System.nanoTime();
        System.out.println("Loop:               "+(e-s)/LOOPS+" res: "+res);

        // Math.pow
        s = System.nanoTime();
        for(int i=0; i<LOOPS; i++) {
            res = Math.pow(BASE, POWER);
        }
        e = System.nanoTime();
        System.out.println("Math.pow:           "+(e-s)/LOOPS+" res: "+res);


    }
}
