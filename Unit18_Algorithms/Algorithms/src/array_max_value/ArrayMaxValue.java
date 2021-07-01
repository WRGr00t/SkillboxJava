package array_max_value;

import java.util.Arrays;

public class ArrayMaxValue {
    public static int getMaxValue(int[] values) {
        if (values != null) {
            int maxValue = Integer.MIN_VALUE;
            for (int value : values) {
                if (value > maxValue) {
                    maxValue = value;
                }
            }
            return maxValue;
        } else {
            throw new IllegalArgumentException("Array is empty");
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{10, 20, 55, 120, 5, 55, 15, 200, 12, 88, 100, 119};
        int[] emptyArray = null;
        System.out.println(Arrays.toString(array));
        System.out.printf("Max value is - %d%n", ArrayMaxValue.getMaxValue(array));
        try {
            System.out.println(ArrayMaxValue.getMaxValue(emptyArray));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}