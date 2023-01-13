package com.quicksort;

import java.util.concurrent.RecursiveTask;

public class QuickSort extends RecursiveTask<int[]> {
    private int[] source;
    private int leftBorder;
    private int rightBorder;

    public QuickSort(int[] source, int leftBorder, int rightBorder) {
        this.source = source;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }

    @Override
    protected int[] compute() {
        if (source.length == 0) {
            return source;
        }
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        int pivot = source[(leftMarker + rightMarker) / 2];
        do {

            while (source[leftMarker] < pivot) {
                leftMarker++;
            }

            while (source[rightMarker] > pivot) {
                rightMarker--;
            }
            // check, if we need to swap elements under markers
            if (leftMarker <= rightMarker) {
                // if leftMarker is less than rightMarker - swap elements
                if (leftMarker < rightMarker) {
                    int tmp = source[leftMarker];
                    source[leftMarker] = source[rightMarker];
                    source[rightMarker] = tmp;
                }
                // moving markers in order to get new borders
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        // start recursion for both parts
        if (leftMarker < rightBorder) {
            QuickSort q1 = new QuickSort(source,leftMarker,rightBorder);
            q1.fork();
            source = q1.join();
        }
        if (leftBorder < rightMarker) {
            QuickSort q2 = new QuickSort(source,leftBorder,rightMarker);
            q2.fork();
            source = q2.join();
        }
        return source;
    }
}