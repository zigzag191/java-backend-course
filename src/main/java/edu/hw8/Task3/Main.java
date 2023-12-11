package edu.hw8.Task3;

import java.util.Map;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {

    public static final int NUMBER_OF_TESTS = 10;
    public static final int MAX_PASSWORD_LENGTH = 4;
    public static final double NANOSECONDS_IN_SECOND = 1e9;
    private static final Logger LOGGER = LogManager.getLogger();

    private Main() {
    }

    private static void runTest(Supplier<PasswordBreaker> breakerSupplier, String message) {
        var breaker = breakerSupplier.get();
        long time = 0;
        for (int i = 0; i < NUMBER_OF_TESTS; ++i) {
            long start = System.nanoTime();
            breaker.getPasswords();
            time += System.nanoTime() - start;
        }
        time /= NUMBER_OF_TESTS;
        LOGGER.info(message + ": " + (time / NANOSECONDS_IN_SECOND) + " seconds");
    }

    @SuppressWarnings("MagicNumber")
    public static void main(String[] args) {
        var passwords = Map.of(
            "962012d09b8170d912f0669f6d7d9d07", "qwerty-enjoying",
            "1bc29b36f623ba82aaf6724fd3b16718", "md5-user",
            "fa246d0262c3925617b0c72bb20eeb1d", "number-fan"
        );

        runTest(() -> new SingleThreadPasswordBreaker(passwords, MAX_PASSWORD_LENGTH), "single threaded");
        runTest(() -> new MultithreadedPasswordBreaker(passwords, 1, MAX_PASSWORD_LENGTH), "thread pool with 1 thread");
        runTest(() -> new MultithreadedPasswordBreaker(passwords, 2, MAX_PASSWORD_LENGTH), "2 threads");
        runTest(
            () -> new MultithreadedPasswordBreaker(passwords, MAX_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH),
            "4 threads"
        );
        runTest(() -> new MultithreadedPasswordBreaker(passwords, 8, MAX_PASSWORD_LENGTH), "8 threads");
        runTest(() -> new MultithreadedPasswordBreaker(passwords, 12, MAX_PASSWORD_LENGTH), "12 threads");
    }

}
