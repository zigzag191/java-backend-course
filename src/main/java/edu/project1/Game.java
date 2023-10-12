package edu.project1;

import static edu.project1.UserGameInput.Guess;
import static edu.project1.UserGameInput.QuitMessage;

public class Game {

    private static final int ALLOWED_MISTAKES = 5;
    private final Console console = new Console();
    private final Dictionary dictionary;
    private GameSession session;
    private boolean isRunning = true;

    public Game(Dictionary dictionary) {
        this.dictionary = dictionary;
        resetSession();
    }

    public void run() {
        console.write("Welcome to the Hangman game! (to quit the game enter \"quit\")");
        while (isRunning) {
            console.write("The word: " + session.getWordView());
            switch (console.readGameInput()) {
                case QuitMessage ignored -> {
                    isRunning = false;
                }
                case Guess guess -> {
                    handleGuess(guess);
                    handleGameRestart();
                }
            }
        }
    }

    private void resetSession() {
        HiddenWord hiddenWord;
        try {
            hiddenWord = dictionary.getRandomWord();
        } catch (WrongWordFormatException e) {
            console.write("Provided dictionary failed to produce word. Using default word for the game");
            hiddenWord = new HiddenWord("default");
        }
        session = new GameSession(hiddenWord, ALLOWED_MISTAKES);
    }

    private void handleGameRestart() {
        if (session.wordIsOpen()) {
            console.write("You won!");
            askForGameRestart();
        } else if (!session.hasMoreAttempts()) {
            console.write("You lost!");
            askForGameRestart();
        }
    }

    private void askForGameRestart() {
        if (console.askUser("Try again?")) {
            resetSession();
        } else {
            isRunning = false;
        }
    }

    private void handleGuess(Guess guess) {
        var guessResult = session.tryGuess(guess.symbol());
        switch (guessResult) {
            case FAIL -> printFailMessage();
            case SUCCESS -> console.write("Hit!");
            case ALREADY_TRIED -> console.write("You have already tried this symbol");
            default -> throw new RuntimeException();
        }
    }

    private void printFailMessage() {
        console.write(
            String.format(
                "Missed! Mistake %d out of %d",
                session.getMistakes(),
                session.getAllowedMistakes()
            )
        );
    }

}
