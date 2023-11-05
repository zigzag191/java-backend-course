package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw3.Task4.convertToRoman;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task4Test {

    @ParameterizedTest
    @CsvSource({
        "1, I",
        "2, II",
        "12, XII",
        "16, XVI",
        "3999, MMMCMXCIX"
    })
    @DisplayName("Для корректного ввода должен возвращатся правильный результат")
    void validInputShouldBeProcessedCorrectly(int input, String expected) {
        assertThat(convertToRoman(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 40000})
    @DisplayName("При некорректном вводе должно выбрасываться исключение")
    void invalidInputShouldThrow(int input) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> convertToRoman(input));
    }

}
