package edu.project1;

import java.util.HashSet;
import java.util.Set;

public class GameSession {

    private final HiddenWord hiddenWord;
    private final Set<Character> usedSymbols = new HashSet<>();
    private final int allowedMistakes;
    private int mistakes = 0;

    public GameSession(HiddenWord hiddenWord, int allowedMistakes) {
        this.allowedMistakes = allowedMistakes;
        this.hiddenWord = hiddenWord;
    }

    public GuessResult tryGuess(char guess) {
        if (!hasMoreAttempts()) {
            return GuessResult.FAIL;
        }
        if (usedSymbols.contains(guess)) {
            return GuessResult.ALREADY_TRIED;
        }
        usedSymbols.add(guess);
        if (hiddenWord.tryOpen(guess)) {
            return GuessResult.SUCCESS;
        }
        ++mistakes;
        return GuessResult.FAIL;
    }

    public boolean hasMoreAttempts() {
        return mistakes != allowedMistakes;
    }

    public int getAllowedMistakes() {
        return allowedMistakes;
    }

    public int getMistakes() {
        return mistakes;
    }

    public String getWordView() {
        return hiddenWord.getView();
    }

    public boolean wordIsOpen() {
        return hiddenWord.isFullyOpened();
    }

}
