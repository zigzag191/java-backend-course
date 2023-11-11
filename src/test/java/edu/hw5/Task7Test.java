package edu.hw5;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings("MultipleStringLiterals")
public class Task7Test {

    static Stream<Arguments> task1ShouldWorkCorrectly() {
        return Stream.of(
            arguments("000", true),
            arguments("110111", true),
            arguments("11", false),
            arguments("0010", false),
            arguments("ab0c", false)
        );
    }

    public static Stream<Arguments> task2ShouldWorkCorrectly() {
        return Stream.of(
            arguments("000", true),
            arguments("110111", true),
            arguments("1", true),
            arguments("0", true),
            arguments("10", false),
            arguments("0011", false),
            arguments("1abc1", false)
        );
    }

    public static Stream<Arguments> task3ShouldWorkCorrectly() {
        return Stream.of(
            arguments("1", true),
            arguments("00", true),
            arguments("101", true),
            arguments("0000", false),
            arguments("10101", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    void task1ShouldWorkCorrectly(String input, boolean expected) {
        assertThat(Task7.task7p1(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    void task2ShouldWorkCorrectly(String input, boolean expected) {
        assertThat(Task7.task7p2(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    void task3ShouldWorkCorrectly(String input, boolean expected) {
        assertThat(Task7.task7p3(input)).isEqualTo(expected);
    }

}
