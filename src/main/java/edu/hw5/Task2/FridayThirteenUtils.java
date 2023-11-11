package edu.hw5.Task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class FridayThirteenUtils {

    public static final int MONTHS_IN_YEAR = 12;
    public static final int SEARCHED_DAY_OF_MONTH = 13;

    public static List<LocalDate> countFridaysThirteen(int year) {
        var result = new ArrayList<LocalDate>();
        for (int i = 1; i <= MONTHS_IN_YEAR; ++i) {
            var thirteenDayOfMonth = LocalDate.of(year, i, SEARCHED_DAY_OF_MONTH);
            if (thirteenDayOfMonth.getDayOfWeek() == DayOfWeek.FRIDAY) {
                result.add(thirteenDayOfMonth);
            }
        }
        return result;
    }

    public static LocalDate findNextFridayThirteen(LocalDate date) {
        var adjuster = new FridayThirteenAdjuster();
        return date.with(adjuster);
    }

    private FridayThirteenUtils() {
    }

}
