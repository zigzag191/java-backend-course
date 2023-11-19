package edu.hw5.Task1;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SessionUtilsTest {

    @SuppressWarnings("MagicNumber")
    public static Stream<Arguments> averageShouldBeDeterminedCorrectly() {
        return Stream.of(
            arguments(
                List.of("2022-03-12, 20:20 - 2022-03-12, 23:50", "2022-04-01, 21:30 - 2022-04-02, 01:20"),
                Duration.ofMinutes(220)
            ),
            arguments(
                List.of(),
                Duration.ZERO
            )
        );
    }

    public static Stream<List<String>> wrongInputFormatShouldThrow() {
        return Stream.of(
            List.of("2022-13-12, 20:20 - 2022-03-12, 23:50"),
            List.of("2022-03-12, 20:30 - 2022-03-12, 20:10"),
            List.of("2022-03-12, 20:20 - 2022-03-12"),
            List.of("2022-13-12, 20:20")
        );
    }

    @ParameterizedTest
    @MethodSource
    void averageShouldBeDeterminedCorrectly(List<String> sessions, Duration expected) {
        assertThat(SessionUtils.countAverageSessionTime(sessions)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    void wrongInputFormatShouldThrow(List<String> sessions) {
        assertThatExceptionOfType(RuntimeException.class)
            .isThrownBy(() -> SessionUtils.countAverageSessionTime(sessions));
    }

}
