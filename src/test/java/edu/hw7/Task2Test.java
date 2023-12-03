package edu.hw7;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {

    @ParameterizedTest
    @CsvSource({
        "0, 1",
        "-1, -1",
        "1, 1",
        "2, 2",
        "3, 6",
        "5, 120",
        "7, 5040",
        "8, 40320",
        "10, 3628800"
    })
    void factorialShouldBeCalculatedCorrectly(int n, int expected) {
        assertThat(Task2.factorial(n)).isEqualTo(expected);
    }

}
