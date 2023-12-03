package edu.hw7;

import java.util.stream.IntStream;

public final class Task2 {

    private Task2() {
    }

    public static int factorial(int n) {
        if (n < 0) {
            return -1;
        }
        if (n == 0) {
            return 1;
        }
        return IntStream.range(1, n + 1).parallel().reduce(1, (a, b) -> a * b);
    }

}
