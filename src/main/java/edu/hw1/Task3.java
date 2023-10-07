package edu.hw1;

import java.util.Arrays;
import java.util.IllformedLocaleException;
import java.util.Objects;

public final class Task3 {

    public static boolean isNestable(int[] first, int[] second) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);

        if (first.length == 0 || second.length == 0) {
            throw new IllformedLocaleException("both arrays must have at least one element");
        }

        var firstStat = Arrays.stream(first).summaryStatistics();
        var secondStat = Arrays.stream(second).summaryStatistics();

        return firstStat.getMin() > secondStat.getMin()
            && firstStat.getMax() < secondStat.getMax();
    }

    private Task3() {
    }

}
