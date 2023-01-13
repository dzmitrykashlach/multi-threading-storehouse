package com.quicksort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class QuickSortMain {
    public static void main(String[] args) {
        int[] a = {875,245, 3, 7, 9, 54, 1, 2,-9};
        ForkJoinPool pool = new ForkJoinPool();
        QuickSort q = new QuickSort(a, 0, a.length - 1);
        int[] r = pool.invoke(q);
        Arrays.stream(r).forEach(System.out::println);
    }
}
