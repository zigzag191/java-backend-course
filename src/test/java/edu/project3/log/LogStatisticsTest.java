package edu.project3.log;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LogStatisticsTest {

    @SuppressWarnings("MagicNumber")
    static Stream<Arguments> statisticsShouldBeCalculatedCorrectly() {
        return Stream.of(
            arguments(
                LogTestData.ENTRIES,
                new LogStatistics(
                    2,
                    Map.of("/downloads/product_1", 2L),
                    Map.of(HttpCode.CODE_200, 1L, HttpCode.CODE_304, 1L),
                    Map.of("GET", 2L),
                    Map.of(
                        "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)", 1L,
                        "Debian APT-HTTP/1.3 (0.8.10.3)", 1L
                    ),
                    245
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void statisticsShouldBeCalculatedCorrectly(List<LogEntry> entries, LogStatistics expected) {
        var stat = LogStatistics.calculate(entries);
        assertThat(stat).isEqualTo(expected);
    }

}
