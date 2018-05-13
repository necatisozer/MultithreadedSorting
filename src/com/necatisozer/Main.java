package com.necatisozer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        /* Constant value for the size of the list */
        final int LIST_SIZE = 10;
        /* Constant value for bound of random integer values */
        final int RANDOM_BOUND = 32;

        /* Initializes the blank original list */
        List<Integer> originalList = new ArrayList<>();
        /* Initializes the blank sorted list */
        List<Integer> sortedList = new ArrayList<>();
        /* Initializes the random generator */
        Random random = new Random();

        /* Adds random integers to the original list */
        for (int i = 0; i < LIST_SIZE; i++) {
            originalList.add(random.nextInt(RANDOM_BOUND));
        }

        /* Prints the original list */
        System.out.println("Original List = " + originalList);

        /* Variable for the list size */
        int listSize = originalList.size();
        /* Variable for the half index of the list */
        int halfIndex = listSize / 2;

        /* Creates the first sorting thread and overrides run() function with sorting function for the first half of the list */
        Thread sortingThread0 = new Thread(() -> Collections.sort(originalList.subList(0, halfIndex)));
        /* Gives max priority to run before merging thread but simultaneously with the second sorting thread */
        sortingThread0.setPriority(Thread.MAX_PRIORITY);
        /* Runs the first sorting thread */
        sortingThread0.run();

        /* Creates the second thread and overrides run() function with sorting function for the second half of the list */
        Thread sortingThread1 = new Thread(() -> Collections.sort(originalList.subList(halfIndex, listSize)));
        /* Gives also max priority to run before merging thread but simultaneously with the first sorting thread */
        sortingThread1.setPriority(Thread.MAX_PRIORITY);
        /* Runs the first sorting thread */
        sortingThread1.run();

        /* Creates the merge thread and overrides run() function with merge function */
        Thread mergeThread = new Thread(() -> merge(originalList, sortedList));
        /* Gives min priority to run after sorting threads */
        mergeThread.setPriority(Thread.MIN_PRIORITY);
        /* Runs the merge thread */
        mergeThread.run();
    }

    private static void merge(List<Integer> originalList, List<Integer> sortedList) {
        /* Variable for the half index of the list */
        int halfIndex = originalList.size() / 2;

        /* Runs until one half is totally consumed */
        while (halfIndex > 0 && halfIndex < originalList.size()) {
            /* Checks the first elements of the halves */
            if (originalList.get(0) <= originalList.get(halfIndex)) {
                /* Adds the first element of the first half to the sorted list */
                sortedList.add(originalList.get(0));
                /* Removes added element */
                originalList.remove(0);
                /* Decreases half index because of removal of the first element */
                halfIndex--;
            } else {
                /* Adds the first element of the second half to the sorted list */
                sortedList.add(originalList.get(halfIndex));
                /* Removes added element */
                originalList.remove(halfIndex);
            }
        }

        /* Adds rest of the elements of the original list to the sorted list */
        sortedList.addAll(originalList);

        /* Prints the sorted list */
        System.out.println("Sorted List = " + sortedList);
    }
}
