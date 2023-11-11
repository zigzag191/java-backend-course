package edu.hw5;

import java.util.regex.Pattern;

public final class Task5 {

    private static final Pattern LICENSE_PLATE_PATTERN = Pattern
        .compile("[ABEKMHOPCTYX][0-9]{3}[ABEKMHOPCTYX]{2}(?:[1-9][0-9]{2}|[1-9][0-9]|[0-9][1-9])");

    public static boolean licensePlateIsValid(String licensePlate) {
        var matcher = LICENSE_PLATE_PATTERN.matcher(licensePlate);
        return matcher.matches();
    }

    private Task5() {
    }

}
