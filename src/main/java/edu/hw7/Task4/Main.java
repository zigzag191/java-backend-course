package edu.hw7.Task4;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static edu.hw7.Task4.PiCalculationUtils.calculatePiMultithreadedWithFutures;

public final class Main {

    private static final int NUMBER_OF_SIMULATIONS = 10;
    private static final long ITERATIONS = 10000000;
    private static final int NUMBER_OF_THREADS = 12;
    private static final List<Long> LONG_LIST = List.of(10000000L, 100000000L, 1000000000L);
    private static final int NANOSECONDS_IN_SECONDS = 1000000000;
    private static final Logger LOGGER = LogManager.getLogger();

    private Main() {
    }

    private static double runTest(Consumer<Long> piCalculator) {
        long start = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_SIMULATIONS; ++i) {
            piCalculator.accept(ITERATIONS);
        }
        return (System.nanoTime() - start) / (double) NUMBER_OF_SIMULATIONS / NANOSECONDS_IN_SECONDS;
    }

    private static void printCalculationErrors() throws ExecutionException, InterruptedException {
        for (long iteration : LONG_LIST) {
            double result = calculatePiMultithreadedWithFutures(iteration, NUMBER_OF_THREADS);
            double error = Math.abs(Math.PI - result);
            LOGGER.info(iteration + " iterations. Error: " + error);
        }
    }

    private static void printAverageSpeedUpTime() {
        for (long threadCount = 2; threadCount <= NUMBER_OF_THREADS; ++threadCount) {
            double single = runTest(PiCalculationUtils::calculatePi);

            long finalThreadCount = threadCount;
            double multiThreaded =
                runTest(i -> {
                    try {
                        PiCalculationUtils.calculatePiMultithreadedWithFutures(i, finalThreadCount);
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

            LOGGER.info(threadCount + " threads. Difference: " + (single - multiThreaded) + " seconds");
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        printCalculationErrors();
        printAverageSpeedUpTime();
    }

}
