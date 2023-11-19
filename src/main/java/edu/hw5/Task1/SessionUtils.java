package edu.hw5.Task1;

import java.time.DateTimeException;
import java.time.Duration;
import java.util.List;

public final class SessionUtils {

    public static Duration countAverageSessionTime(List<String> sessions) {
        try {
            if (sessions.isEmpty()) {
                return Duration.ZERO;
            }
            var totalMinutes = sessions.stream()
                .map(Session::create)
                .map(Session::duration)
                .mapToLong(Duration::toMinutes)
                .sum();
            return Duration.ofMinutes(totalMinutes).dividedBy(sessions.size());
        } catch (DateTimeException | IndexOutOfBoundsException e) {
            throw new RuntimeException("wrong string format", e);
        }
    }

    private SessionUtils() {
    }

}
