package edu.hw5.Task3.processors;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

public abstract class DateProcessor {

    private final DateProcessor next;

    public static Builder builder() {
        return new Builder();
    }

    public DateProcessor(DateProcessor next) {
        this.next = next;
    }

    public Optional<LocalDate> processDate(String date) {
        var processedDate = tryProcess(date);
        if (processedDate != null) {
            return Optional.of(processedDate);
        }
        if (next != null) {
            return next.processDate(date);
        }
        return Optional.empty();
    }

    protected abstract LocalDate tryProcess(String date);

    public final static class Builder {

        private DateProcessor current;

        private Builder() {
        }

        public Builder add(Function<DateProcessor, DateProcessor> supplier) {
            current = supplier.apply(current);
            return this;
        }

        public DateProcessor build() {
            return current;
        }

    }

}
