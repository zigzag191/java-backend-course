package edu.hw1;

public final class Task1 {

    private static final int SECONDS_IN_MINUTE = 60;

    public static int minutesToSeconds(String videoLength) {
        try {
            var time = videoLength.split(":");
            if (time.length != 2) {
                return -1;
            }

            int minutes = Integer.parseInt(time[0]);
            int seconds = Integer.parseInt(time[1]);
            if (seconds >= SECONDS_IN_MINUTE || minutes < 0 || seconds < 0) {
                return -1;
            }

            return minutes * SECONDS_IN_MINUTE + seconds;
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    private Task1() {
    }

}
