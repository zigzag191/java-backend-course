package edu.hw1;

import java.util.Arrays;

public final class Task6 {

    private static final int BASE = 10;
    private static final int MIN_RANGE = 1000;
    private static final int MAX_RANGE = 9999;
    private static final int K = 6174;

    public static int countK(int number) {
        if (number <= MIN_RANGE || number > MAX_RANGE) {
            throw new IllegalArgumentException("number must be in range ("
                + MIN_RANGE + ";" + MAX_RANGE + "]");
        }
        if (number == K) {
            return 0;
        }

        int[] arr = numberToArray(number);
        Arrays.sort(arr);
        int ascending = arrayToNumber(arr);
        int descending = reverseArrayToNumber(arr);

        int diff = descending - ascending;
        return 1 + countK(diff);
    }

    private static int[] numberToArray(int number) {
        var str = Integer.toString(number);
        int[] result = new int[str.length()];
        for (int i = 0; i < str.length(); ++i) {
            result[i] = str.charAt(i) - '0';
        }
        return result;
    }

    private static int arrayToNumber(int[] array) {
        int result = 0;
        for (int n : array) {
            result *= BASE;
            result += n;
        }
        return result;
    }

    private static int reverseArrayToNumber(int[] array) {
        int result = 0;
        for (int i = array.length - 1; i >= 0; --i) {
            result *= BASE;
            result += array[i];
        }
        return result;
    }

    private Task6() {
    }

}
