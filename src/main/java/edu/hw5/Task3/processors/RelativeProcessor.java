package edu.hw5.Task3.processors;

import java.time.LocalDate;

public class RelativeProcessor extends DateProcessor {

    public RelativeProcessor(DateProcessor next) {
        super(next);
    }

    @Override
    protected LocalDate tryProcess(String date) {
        return switch (date) {
            case "tomorrow" -> LocalDate.now().plusDays(1);
            case "today" -> LocalDate.now();
            case "yesterday" -> LocalDate.now().minusDays(1);
            default -> null;
        };
    }

}
