package edu.hw5.Task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class FridayThirteenAdjuster implements TemporalAdjuster {

    public static final int SEARCHED_DAY_OF_MONTH = 13;

    @Override
    public Temporal adjustInto(Temporal temporal) {
        var current = LocalDate.from(temporal);
        if (current.getDayOfMonth() > SEARCHED_DAY_OF_MONTH) {
            current = current.plusMonths(1);
        }
        current = current.withDayOfMonth(SEARCHED_DAY_OF_MONTH);
        while (current.getDayOfWeek() != DayOfWeek.FRIDAY) {
            current = current.plusMonths(1);
        }
        return current;
    }

}
