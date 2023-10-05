package hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task3Test {

    @ParameterizedTest
    @MethodSource
    @DisplayName("Тест с различными аргументами")
    void isNestableShouldWorkGivenDifferentArguments(int[] first, int[] second, boolean expectedResult) {
        boolean result = Task3.isNestable(first, second);
        assertThat(result).isEqualTo(expectedResult);
    }

    static Stream<Arguments> isNestableShouldWorkGivenDifferentArguments() {
        return Stream.of(
            Arguments.arguments(new int[]{1, 2, 3, 4}, new int[]{0, 6}, true),
            Arguments.arguments(new int[]{3, 1}, new int[]{4, 0}, true),
            Arguments.arguments(new int[]{9, 9, 8}, new int[]{8, 9}, false),
            Arguments.arguments(new int[]{1, 2, 3, 4}, new int[]{2, 39}, false)
        );
    }

}
