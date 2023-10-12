package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task2Test {

    @ParameterizedTest
    @CsvSource({
        "4666, 4",
        "544, 3",
        "9, 1"
    })
    @DisplayName("Положительные числа должны правильно обрабатываться")
    void positiveNumbersShouldBeProcessedCorrectly(int input, int expected) {
        int digitsCount = Task2.countDigits(input);
        assertThat(digitsCount).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "-123456, 6",
        "-100, 3",
        "-1, 1"
    })
    @DisplayName("Отрицательные числа должны правильно обрабатываться")
    void negativeNumbersShouldBeProcessedCorrectly(int input, int expected) {
        int digitsCount = Task2.countDigits(input);
        assertThat(digitsCount).isEqualTo(expected);
    }

    @Test
    @DisplayName("Длина \"0\" должна быть единицей")
    void zeroShouldHaveLengthOne() {
        int number = 0;
        int digitsCount = Task2.countDigits(number);
        assertThat(digitsCount).isEqualTo(1);
    }

}
