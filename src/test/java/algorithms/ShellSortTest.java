package algorithms;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ShellSortBenchmarkCSV {

    public static void shellSortShellGaps(int[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }

    public static void shellSortKnuthGaps(int[] arr) {
        int n = arr.length;
        int gap = 1;
        while (gap < n / 3) {
            gap = 3 * gap + 1; // 1, 4, 13, 40, ...
        }
        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
            gap /= 3;
        }
    }

    public static void shellSortSedgewickGaps(int[] arr) {
        int n = arr.length;
        int[] sedgewickGaps = generateSedgewickGaps(n);

        for (int g = sedgewickGaps.length - 1; g >= 0; g--) {
            int gap = sedgewickGaps[g];
            if (gap >= n) continue;

            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }

    private static int[] generateSedgewickGaps(int n) {
        int[] gaps = new int[40];
        int k = 0;
        int gap;
        do {
            if (k % 2 == 0) {
                gap = 9 * (1 << (2 * k)) - 9 * (1 << k) + 1;
            } else {
                gap = 8 * (1 << (2 * k)) - 6 * (1 << (k + 1)) + 1;
            }
            gaps[k] = gap;
            k++;
        } while (gap < n);

        int[] result = new int[k];
        System.arraycopy(gaps, 0, result, 0, k);
        return result;
    }

    public static int[] generateArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(1000000);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] sizes = {1000, 5000, 10000, 50000};
        String fileName = "data/benchmark.csv";

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.append("InputSize,ShellGaps(ms),KnuthGaps(ms),SedgewickGaps(ms)\n");

            for (int n : sizes) {
                int[] arr;

                arr = generateArray(n);
                long start = System.nanoTime();
                shellSortShellGaps(arr);
                long end = System.nanoTime();
                double shellTime = (end - start) / 1e6;

                arr = generateArray(n);
                start = System.nanoTime();
                shellSortKnuthGaps(arr);
                end = System.nanoTime();
                double knuthTime = (end - start) / 1e6;

                arr = generateArray(n);
                start = System.nanoTime();
                shellSortSedgewickGaps(arr);
                end = System.nanoTime();
                double sedgewickTime = (end - start) / 1e6;

                writer.append(n + "," +
                        String.format("%.3f", shellTime) + "," +
                        String.format("%.3f", knuthTime) + "," +
                        String.format("%.3f", sedgewickTime) + "\n");

                System.out.println("n=" + n + " done");
            }

            System.out.println("Benchmark results saved to " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
