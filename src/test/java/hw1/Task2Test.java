package hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task2Test {

    @Test
    @DisplayName("Положительное число")
    void positiveNumber() {
        int number = 123;
        int digitsCount = Task2.countDigits(number);
        assertThat(digitsCount).isEqualTo(3);
    }

    @Test
    @DisplayName("Отрицательное число")
    void negativeNumber() {
        int number = -1;
        int digitsCount = Task2.countDigits(number);
        assertThat(digitsCount).isEqualTo(1);
    }

    @Test
    @DisplayName("Ноль")
    void zero() {
        int number = 0;
        int digitsCount = Task2.countDigits(number);
        assertThat(digitsCount).isEqualTo(1);
    }

}
