package edu.hw5.Task3.processors;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;

public class DayMonthYearProcessor extends DateProcessor {

    public static final int RELATIVE_MILLENNIAL = 2000;
    public static final int MAX_RELATIVE_VALUE = 1000;
    public static final int COMPONENTS_OF_DATE = 3;

    public DayMonthYearProcessor(DateProcessor next) {
        super(next);
    }

    @Override
    protected LocalDate tryProcess(String date) {
        try {
            var parsedDate = Arrays.stream(date.split("/"))
                .mapToInt(Integer::parseInt)
                .toArray();
            if (parsedDate.length != COMPONENTS_OF_DATE) {
                return null;
            }
            int year = parsedDate[2];
            if (year > 0 && year < MAX_RELATIVE_VALUE) {
                year += RELATIVE_MILLENNIAL;
            }
            return LocalDate.of(year, parsedDate[1], parsedDate[0]);
        } catch (NumberFormatException | DateTimeException e) {
            return null;
        }
    }

}
