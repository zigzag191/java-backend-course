package edu.hw5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task6Test {

    @ParameterizedTest
    @CsvSource({
        "abc, achfdbaabgabcaabg",
        "qwerty, ~q~w~e~r~t~y~",
        "abc, abc",
        "abc, qwertyabc",
        "abc, abcqwerty"
    })
    void subsequencesShouldBeDeterminedCorrectly(String s, String t) {
        assertThat(Task6.isSubsequence(s, t)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
        "abc, cba",
        "abc, qwerty",
        "abc, cabaaba"
    })
    void absenceOfSubsequenceShouldBeDetermined(String s, String t) {
        assertThat(Task6.isSubsequence(s, t)).isFalse();
    }

}
