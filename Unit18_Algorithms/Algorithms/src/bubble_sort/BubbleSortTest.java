package bubble_sort;

import merge_sort.MergeSort;
import org.junit.Test;
import quick_sort.QuickSort;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;

public class BubbleSortTest {
    private static final int NUM_OF_TESTS = 3;
    private int[][] test = {
            {4, 3, 5, 1, 3, 2, 9},
            {45, 8, 78, 1, 0, -5, 45, 77},
            {-1, 0, -77, 155, -3, 2}
    };
    private int[][] answers = {
            {1, 2, 3, 3, 4, 5, 9},
            {-5, 0, 1, 8, 45, 45, 77, 78},
            {-77, -3, -1, 0, 2, 155}
    };

    List<Integer> numberOfElements = Arrays.asList(0, 1, 2, 3, 4, 10, 500);

    @Test(timeout = 5000)
    public void testBubble() {

        for (Integer numberOfElement : numberOfElements) {
            List<Integer> elements = generateRandom(numberOfElement);

            int[] array = elements.stream().mapToInt(i->i).toArray();
            BubbleSort.sort(array);
            assertSorted(array);
        }
    }


    @Test(timeout = 5000)
    public void testMerge() {

        for (Integer numberOfElement : numberOfElements) {
            List<Integer> elements = generateRandom(numberOfElement);

            int[] array = elements.stream().mapToInt(i->i).toArray();
            MergeSort.mergeSort(array);
            assertSorted(array);
        }
    }

    @Test(timeout = 5000)
    public void testQuick() {

        for (Integer numberOfElement : numberOfElements) {
            List<Integer> elements = generateRandom(numberOfElement);
            System.out.println(elements);

            int[] array = elements.stream().mapToInt(i->i).toArray();
            QuickSort.sort(array);
            assertSorted(array);
        }
    }

    private void assertSorted(int[] actual) {
        int[] expected = Arrays.copyOf(actual, actual.length);
        Arrays.sort(expected);
        assertArrayEquals(expected, actual);
    }

    private List<Integer> generateRandom(Integer numberOfElement) {
        Random random = new Random(0);
        return Stream.generate(() -> random.nextInt(3)).limit(numberOfElement).collect(Collectors.toList());
    }

}