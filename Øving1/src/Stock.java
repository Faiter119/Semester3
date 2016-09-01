import java.time.DayOfWeek;
import java.util.Arrays;

public class Stock {


    private int[] stockChangeTable;
    private int[] absoluteValueTable;

    public Stock(int ... stockChangeTable){

        this.stockChangeTable = stockChangeTable;
        setChangeTable();

    }
    public int[] getAbsoluteValueTable(){return absoluteValueTable;}
    public int[] getStockChangeTable(){return stockChangeTable;}

    private void setChangeTable(){ // O(n)

        absoluteValueTable = new int[stockChangeTable.length];
        int total = 0;
        int counter = 0;
        for (int nr : stockChangeTable){

            total += nr;
            absoluteValueTable[counter++] = total;

        }

    }

    public int[] getBuyAndSellDays(){ // O(n^2)

        int buy = -1;
        int sell = -1;

        int maxDifference = -1;

        for(int i=0 ; i < absoluteValueTable.length; i++){ // n

            for (int j=i+1; j < absoluteValueTable.length; j++){ // *n

                int diff = absoluteValueTable[j] - absoluteValueTable[i];
                // System.out.println(i+" "+j+" "+diff);

                if (diff > maxDifference){
                    // System.out.println("New Max!");
                    maxDifference = diff;
                    buy = i;
                    sell = j;
                }

            }

        }

        return new int[]{buy,sell};
    }



    public static void main(String[] args) {

        DayOfWeek[] days = DayOfWeek.values();


        /*int[] values = new int[]{-1, 3, -9, 2, 2, -1, 2, -1, -5};
        System.out.println("Values: " + Arrays.toString(values));

        Stock stock = new Stock(values); // Kursforandring per dag

        System.out.println("Totals: "+Arrays.toString(stock.getAbsoluteValueTable()));

        System.out.println("Buy: "+days[stock.getBuyAndSellDays()[0]]);
        System.out.println("Sell: "+days[stock.getBuyAndSellDays()[1]]);*/

        // First Run
        int[] randomValues = Rand.getRandomValues(100, -10, 10);
        long start = System.currentTimeMillis();

        Stock stock = new Stock(randomValues); // O(n)
        //System.out.println(Arrays.toString(stock.getStockChangeTable()));
        //System.out.println(Arrays.toString(stock.getAbsoluteValueTable()));
        System.out.println(Arrays.toString(stock.getBuyAndSellDays())); // O(n^2)

        System.out.println("Time: "+(System.currentTimeMillis()-start));

        // Second Run

        randomValues = Rand.getRandomValues(1000, -10, 10);
        start = System.currentTimeMillis();

        stock = new Stock(randomValues);
        //System.out.println(Arrays.toString(stock.getStockChangeTable()));
        //System.out.println(Arrays.toString(stock.getAbsoluteValueTable()));
        System.out.println(Arrays.toString(stock.getBuyAndSellDays()));

        System.out.println("Time: "+(System.currentTimeMillis()-start));

        // Third Run

        randomValues = Rand.getRandomValues(10000, -10, 10);
        start = System.currentTimeMillis();

        stock = new Stock(randomValues);
        //System.out.println(Arrays.toString(stock.getStockChangeTable()));
        //System.out.println(Arrays.toString(stock.getAbsoluteValueTable()));
        System.out.println(Arrays.toString(stock.getBuyAndSellDays()));

        System.out.println("Time: "+(System.currentTimeMillis()-start));

        //

        randomValues = Rand.getRandomValues(100000, -10, 10);
        start = System.currentTimeMillis();

        stock = new Stock(randomValues);
        //System.out.println(Arrays.toString(stock.getStockChangeTable()));
        //System.out.println(Arrays.toString(stock.getAbsoluteValueTable()));
        System.out.println(Arrays.toString(stock.getBuyAndSellDays()));

        System.out.println("Time: "+(System.currentTimeMillis()-start));

        //

        randomValues = Rand.getRandomValues(1000000, -10, 10);
        start = System.currentTimeMillis();

        stock = new Stock(randomValues);
        //System.out.println(Arrays.toString(stock.getStockChangeTable()));
        //System.out.println(Arrays.toString(stock.getAbsoluteValueTable()));
        System.out.println(Arrays.toString(stock.getBuyAndSellDays()));

        System.out.println("Time: "+(System.currentTimeMillis()-start));

    }
}
