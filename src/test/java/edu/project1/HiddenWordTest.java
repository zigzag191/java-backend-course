package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HiddenWordTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " \t ", "абвгд", "q", "qw", "hello, world", "foo11"})
    @DisplayName("При некорректном формате строки должно выбрасываться исключение")
    void invalidStringFormatShouldThrow(String input) {
        assertThrows(WrongWordFormatException.class, () -> new HiddenWord(input));
    }

    @Test
    @DisplayName("Все вхождения угаданной буквы должны открываться")
    void successfulGuessShouldOpenAllOccurrences() {
        var hiddenWord = new HiddenWord("abracadabra");
        hiddenWord.tryOpen('a');
        var expectedView = "a**a*a*a**a";
        assertThat(hiddenWord.getView()).isEqualTo(expectedView);
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "WoRlD", "QWERTY", "Foo", "abracadabra"})
    @DisplayName("После отгадывания всех букв слово должно открыться")
    void wordShouldOpenAfterAllLettersAreGuesses(String input) {
        var hiddenWord = new HiddenWord(input);
        for (int i = 0; i < input.length(); ++i) {
            hiddenWord.tryOpen(input.charAt(i));
        }
        assertThat(hiddenWord.isFullyOpened()).isTrue();
        assertThat(hiddenWord.getView().indexOf('*')).isEqualTo(-1);
    }

}
