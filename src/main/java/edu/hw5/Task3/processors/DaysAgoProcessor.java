package edu.hw5.Task3.processors;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class DaysAgoProcessor extends DateProcessor {

    private static final Pattern DATE_PATTERN = Pattern.compile("^([0-9]+) days? ago$");

    public DaysAgoProcessor(DateProcessor next) {
        super(next);
    }

    @Override
    protected LocalDate tryProcess(String date) {
        var matcher = DATE_PATTERN.matcher(date);
        if (matcher.matches()) {
            return LocalDate
                .now()
                .minusDays(Integer.parseInt(matcher.group(1)));
        }
        return null;
    }

}
