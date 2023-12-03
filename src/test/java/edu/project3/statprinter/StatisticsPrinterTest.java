package edu.project3.statprinter;

import edu.project3.cli.CommandLineArguments;
import edu.project3.log.LogStatistics;
import edu.project3.log.LogTestData;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings("MultipleStringLiterals")
public class StatisticsPrinterTest {

    static final String EXPECTED_MD = "### Общая информация" + System.lineSeparator()
        + "|Метрика|Значение|" + System.lineSeparator()
        + "|:-:|:-:|" + System.lineSeparator()
        + "|Источник|in memory|" + System.lineSeparator()
        + "|Начальная дата|-|" + System.lineSeparator()
        + "|Конечная дата|-|" + System.lineSeparator()
        + "|Количество запросов|2|" + System.lineSeparator()
        + "|Средний размер ответа|245|" + System.lineSeparator()
        + System.lineSeparator()
        + "### Запрашиваемые ресурсы" + System.lineSeparator()
        + "|Ресурс|Количество|" + System.lineSeparator()
        + "|:-:|:-:|" + System.lineSeparator()
        + "|/downloads/product_1|2|" + System.lineSeparator()
        + System.lineSeparator()
        + "### Коды ответа" + System.lineSeparator()
        + "|Код|Имя|Количество|" + System.lineSeparator()
        + "|:-:|:-:|:-:|" + System.lineSeparator()
        + "|200|OK|1|" + System.lineSeparator()
        + "|304|Not Modified|1|" + System.lineSeparator()
        + System.lineSeparator()
        + "### Http методы" + System.lineSeparator()
        + "|Метод|Количество|" + System.lineSeparator()
        + "|:-:|:-:|" + System.lineSeparator()
        + "|GET|2|" + System.lineSeparator()
        + System.lineSeparator()
        + "### User agents" + System.lineSeparator()
        + "|User agent|Количество|" + System.lineSeparator()
        + "|:-:|:-:|" + System.lineSeparator()
        + "|Debian APT-HTTP/1.3 (0.8.10.3)|1|" + System.lineSeparator()
        + "|Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)|1|" + System.lineSeparator()
        + System.lineSeparator();

    static final String EXPECTED_ADOC = "=== Общая информация" + System.lineSeparator()
        + "[cols=\"1,1\"]" + System.lineSeparator()
        + "|===" + System.lineSeparator()
        + "|Источник" + System.lineSeparator()
        + "|in memory" + System.lineSeparator()
        + System.lineSeparator()
        + "|Начальная дата" + System.lineSeparator()
        + "|-" + System.lineSeparator()
        + System.lineSeparator()
        + "|Конечная дата" + System.lineSeparator()
        + "|-" + System.lineSeparator()
        + System.lineSeparator()
        + "|Количество запросов" + System.lineSeparator()
        + "|2" + System.lineSeparator()
        + System.lineSeparator()
        + "|Средний размер ответа" + System.lineSeparator()
        + "|245" + System.lineSeparator()
        + System.lineSeparator()
        + "|===" + System.lineSeparator()
        + "=== Запрашиваемые ресурсы" + System.lineSeparator()
        + "[cols=\"1,1\"]" + System.lineSeparator()
        + "|===" + System.lineSeparator()
        + "|/downloads/product_1" + System.lineSeparator()
        + "|2" + System.lineSeparator()
        + System.lineSeparator()
        + "|===" + System.lineSeparator()
        + "=== Коды ответа" + System.lineSeparator()
        + "[cols=\"1,1,1\"]" + System.lineSeparator()
        + "|===" + System.lineSeparator()
        + "|200" + System.lineSeparator()
        + "|OK" + System.lineSeparator()
        + "|1" + System.lineSeparator()
        + System.lineSeparator()
        + "|304" + System.lineSeparator()
        + "|Not Modified" + System.lineSeparator()
        + "|1" + System.lineSeparator()
        + System.lineSeparator()
        + "|===" + System.lineSeparator()
        + "=== Http методы" + System.lineSeparator()
        + "[cols=\"1,1\"]" + System.lineSeparator()
        + "|===" + System.lineSeparator()
        + "|GET" + System.lineSeparator()
        + "|2" + System.lineSeparator()
        + System.lineSeparator()
        + "|===" + System.lineSeparator()
        + "=== User agents" + System.lineSeparator()
        + "[cols=\"1,1\"]" + System.lineSeparator()
        + "|===" + System.lineSeparator()
        + "|Debian APT-HTTP/1.3 (0.8.10.3)" + System.lineSeparator()
        + "|1" + System.lineSeparator()
        + System.lineSeparator()
        + "|Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)" + System.lineSeparator()
        + "|1" + System.lineSeparator()
        + System.lineSeparator()
        + "|===" + System.lineSeparator();

    static final LogStatistics STATISTICS = LogStatistics.calculate(LogTestData.ENTRIES);
    static final CommandLineArguments ARGUMENTS = CommandLineArguments.builder()
        .setSources(null)
        .setFrom(LocalDate.MIN)
        .setTo(LocalDate.MAX)
        .setFormat(null)
        .setResource("in memory")
        .build();

    public static Stream<Arguments> printerShouldFormatReportCorrectly() {
        return Stream.of(
            arguments(
                (Function<OutputStream, StatisticsPrinter>) outputStream ->
                    new MarkdownPrinter(STATISTICS, outputStream, ARGUMENTS),
                EXPECTED_MD
            ),
            arguments(
                (Function<OutputStream, StatisticsPrinter>) outputStream ->
                    new AdocPrinter(STATISTICS, outputStream, ARGUMENTS),
                EXPECTED_ADOC
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void printerShouldFormatReportCorrectly(
        Function<OutputStream, StatisticsPrinter> printerSupplier,
        String expected
    ) {
        var os = new ByteArrayOutputStream();
        var printer = printerSupplier.apply(os);
        printer.print();
        assertThat(os.toString()).isEqualTo(expected);
    }

}
