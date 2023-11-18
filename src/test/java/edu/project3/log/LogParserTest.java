package edu.project3.log;

import edu.project3.cli.CommandLineArguments;
import edu.project3.cli.OutputFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LogParserTest {

    static Stream<Arguments> logsShouldBeParsedCorrectly() {
        return Stream.of(
            arguments(
                new CommandLineArguments(
                    List.of(LogTestData.SOURCE), LocalDate.MIN, LocalDate.MAX, OutputFormat.MARKDOWN, ""
                ),
                LogTestData.ENTRIES
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void logsShouldBeParsedCorrectly(CommandLineArguments arguments, List<LogEntry> expected) {
        var parser = new LogParser(arguments);
        var parsed = parser.parse();
        assertThat(parsed).containsExactlyInAnyOrderElementsOf(expected);
    }

}
