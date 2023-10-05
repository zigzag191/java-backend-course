package hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task4Test {

    @ParameterizedTest
    @CsvSource({
        "123456, 214365",
        "hTsii  s aimex dpus rtni.g, This is a mixed up string.",
        "badce, abcde"
    })
    @DisplayName("Тест с различными аргументами")
    void fixStringShouldWorkGivenDifferentArguments(String input, String expectedOutput) {
        var formatted = Task4.fixString(input);
        assertThat(formatted).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("Пустая строка должна остаться неизменённой")
    void emptyStringShouldBeUnchanged() {
        var input = "";
        var formatted = Task4.fixString(input);
        assertThat(formatted).isEqualTo("");
    }

}
