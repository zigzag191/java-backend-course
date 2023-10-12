package edu.hw1;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task3Test {

    @ParameterizedTest
    @MethodSource
    @DisplayName("Корректные входные данные должны правильно обрабытываться")
    void validInputShouldBeProcessedCorrectly(int[] first, int[] second, boolean expected) {
        boolean result = Task3.isNestable(first, second);
        assertThat(result).isEqualTo(expected);
    }

    @SuppressWarnings("MagicNumber")
    static Stream<Arguments> validInputShouldBeProcessedCorrectly() {
        return Stream.of(
            Arguments.arguments(new int[]{1, 2, 3, 4}, new int[]{0, 6}, true),
            Arguments.arguments(new int[]{3, 1}, new int[]{4, 0}, true),
            Arguments.arguments(new int[]{9, 9, 8}, new int[]{8, 9}, false),
            Arguments.arguments(new int[]{1, 2, 3, 4}, new int[]{2, 39}, false)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("При некоррентных данных должно выбрасываться исключение")
    void invalidInputShouldThrow(int[] first, int[] second) {
        assertThrows(Exception.class, () -> Task3.isNestable(first, second));
    }

    static Stream<Arguments> invalidInputShouldThrow() {
        return Stream.of(
            Arguments.arguments(null, null),
            Arguments.arguments(new int[0], null),
            Arguments.arguments(null, new int[] {1, 2})
        );
    }

}
