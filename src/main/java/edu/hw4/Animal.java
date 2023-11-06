package edu.hw4;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {

    public static final int MAMMAL_PAWS_COUNT = 4;
    public static final int BIRD_PAWS_COUNT = 2;
    public static final int SPIDER_PAWS_COUNT = 8;
    public static final int FISH_PAWS_COUNT = 0;

    enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    enum Sex {
        M, F
    }

    public int paws() {
        return switch (type) {
            case CAT, DOG -> MAMMAL_PAWS_COUNT;
            case BIRD -> BIRD_PAWS_COUNT;
            case FISH -> FISH_PAWS_COUNT;
            case SPIDER -> SPIDER_PAWS_COUNT;
        };
    }

}
