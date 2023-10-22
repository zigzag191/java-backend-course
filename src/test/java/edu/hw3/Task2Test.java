package edu.hw3;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw3.Task2.clusterize;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task2Test {

    @SuppressWarnings("MultipleStringLiterals")
    static Stream<Arguments> stringsShouldBeProcessedCorrectly() {
        return Stream.of(
            Arguments.arguments("()()()", List.of("()", "()", "()")),
            Arguments.arguments("((()))", List.of("((()))")),
            Arguments.arguments("((()))(())()()(()())", List.of("((()))", "(())", "()", "()", "(()())")),
            Arguments.arguments("((())())(()(()()))", List.of("((())())", "(()(()()))"))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Строки должны кластеризироваться корректно")
    void stringsShouldBeProcessedCorrectly(String input, List<String> expected) {
        assertThat(clusterize(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("При null должно выбрасываться исключение")
    void nullShouldThrow() {
        assertThrows(NullPointerException.class, () -> clusterize(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"()(())a()()", ")(())()()((()))", "()()()(())("})
    @DisplayName("При некорректном формате строки должно выбрасываться исключение")
    void invalidStringShouldThrow(String input) {
        assertThrows(RuntimeException.class, () -> clusterize(input));
    }

}
