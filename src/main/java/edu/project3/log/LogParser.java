package edu.project3.log;

import edu.project3.cli.CommandLineArguments;
import edu.project3.logsource.LogSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class LogParser {

    public static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
        .appendPattern("dd/MMM/yyyy:HH:mm:ss Z")
        .toFormatter(Locale.ENGLISH);
    private static final Pattern LOG_PATTERN = Pattern
        .compile("^(.*) - (.*) \\[(.*)] \"(.*) (.*) (.*)\" (.*) (.*) \"(.*)\" \"(.*)\"$");
    private final CommandLineArguments arguments;

    public LogParser(CommandLineArguments arguments) {
        this.arguments = arguments;
    }

    public List<LogEntry> parse() {
        return arguments.sources().stream()
            .map(this::parseLog)
            .filter(Objects::nonNull)
            .flatMap(List::stream)
            .toList();
    }

    private OffsetDateTime convertToOffsetDateTime(LocalDate date) {
        return OffsetDateTime.from(date
            .atStartOfDay()
            .atZone(ZoneId.of("Europe/Moscow")));
    }

    private List<LogEntry> parseLog(LogSource source) {
        try (var is = source.newInputStream();
             var reader = new InputStreamReader(is);
             var bufferedReader = new BufferedReader(reader)
        ) {
            return bufferedReader.lines()
                .map(this::parseEntry)
                .filter(Objects::nonNull)
                .filter(log -> {
                    var time = log.time();
                    var from = convertToOffsetDateTime(arguments.from());
                    var to = convertToOffsetDateTime(arguments.to());
                    return time.isAfter(from) && time.isBefore(to);
                })
                .toList();
        } catch (IOException e) {
            return null;
        }
    }

    @SuppressWarnings("MagicNumber")
    private LogEntry parseEntry(String line) {
        var matcher = LOG_PATTERN.matcher(line);
        if (!matcher.matches()) {
            return null;
        }
        return new LogEntry(
            matcher.group(1),
            matcher.group(2),
            OffsetDateTime.parse(matcher.group(3), FORMATTER),
            matcher.group(4),
            matcher.group(5),
            HttpCode.of(Integer.parseInt(matcher.group(7))),
            Integer.parseInt(matcher.group(8)),
            matcher.group(9),
            matcher.group(10)
        );
    }

}
