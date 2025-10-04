package algorithms;

import metrics.PerformanceTracker;

public class ShellSort {

    public PerformanceTracker getTracker() {
        return null;
    }

    public void sort(int[] arr, GapSequence sequence) {
    }

    public enum GapSequence {
        SHELL, KNUTH, SEDGEWICK
    }

    private final GapSequence sequence;

    public ShellSort(GapSequence sequence) {
        this.sequence = sequence;
    }

    public void sort(int[] arr) {
        int n = arr.length;
        int[] gaps = generateGaps(n);

        for (int gap : gaps) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }

    private int[] generateGaps(int n) {
        switch (sequence) {
            case SHELL:
                return generateShellGaps(n);
            case KNUTH:
                return generateKnuthGaps(n);
            case SEDGEWICK:
                return generateSedgewickGaps(n);
            default:
                throw new IllegalArgumentException("Unknown gap sequence");
        }
    }

    private int[] generateShellGaps(int n) {
        int k = (int) (Math.log(n) / Math.log(2));
        int[] gaps = new int[k];
        for (int i = 0; i < k; i++) {
            gaps[i] = n / (int) Math.pow(2, i + 1);
        }
        return gaps;
    }

    private int[] generateKnuthGaps(int n) {
        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1;
        }
        int size = (int) (Math.log(n) / Math.log(3)) + 1;
        int[] gaps = new int[size];
        int index = 0;
        while (h > 0) {
            gaps[index++] = h;
            h /= 3;
        }
        int[] result = new int[index];
        System.arraycopy(gaps, 0, result, 0, index);
        return result;
    }

    private int[] generateSedgewickGaps(int n) {
        int[] gaps = new int[30];
        int k = 0;
        int gap;
        do {
            if (k % 2 == 0) {
                gap = 9 * ((int) Math.pow(2, k)) - 9 * ((int) Math.pow(2, k / 2)) + 1;
            } else {
                gap = 8 * ((int) Math.pow(2, k)) - 6 * ((int) Math.pow(2, (k + 1) / 2)) + 1;
            }
            if (gap < n) {
                gaps[k] = gap;
                k++;
            } else {
                break;
            }
        } while (gap < n);
        int[] result = new int[k];
        System.arraycopy(gaps, 0, result, 0, k);
        return result;
    }
}
