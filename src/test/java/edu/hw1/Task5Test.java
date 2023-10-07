package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {

    @ParameterizedTest
    @CsvSource({
        "11211230, true",
        "13001120, true",
        "23336014, true",
        "-23336014, true",
        "9998, false",
        "11, true",
        "0, true"
    })
    @DisplayName("Функция должна возвращать правильные результаты")
    void inputShouldBeProcessedCorrectly(int input, boolean expected) {
        boolean result = Task5.isPalindromeDescendant(input);
        assertThat(result).isEqualTo(expected);
    }

}
