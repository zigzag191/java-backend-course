package edu.project1;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConsoleTest {

    @Test
    @DisplayName("Сообщение о выходе из игры должно распозноваться")
    void quitMessageShouldBeDetected() {
        var testUserInput = "quit";
        var inputStream = new ByteArrayInputStream(testUserInput.getBytes());
        var console = new Console(inputStream);
        var gameInput = console.readGameInput();
        assertThat(gameInput).isInstanceOf(UserGameInput.QuitMessage.class);
    }

    @Test
    @DisplayName("При вводе некорректные значения должны игнорироваться")
    void invalidInputShouldBeIgnored() {
        var testUserInput = "123\n....\nабвгд\nqwerty\na";
        var inputStream = new ByteArrayInputStream(testUserInput.getBytes());
        var console = new Console(inputStream);
        var gameInput = console.readGameInput();
        assertThat(gameInput).isInstanceOf(UserGameInput.Guess.class);
        assertThat(((UserGameInput.Guess) gameInput).symbol()).isEqualTo('a');
    }

    @ParameterizedTest
    @CsvSource({
        "y, true",
        "Y, true",
        "n, false",
        "yes, false",
        "да, false"
    })
    @DisplayName("Ответы пользователя должны корректно распознаваться")
    void userResponsesShouldBeProcessedCorrectly(String input, boolean expected) {
        var inputStream = new ByteArrayInputStream(input.getBytes());
        var console = new Console(inputStream);
        assertThat(console.askUser("")).isEqualTo(expected);
    }

}
