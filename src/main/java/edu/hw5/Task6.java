package edu.hw5;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Task6 {

    private Task6() {
    }

    public static boolean isSubsequence(String s, String t) {
        return Pattern.compile(s.chars()
            .mapToObj(c -> String.valueOf((char) c))
            .collect(Collectors.joining(".*", "^.*", ".*$")))
            .matcher(t)
            .matches();
    }

}
