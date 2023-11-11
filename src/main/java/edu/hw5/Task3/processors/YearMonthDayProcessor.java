package edu.hw5.Task3.processors;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;

public class YearMonthDayProcessor extends DateProcessor {

    public static final int COMPONENTS_OF_DATE = 3;

    public YearMonthDayProcessor(DateProcessor next) {
        super(next);
    }

    @Override
    protected LocalDate tryProcess(String date) {
        try {
            var parsedDate = Arrays.stream(date.split("-"))
                .mapToInt(Integer::parseInt)
                .toArray();
            if (parsedDate.length != COMPONENTS_OF_DATE) {
                return null;
            }
            return LocalDate.of(parsedDate[0], parsedDate[1], parsedDate[2]);
        } catch (NumberFormatException | DateTimeException e) {
            return null;
        }
    }

}
