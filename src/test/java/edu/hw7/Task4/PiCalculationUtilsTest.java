package edu.hw7.Task4;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class PiCalculationUtilsTest {

    static final double EPS = 0.01;
    public static final long NUMBER_OF_ITERATIONS = 1000000L;
    public static final int NUMBER_OF_THREADS = 12;

    public static Stream<Function<Long, Double>> piShouldBeCalculatedWithMinimalError() {
        return Stream.of(
            PiCalculationUtils::calculatePi,
            i -> {
                try {
                    return PiCalculationUtils.calculatePiMultithreaded(i, NUMBER_OF_THREADS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            },
            i -> {
                try {
                    return PiCalculationUtils.calculatePiMultithreadedWithFutures(i, NUMBER_OF_THREADS);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }

    @ParameterizedTest
    @MethodSource
    void piShouldBeCalculatedWithMinimalError(Function<Long, Double> piCalculator) {
        double result = piCalculator.apply(NUMBER_OF_ITERATIONS);
        assertThat(Math.abs(result - Math.PI)).isLessThan(EPS);
    }

}
