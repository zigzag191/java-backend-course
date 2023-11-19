package edu.hw5.Task3;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DateUtilsTest {

    @SuppressWarnings("MagicNumber")
    public static Stream<Arguments> correctDateFormatsShouldBeRecognized() {
        return Stream.of(
            arguments("2020-10-10", LocalDate.of(2020, 10, 10)),
            arguments("2020-12-2", LocalDate.of(2020, 12, 2)),
            arguments("1/3/1976", LocalDate.of(1976, 3, 1)),
            arguments("1/3/20", LocalDate.of(2020, 3, 1)),
            arguments("tomorrow", LocalDate.now().plusDays(1)),
            arguments("today", LocalDate.now()),
            arguments("yesterday", LocalDate.now().minusDays(1)),
            arguments("1 day ago", LocalDate.now().minusDays(1)),
            arguments("2234 days ago", LocalDate.now().minusDays(2234))
        );
    }

    public static Stream<String> incorrectDateFormatsShouldReturnEmptyOptional() {
        return Stream.of("2019", "2019/12/12", "1-3-1976", "1/2/1978/4");
    }

    @ParameterizedTest
    @MethodSource
    void correctDateFormatsShouldBeRecognized(String date, LocalDate expected) {
        assertThat(DateUtils.parseDate(date)).isPresent().hasValue(expected);
    }

    @ParameterizedTest
    @MethodSource
    void incorrectDateFormatsShouldReturnEmptyOptional(String date) {
        assertThat(DateUtils.parseDate(date)).isEmpty();
    }

}
