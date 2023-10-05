package hw1;

public class Task1 {

    public static int minutesToSeconds(String videoLength) {
        try {
            var time = videoLength.split(":");
            if (time.length != 2) {
                return -1;
            }

            int minutes = Integer.parseInt(time[0]);
            int seconds = Integer.parseInt(time[1]);
            if (seconds >= 60 || minutes < 0 || seconds < 0) {
                return -1;
            }

            return minutes * 60 + seconds;
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    private Task1() {
    }

}
