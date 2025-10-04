package cli;

import algorithms.ShellSort;
import algorithms.ShellSort.GapSequence;
import metrics.PerformanceTracker;

import java.util.Random;
import java.util.Scanner;

public class BenchmarkRunner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array size:");
        int n = scanner.nextInt();

        System.out.println("Choose gap sequence: SHELL, KNUTH, SEDGEWICK");
        String seqInput = scanner.next().toUpperCase();

        GapSequence sequence;
        try {
            sequence = GapSequence.valueOf(seqInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid sequence, using SHELL by default.");
            sequence = GapSequence.SHELL;
        }

        int[] arr = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(100000);

        ShellSort sorter = new ShellSort(GapSequence.SHELL);

        long startTime = System.nanoTime();
        sorter.sort(arr, sequence);
        long endTime = System.nanoTime();

        PerformanceTracker tracker = sorter.getTracker();

        System.out.println("Sorting time: " + ((endTime - startTime)/1e6) + " ms");
        System.out.println(tracker);
    }
}
