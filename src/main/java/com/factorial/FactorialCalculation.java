package com.factorial;


import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;

public class FactorialCalculation {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int factorial = 20;
        Factorial f = new Factorial(factorial);
        BigInteger r = pool.invoke(f);
        System.out.println("Factorial of "+factorial+" = "+r.longValue());
    }
}
