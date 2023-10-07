package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task4Test {

    @ParameterizedTest
    @CsvSource({
        "123456, 214365",
        "hTsii  s aimex dpus rtni.g, This is a mixed up string.",
        "badce, abcde"
    })
    @DisplayName("Корректные входные данные должны правильно обрабытываться")
    void validInputShouldBeProcessedCorrectly(String input, String expected) {
        var formatted = Task4.fixString(input);
        assertThat(formatted).isEqualTo(expected);
    }

    @Test
    @DisplayName("Пустая строка должна остаться неизменённой")
    void emptyStringShouldBeUnchanged() {
        var input = "";
        var formatted = Task4.fixString(input);
        assertThat(formatted).isEqualTo("");
    }

    @Test
    @DisplayName("При передаче null должно выбрасываться исключение")
    void nullShouldThrow() {
        assertThrows(NullPointerException.class, () -> Task4.fixString(null));
    }

}
