package edu.hw11;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {

    public static final int NUMBER_OF_SIMULATIONS = 100;

    @Test
    void methodShouldBeDelegated() {
        Task2.delegateMethodCall(ArithmeticUtils.class, "sum", ArithmeticUtilsDelegate.class);

        var utils = new ArithmeticUtils();

        for (int i = 0; i < NUMBER_OF_SIMULATIONS; ++i) {
            for (int j = 0; j < NUMBER_OF_SIMULATIONS; ++j) {
                assertThat(utils.sum(i, j)).isEqualTo(i * j);
            }
        }
    }

    static public class ArithmeticUtils {
        public int sum(int a, int b) {
            return a + b;
        }
    }

    static public class ArithmeticUtilsDelegate {
        static public int sum(int a, int b) {
            return a * b;
        }
    }

}
