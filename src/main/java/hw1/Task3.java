package hw1;

import java.util.Arrays;
import java.util.Objects;

public class Task3 {

    public static boolean isNestable(int[] first, int[] second) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);

        var firstStat = Arrays.stream(first).summaryStatistics();
        var secondStat = Arrays.stream(second).summaryStatistics();

        return firstStat.getMin() > secondStat.getMin()
            && firstStat.getMax() < secondStat.getMax();
    }

    private Task3() {
    }

}
