package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw3.Task1.atbash;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {

    @ParameterizedTest
    @CsvSource({
        "Hello world!, Svool dliow!",
        "Any fool can write code that a computer can understand."
            + "Good programmers write code that humans can understand. ― Martin Fowler,"
            + "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw."
            + "Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi"
    })
    @DisplayName("Строки должны преобразовываться корректно")
    void stringsShouldBeConvertedCorrectly(String input, String expected) {
        assertThat(atbash(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("При null должно выбрасываться исключение")
    void nullShouldThrow() {
        assertThrows(NullPointerException.class, () -> atbash(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "abcdefghijklmnopqrstuvwxyz",
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    })
    @DisplayName("Строки, содержищие алфавит, должны переворачиваться")
    void stringsThatContainAlphabetShouldBeReversed(String input) {
        assertThat(atbash(input)).isEqualTo(new StringBuilder(input).reverse().toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Привет, мир",
        ".,/<>\\|/-=ж;:'\"",
        "~!@#$%^&*()_+"
    })
    @DisplayName("Нелатинские символы должны игнорироваться")
    void nonLatinSymbolsShouldBeIgnored(String input) {
        assertThat(atbash(input)).isEqualTo(input);
    }

}
