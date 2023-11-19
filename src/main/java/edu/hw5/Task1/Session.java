package edu.hw5.Task1;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record Session(Duration duration) {

    public static Session create(String sessionString) {
        var parsed = sessionString.split(" - ");
        var start = parseDate(parsed[0]);
        var end = parseDate(parsed[1]);
        if (end.isBefore(start)) {
            throw new DateTimeException("start must be before end");
        }
        return new Session(Duration.between(start, end));
    }

    private static LocalDateTime parseDate(String date) {
        var parsed = date.split(", ");
        var parsedDate = LocalDate.parse(parsed[0]);
        var parsedTime = LocalTime.parse(parsed[1]);
        return LocalDateTime.of(parsedDate, parsedTime);
    }

}
