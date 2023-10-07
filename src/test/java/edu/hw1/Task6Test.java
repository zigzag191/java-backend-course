package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task6Test {

    @ParameterizedTest
    @CsvSource({
        "3524, 3",
        "6621, 5",
        "6554, 4",
        "1234, 3"
    })
    @DisplayName("Корректные входные данные должны правильно обрабытываться")
    void validInputShouldBeProcessedCorrectly(int input, int expected) {
        int result = Task6.countK(input);
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {1000, 10000, 0, 999, -1})
    @DisplayName("При некоррентных данных должно выбрасываться исключение")
    void invalidInputShouldThrow(int input) {
        assertThrows(IllegalArgumentException.class, () -> Task6.countK(input));
    }

}
