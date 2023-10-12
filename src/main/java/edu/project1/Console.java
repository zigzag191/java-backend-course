package edu.project1;

import java.io.InputStream;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Console {

    private static final Logger LOGGER = LogManager.getLogger("hangman");
    private final Scanner scanner;

    public Console() {
        scanner = new Scanner(System.in);
    }

    public Console(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    public void write(String message) {
        LOGGER.info(message);
    }

    public UserGameInput readGameInput() {
        LOGGER.info("Guess a letter:");
        while (true) {
            var input = scanner.nextLine().trim();
            if ("quit".equals(input)) {
                return new UserGameInput.QuitMessage();
            }
            if (input.length() == 1 && isAllowedSymbol(input.charAt(0))) {
                return new UserGameInput.Guess(input.charAt(0));
            }
            LOGGER.info("Wrong input. Try again:");
        }
    }

    public boolean askUser(String question) {
        LOGGER.info(question + " [y/n]: ");
        var input = scanner.nextLine().trim();
        return "y".equals(input) || "Y".equals(input);
    }

    private boolean isAllowedSymbol(char c) {
        return (c >= 'a' && c <= 'z')
            || (c >= 'A' && c <= 'Z');
    }

}
