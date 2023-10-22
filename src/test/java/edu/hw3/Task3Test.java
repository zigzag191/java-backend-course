package edu.hw3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.hw3.Task3.freqDict;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task3Test {

    @SuppressWarnings({"MultipleStringLiterals", "MagicNumber"})
    static Stream<Arguments> validInputShouldBeProcessedCorrectly() {
        return Stream.of(
            Arguments.arguments(
                List.of("this", "and", "that", "and"),
                Map.of("that", 1, "and", 2, "this", 1)
            ),
            Arguments.arguments(
                List.of("a", "bb", "a", "bb"),
                Map.of("bb", 2, "a", 2)
            ),
            Arguments.arguments(
                List.of("код", "код", "код", "bug"),
                Map.of("код", 3, "bug", 1)
            ),
            Arguments.arguments(
                List.of(1, 1, 2, 2),
                Map.of(1, 2, 2, 2)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Для корректного ввода должен возвращатся правильный результат")
    <T> void validInputShouldBeProcessedCorrectly(List<T> input, Map<T, Integer> expected) {
        assertThat(freqDict(input)).containsExactlyInAnyOrderEntriesOf(expected);
    }

    @Test
    @DisplayName("При пустом списке должен возвращаться пустой словарь")
    void emptyListShouldResultInEmptyMap() {
        var emptyList = new ArrayList<String>();
        var map = freqDict(emptyList);
        assertThat(map).isEmpty();
    }

    @Test
    @DisplayName("При null должно выбрасываться исключение")
    void nullShouldThrow() {
        assertThrows(NullPointerException.class, () -> freqDict(null));
    }

}
