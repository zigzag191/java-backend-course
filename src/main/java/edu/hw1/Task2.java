package edu.hw1;

public final class Task2 {

    private static final int BASE = 10;

    public static int countDigits(int number) {
        int n = number / BASE;

        int result = 1;
        while (n != 0) {
            ++result;
            n /= BASE;
        }

        return result;
    }

    private Task2() {
    }

}
