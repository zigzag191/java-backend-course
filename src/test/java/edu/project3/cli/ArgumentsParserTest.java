package edu.project3.cli;

import edu.project3.exceptions.WrongArgumentsException;
import edu.project3.logsource.FileSource;
import edu.project3.logsource.HttpSource;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
public class ArgumentsParserTest {

    @BeforeAll
    static void createLogsDirectory() throws IOException {
        Files.createDirectory(Path.of("logs"));

        Files.createFile(Path.of("logs/2023.txt"));
        Files.createFile(Path.of("logs/2023-10-04"));

        Files.createDirectory(Path.of("logs/more-logs"));
        Files.createFile(Path.of("logs/more-logs/2023-08-31.txt"));

        Files.createDirectory(Path.of("logs/even-more-logs"));
        Files.createFile(Path.of("logs/even-more-logs/2023-08-31.txt"));
    }

    @AfterAll
    static void deleteLogsDirectory() {
        try {
            Files.walkFileTree(Path.of("logs"), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path directory, IOException exc) throws IOException {
                    Files.delete(directory);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (RuntimeException | IOException ignored) {
        }
    }

    static Stream<Arguments> argumentsShouldBeParsedCorrectly() {
        return Stream.of(
            arguments(
                new String[] {"--path", "logs/2023*", "--from", "2023-08-31", "--format", "markdown"},
                new CommandLineArguments(
                    List.of(
                        new FileSource(Path.of("logs/2023.txt")),
                        new FileSource(Path.of("logs/2023-10-04"))
                    ),
                    LocalDate.of(2023, 8, 31),
                    LocalDate.MAX,
                    OutputFormat.MARKDOWN,
                    "logs/2023*"
                )
            ),
            arguments(
                new String[] {"--path", "https://my.logs/logs", "--format", "adoc"},
                new CommandLineArguments(
                    List.of(new HttpSource(URI.create("https://my.logs/logs"))),
                    LocalDate.MIN,
                    LocalDate.MAX,
                    OutputFormat.ADOC,
                    "https://my.logs/logs"
                )
            ),
            arguments(
                new String[] {"--path", "logs/**/2023-08-31.txt"},
                new CommandLineArguments(
                    List.of(
                        new FileSource(Path.of("logs/more-logs/2023-08-31.txt")),
                        new FileSource(Path.of("logs/even-more-logs/2023-08-31.txt"))
                    ),
                    LocalDate.MIN,
                    LocalDate.MAX,
                    OutputFormat.MARKDOWN,
                    "logs/**/2023-08-31.txt"
                )
            ),
            arguments(
                new String[] {"--path", "logs/more-logs/* https://my.logs/logs"},
                new CommandLineArguments(
                    List.of(
                        new FileSource(Path.of("logs/more-logs/2023-08-31.txt")),
                        new HttpSource(URI.create("https://my.logs/logs"))
                    ),
                    LocalDate.MIN,
                    LocalDate.MAX,
                    OutputFormat.MARKDOWN,
                    "logs/more-logs/* https://my.logs/logs"
                )
            )
        );
    }

    static Stream<Arguments> invalidArgumentsShouldThrow() {
        return Stream.of(
            arguments((Object) new String[] {"--path", "http://my.logs/logs", "--format", "md"}),
            arguments((Object) new String[] {"--from", "2023-08-31", "--to", "2023-08-31", "--format", "markdown"}),
            arguments((Object) new String[] {"--path", "http://my.logs/logs", "--from", "1/2/1999"})
        );
    }

    @ParameterizedTest
    @MethodSource
    void argumentsShouldBeParsedCorrectly(String[] arguments, CommandLineArguments expected) {
        var parsed = ArgumentsParser.parse(arguments);
        assertThat(parsed)
            .usingRecursiveComparison()
            .ignoringFields("sources")
            .isEqualTo(expected);
        assertThat(parsed.sources()).containsExactlyInAnyOrderElementsOf(expected.sources());
    }

    @ParameterizedTest
    @MethodSource
    void invalidArgumentsShouldThrow(String[] arguments) {
        assertThatExceptionOfType(WrongArgumentsException.class)
            .isThrownBy(() -> ArgumentsParser.parse(arguments));
    }

}
