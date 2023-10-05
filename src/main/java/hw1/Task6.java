package hw1;

import java.util.Arrays;

public class Task6 {

    public static int countK(int number) {
        if (number <= 1000 || number > 9999) {
            throw new IllegalArgumentException("number must be in range (1000;9999]");
        }
        if (number == 6174) {
            return 0;
        }

        int[] arr = numberToArray(number);
        Arrays.sort(arr);
        int ascending = arrayToNumber(arr);
        reverse(arr);
        int descending = arrayToNumber(arr);

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
            result *= 10;
            result += n;
        }
        return result;
    }

    private static void reverse(int[] array) {
        for (int l = 0, r = array.length - 1; l < r; ++l, --r) {
            int tmp = array[l];
            array[l] = array[r];
            array[r] = tmp;
        }
    }

    private Task6() {
    }

}
