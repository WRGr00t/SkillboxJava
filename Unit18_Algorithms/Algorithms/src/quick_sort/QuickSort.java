package quick_sort;

import java.util.Arrays;

public class QuickSort {
    public static void sort(int[] array) {
        if (array.length <= 1) {
            return;
        }
        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] array, int from, int to) {
        if (from < to) {
            int partitionIndex = partition(array, from, to);
            sort(array, from, partitionIndex - 1);
            sort(array, partitionIndex + 1, to);
        }
    }

    private static int partition(int[] array, int begin, int end) {
        int pivot = array[end];
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, end);
        return i + 1;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] x = {8, 0, 4, 7, 3, 7, 10, 12, -3, -2};

        System.out.println("Было");
        System.out.println(Arrays.toString(x));

        sort(x);
        System.out.println("Стало");
        System.out.println(Arrays.toString(x));
    }

}
