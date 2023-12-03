package edu.project3.cli;

import edu.project3.logsource.LogSource;
import java.time.LocalDate;
import java.util.List;

public record CommandLineArguments(
    List<LogSource> sources,
    LocalDate from,
    LocalDate to,
    OutputFormat format,
    String resource
) {

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private List<LogSource> sources;
        private LocalDate from;
        private LocalDate to;
        private OutputFormat format;
        private String resource;

        private Builder() {
        }

        public Builder setSources(List<LogSource> sources) {
            this.sources = sources;
            return this;
        }

        public Builder setFrom(LocalDate from) {
            this.from = from;
            return this;
        }

        public Builder setTo(LocalDate to) {
            this.to = to;
            return this;
        }

        public Builder setFormat(OutputFormat format) {
            this.format = format;
            return this;
        }

        public Builder setResource(String resource) {
            this.resource = resource;
            return this;
        }

        public CommandLineArguments build() {
            return new CommandLineArguments(sources, from, to, format, resource);
        }

    }

}
