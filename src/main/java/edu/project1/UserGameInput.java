package edu.project1;

public sealed interface UserGameInput {

    record QuitMessage() implements UserGameInput {}

    record Guess(char symbol) implements UserGameInput {}

}
