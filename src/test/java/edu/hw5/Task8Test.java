package edu.hw5;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings("MultipleStringLiterals")
public class Task8Test {

    public static Stream<Arguments> task1ShouldWorkCorrectly() {
        return Stream.of(
            arguments("0", true),
            arguments("010", true),
            arguments("00", false),
            arguments("1010", false),
            arguments("", false)
        );
    }

    public static Stream<Arguments> task2ShouldWorkCorrectly() {
        return Stream.of(
            arguments("011", true),
            arguments("1000", true),
            arguments("01", false),
            arguments("101", false)
        );
    }

    public static Stream<Arguments> task3ShouldWorkCorrectly() {
        return Stream.of(
            arguments("000", true),
            arguments("000000", true),
            arguments("1010101", true),
            arguments("001111100", false),
            arguments("101", false)
        );
    }

    public static Stream<Arguments> task4ShouldWorkCorrectly() {
        return Stream.of(
            arguments("11", false),
            arguments("111", false),
            arguments("abc", false),
            arguments("1", true),
            arguments("1111", true),
            arguments("01010101", true),
            arguments("", true)
        );
    }

    @ParameterizedTest
    @MethodSource
    void task1ShouldWorkCorrectly(String input, boolean expected) {
        assertThat(Task8.task8p1(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    void task2ShouldWorkCorrectly(String input, boolean expected) {
        assertThat(Task8.task8p2(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    void task3ShouldWorkCorrectly(String input, boolean expected) {
        assertThat(Task8.task8p3(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    void task4ShouldWorkCorrectly(String input, boolean expected) {
        assertThat(Task8.task8p4(input)).isEqualTo(expected);
    }

}
