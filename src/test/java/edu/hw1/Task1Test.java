package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task1Test {

    @ParameterizedTest
    @CsvSource({
        "01:00, 60",
        "13:56, 836",
        "12:44, 764"
    })
    @DisplayName("Корректные входные данные должны правильно обрабытываться")
    void validInputShouldBeProcessedCorrectly(String input, int expected) {
        int seconds = Task1.minutesToSeconds(input);
        assertThat(seconds).isEqualTo(expected);
    }

    @Test
    @DisplayName("Количество минут больше 60 должно правильно обрабатываться")
    @SuppressWarnings("MagicNumber")
    void moreThan60MinutesShouldBePAllowed() {
        String videoLength = "999:59";
        int expected = 59999;
        int seconds = Task1.minutesToSeconds(videoLength);
        assertThat(seconds).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"10:60", "0:-1", "12:3:12"})
    @DisplayName("При некорректном формате результат должен быть -1")
    void invalidFormatShouldResultInMinusOne(String input) {
        int seconds = Task1.minutesToSeconds(input);
        assertThat(seconds).isEqualTo(-1);
    }

}
