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

        MathContext mc = new MathContext(1000);
        int n;
        public CalculatePi(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            BigDecimal sign = new BigDecimal(1);
            if (n % 2 == 1) {
                sign = new BigDecimal(-1);
            }

            BigDecimal numerator = factorial(6* n);
            BigDecimal sum2 = new BigDecimal("13591409");
            BigDecimal sum1 = new BigDecimal("545140134");
            BigDecimal nBig = new BigDecimal(n);
            BigDecimal sum3 = sum1.multiply(nBig, mc);
            BigDecimal TotalSum = sum3.add(sum2, mc);
            numerator = numerator.multiply(TotalSum);
            numerator = numerator.multiply(sign);

            BigDecimal denominator = factorial(3 * n);
            BigDecimal fact2 = factorial(n).pow(3);
            BigDecimal fact3 = new BigDecimal("640320");
            BigDecimal fact4 = fact3.pow(3 * n + 3 / 2);
            denominator = denominator.multiply(fact2, mc);
            denominator = denominator.multiply(fact4, mc);
            denominator = denominator.multiply(sign, mc);

            BigDecimal result = numerator.divide(denominator, mc);
            addTouSum(result);
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

    public static synchronized void addTouSum(BigDecimal value){
        sum = sum.add(value);
    }

    public String calculate(int floatingPoint)
    {
        // TODO
        return null;
    }

    public static void main(String[] args) {
        // Use the main function to test the code yourself
        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        sum = new BigDecimal(0);

        for (int i = 0; i < 100; i++) {                          // increasing the number of iterations improves
            CalculatePi task = new CalculatePi(i);          // accuracy, try 200 and see the difference!
            threadPool.execute(task);
        }

        threadPool.shutdown();

        try {
            threadPool.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sum = sum.setScale(1000, RoundingMode.HALF_DOWN);

        BigDecimal num = new BigDecimal("12");
        BigDecimal num1 = new BigDecimal("1");

        sum = sum.multiply(num, new MathContext(1000));

        BigDecimal ans = num1.divide(sum, new MathContext(1000));

        System.out.println(ans);

        BigDecimal accVal = new BigDecimal("3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679");

        System.out.println("Difference:        " + accVal.subtract(sum).abs().toPlainString());

    }
}
