package edu.project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GameSessionTest {

    static final int MAX_MISTAKES = 5;
    static final String WORD = "hangman";
    GameSession session = new GameSession(new HiddenWord(WORD), MAX_MISTAKES);

    void failGame() {
        char wrongGuess = 'z';
        for (int i = 0; i < MAX_MISTAKES; ++i, ++wrongGuess) {
            session.tryGuess(wrongGuess);
        }
    }

    @BeforeEach
    void resetSession() {
        session = new GameSession(new HiddenWord(WORD), MAX_MISTAKES);
    }

    @Test
    @DisplayName("От неверных ответов должны заканчиваться попытки")
    void wrongAnswersShouldDecreaseAttempts() {
        failGame();
        assertThat(session.hasMoreAttempts()).isFalse();
    }

    @Test
    @DisplayName("Если попытки закончились, отгадывать нельзя")
    void guessingAfterFailShouldNotBePossible() {
        failGame();
        var guessResult = session.tryGuess('h');
        assertThat(guessResult).isEqualTo(GuessResult.FAIL);
    }

    @Test
    @DisplayName("Нельзя отгадывать одну и ту же букву несколько раз")
    void guessingSameLetterShouldNotBePossible() {
        session.tryGuess('a');
        var guessResult = session.tryGuess('a');
        assertThat(guessResult).isEqualTo(GuessResult.ALREADY_TRIED);
    }

}
