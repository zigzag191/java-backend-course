package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {

    @ParameterizedTest
    @CsvSource({
        "16, 1, 1",
        "17, 2, 6"
    })
    @DisplayName("Функция должна возвращать правильные результаты для левого сдвига")
    void rotateLeftInputShouldBeProcessedCorrectly(int number, int shift, int expected) {
        int result = Task7.rotateLeft(number, shift);
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "8, 1, 4",
        "20, 7, 5"
    })
    @DisplayName("Функция должна возвращать правильные результаты для правого сдвига")
    void rotateRightInputShouldBeProcessedCorrectly(int number, int shift, int expected) {
        int result = Task7.rotateRight(number, shift);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Сдвиг на полный цикл должен возвращать тоже самое число")
    @SuppressWarnings("MagicNumber")
    void fullCycleShouldGiveSameNumber() {
        int number = 1234;
        int shift = 33;
        int resultLeft = Task7.rotateLeft(number, shift);
        int resultRight = Task7.rotateRight(number, shift);
        assertThat(resultLeft).isEqualTo(resultRight).isEqualTo(number);
    }

}
