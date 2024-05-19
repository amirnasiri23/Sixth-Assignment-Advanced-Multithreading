package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PiCalculator {

    /**
     * Calculate pi and represent it as a BigDecimal object with the given floating point number (digits after . )
     * There are several algorithms designed for calculating pi, it's up to you to decide which one to implement.
     Experiment with different algorithms to find accurate results.

     * You must design a multithreaded program to calculate pi. Creating a thread pool is recommended.
     * Create as many classes and threads as you need.
     * Your code must pass all of the test cases provided in the test folder.

     * /@param floatingPoint the exact number of digits after the floating point
     * @return pi in string format (the string representation of the BigDecimal object)
     */

    public static class CalculatePi implements Runnable {
        int n;
        private static MathContext mc = new MathContext(1100);
        static final BigDecimal A = new BigDecimal(2).multiply(new BigDecimal(2).sqrt(mc), mc).divide(new BigDecimal(9801), mc);
        public CalculatePi(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            BigDecimal nominator = factorial(4 * n).multiply(new BigDecimal(26390 * n + 1103), mc);
            BigDecimal denominator = factorial(n).pow(4, mc).multiply(new BigDecimal(396).pow(4 * n, mc), mc);
            BigDecimal result = A.multiply(nominator, mc).divide(denominator, mc);
            addToSum(result);
        }
        public BigDecimal factorial(int n){
            BigDecimal temp = new BigDecimal(1);
            for (int i = 1; i <= n; i++) {
                temp = temp.multiply(new BigDecimal(i), mc);
            }

            return temp;
        }
        
    }

    public static BigDecimal sum;

    public static synchronized void addToSum(BigDecimal value){
        sum = sum.add(value);
    }

    public String calculate(int floatingPoint)
    {
        // TODO
        ExecutorService pool = Executors.newFixedThreadPool(4);

        sum = BigDecimal.valueOf(0);

        for (int i = 0; i <= 200; i++) {
            CalculatePi task = new CalculatePi(i);
            pool.execute(task);
        }

        pool.shutdown();

        try {
            pool.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return BigDecimal.valueOf(1).divide(sum, new MathContext(1100)).setScale(floatingPoint, RoundingMode.DOWN).toString();
    }

    public static void main(String[] args) {
//        String pi = calculate(7);
//        System.out.println(pi);
    }
}
