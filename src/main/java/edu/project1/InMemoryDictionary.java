package edu.project1;

import java.util.Random;

public class InMemoryDictionary implements Dictionary {

    private static final String[] WORDS = new String[]{
        "hello",
        "world",
        "hangman",
        "dictionary",
        "monitor",
        "keyboard",
        "mouse",
        "window"
    };

    private static final Random RNG = new Random();

    @Override
    public HiddenWord getRandomWord() {
        int index = RNG.nextInt(0, WORDS.length);
        return new HiddenWord(WORDS[index]);
    }

}
