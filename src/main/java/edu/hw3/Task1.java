package edu.hw3;

import java.util.Objects;

public final class Task1 {

    public static final int LATIN_ALPHABET_SIZE = 26;

    public static String atbash(String s) {
        Objects.requireNonNull(s);
        return s.chars()
            .map(Task1::getMirrored)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    private static char getMirrored(int c) {
        int shift;
        if (c >= 'a' && c <= 'z') {
            shift = 'a';
        } else if (c >= 'A' && c <= 'Z') {
            shift = 'A';
        } else {
            return (char) c;
        }
        return (char) (LATIN_ALPHABET_SIZE + 2 * shift - c - 1);
    }

    private Task1() {
    }

}
