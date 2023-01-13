package com.factorial;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class Factorial extends RecursiveTask<BigInteger> {
    private final int factorial;
    private static AtomicInteger taskCounter = new AtomicInteger(0);

    public Factorial(int factorial) {
        super();
        this.factorial = factorial;
    }

    @Override
    protected BigInteger compute() {
        if (factorial <= 1) {
            return new BigInteger(String.valueOf(1));
        }
        final Factorial t1 = new Factorial(factorial - 1);
        t1.fork();
        taskCounter.getAndIncrement();
        System.out.println("Number of tasks = " + Factorial.getTaskCounter().get());
        return new BigInteger(String.valueOf(factorial)).multiply(t1.join());
    }

    public static AtomicInteger getTaskCounter() {
        return taskCounter;
    }
}
