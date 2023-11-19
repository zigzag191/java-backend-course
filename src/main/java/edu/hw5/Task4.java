package edu.hw5;

import java.util.regex.Pattern;

public final class Task4 {

    private static final Pattern PASSWORD_VALIDATION_PATTERN  = Pattern.compile(".*[~!@#$%^&*|].*");

    public static boolean passwordIdValid(String password) {
        var matcher = PASSWORD_VALIDATION_PATTERN.matcher(password);
        return matcher.matches();
    }

    private Task4() {
    }

}
