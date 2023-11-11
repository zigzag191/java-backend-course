package edu.hw5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {

    @ParameterizedTest
    @ValueSource(strings = {
        "!",
        "!qwerty",
        "qwerty!",
        "~ ! @ # $ % ^ & * |",
        "qwe~rty"
    })
    void validPasswordsShouldBeDeterminedCorrectly(String password) {
        assertThat(Task4.passwordIdValid(password)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "qwerty",
        "qwe?rty"
    })
    void invalidPasswordShouldBeDeterminedCorrectly(String password) {
        assertThat(Task4.passwordIdValid(password)).isFalse();
    }

}
