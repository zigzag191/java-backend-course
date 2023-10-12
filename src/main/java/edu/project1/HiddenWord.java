package edu.project1;

public class HiddenWord {

    private final String word;
    private final StringBuffer view;
    private int openedSymbols = 0;

    public HiddenWord(String word) {
        if (!word.matches("[A-Za-z]{3,}")) {
            throw new WrongWordFormatException("word must contain only latin characters and be at least 3 in length");
        }
        this.word = word.toLowerCase();
        this.view = new StringBuffer(word.length());
        view.append("*".repeat(word.length()));
    }

    public boolean tryOpen(char guess) {
        boolean openedOnce = false;
        char searchedSymbol = Character.toLowerCase(guess);
        for (int i = 0; i < word.length(); ++i) {
            if (view.charAt(i) == '*' && word.charAt(i) == searchedSymbol) {
                view.setCharAt(i, searchedSymbol);
                openedOnce = true;
                ++openedSymbols;
            }
        }
        return openedOnce;
    }

    public String getView() {
        return view.toString();
    }

    public boolean isFullyOpened() {
        return openedSymbols == word.length();
    }

}
