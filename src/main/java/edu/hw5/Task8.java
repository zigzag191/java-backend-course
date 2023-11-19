package edu.hw5;

import java.util.regex.Pattern;

public final class Task8 {

    private static final Pattern TASK_8_1_PATTERN = Pattern.compile("^[01](?:[01]{2})*$");
    private static final Pattern TASK_8_2_PATTERN = Pattern.compile("^(0(?:[01]{2})*)|(1[01](?:[01]{2})*)$");
    private static final Pattern TASK_8_3_PATTERN = Pattern.compile("^(?:1*01*01*01*)+$");
    private static final Pattern TASK_8_4_PATTERN = Pattern.compile("^(?!11$|111$)[01]*$");

    private Task8() {
    }

    public static boolean task8p1(String s) {
        return TASK_8_1_PATTERN.matcher(s).matches();
    }

    public static boolean task8p2(String s) {
        return TASK_8_2_PATTERN.matcher(s).matches();
    }

    public static boolean task8p3(String s) {
        return TASK_8_3_PATTERN.matcher(s).matches();
    }

    public static boolean task8p4(String s) {
        return TASK_8_4_PATTERN.matcher(s).matches();
    }

}
