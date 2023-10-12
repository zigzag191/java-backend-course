package edu.project1;

public final class Main {

    public static void main(String[] args) {
        var game = new Game(new InMemoryDictionary());
        game.run();
    }

    private Main() {
    }

}
