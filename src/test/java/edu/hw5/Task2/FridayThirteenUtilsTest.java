package edu.hw5.Task2;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FridayThirteenUtilsTest {

    @SuppressWarnings("MagicNumber")
    static Stream<Arguments> fridays13ShouldBeDeterminedCorrectly() {
        return Stream.of(
            arguments(
                1925,
                Stream.of("1925-02-13", "1925-03-13", "1925-11-13").map(LocalDate::parse).toList()
            ),
            arguments(
                2024,
                Stream.of("2024-09-13", "2024-12-13").map(LocalDate::parse).toList()
            )
        );
    }

    public static Stream<Arguments> nextFriday13ShouldBeDeterminedCorrectly() {
        return Stream.of(
            arguments(LocalDate.parse("2028-01-01"), LocalDate.parse("2028-10-13")),
            arguments(LocalDate.parse("2027-02-02"), LocalDate.parse("2027-08-13")),
            arguments(LocalDate.parse("2030-03-03"), LocalDate.parse("2030-09-13"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void fridays13ShouldBeDeterminedCorrectly(int year, List<LocalDate> expected) {
        assertThat(FridayThirteenUtils.countFridaysThirteen(year)).containsExactlyInAnyOrderElementsOf(expected);
    }

    @ParameterizedTest
    @MethodSource
    void nextFriday13ShouldBeDeterminedCorrectly(LocalDate date, LocalDate expected) {
        assertThat(FridayThirteenUtils.findNextFridayThirteen(date)).isEqualTo(expected);
    }

}
