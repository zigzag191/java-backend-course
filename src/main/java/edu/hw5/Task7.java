package edu.hw5;

import java.util.regex.Pattern;

public final class Task7 {

    private static final Pattern TASK_7_1_PATTERN = Pattern.compile("^[01]{2}0[01]*$");
    private static final Pattern TASK_7_2_PATTERN = Pattern.compile("^([01])[01]*\\1$|[01]");
    private static final Pattern TASK_7_3_PATTERN = Pattern.compile("^[01]{1,3}$");

    public static boolean task7p1(String s) {
        return TASK_7_1_PATTERN.matcher(s).matches();
    }

    public static boolean task7p2(String s) {
        return TASK_7_2_PATTERN.matcher(s).matches();
    }

    public static boolean task7p3(String s) {
        return TASK_7_3_PATTERN.matcher(s).matches();
    }

    private Task7() {
    }

}
