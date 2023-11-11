package edu.hw5.Task3;

import edu.hw5.Task3.processors.DateProcessor;
import edu.hw5.Task3.processors.DayMonthYearProcessor;
import edu.hw5.Task3.processors.DaysAgoProcessor;
import edu.hw5.Task3.processors.RelativeProcessor;
import edu.hw5.Task3.processors.YearMonthDayProcessor;
import java.time.LocalDate;
import java.util.Optional;

public final class DateUtils {

    private static final DateProcessor PROCESSORS_CHAIN = DateProcessor.builder()
        .add(DayMonthYearProcessor::new)
        .add(YearMonthDayProcessor::new)
        .add(RelativeProcessor::new)
        .add(DaysAgoProcessor::new)
        .build();

    public static Optional<LocalDate> parseDate(String date) {
        return PROCESSORS_CHAIN.processDate(date);
    }

    private DateUtils() {
    }

}
