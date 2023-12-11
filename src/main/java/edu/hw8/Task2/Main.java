package edu.hw8.Task2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final int FIBONACCI_BASE = 3;
    public static final double NANOSECONDS_IN_SECOND = 1e9;

    private static int fib(int n) {
        return n < FIBONACCI_BASE ? 1 : fib(n - 1) + fib(n - 2);
    }

    private static int[] firstNFibSingleThread(int n) {
        int[] result = new int[n];
        for (int i = 0; i < n; ++i) {
            result[i] = fib(i + 1);
        }
        return result;
    }

    private static int[] firstNFibMultithreaded(int n, int threads) {
        var threadPool = FixedThreadPool.create(threads);
        threadPool.start();

        int[] result = new int[n];
        for (int i = 0; i < n; ++i) {
            int finalI = i;
            threadPool.execute(() -> {
                int fib = fib(finalI + 1);
                synchronized (result) {
                    result[finalI] = fib;
                }
            });
        }

        try {
            threadPool.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private static void runTest(Runnable test, String message) {
        long start = System.nanoTime();
        test.run();
        long time = System.nanoTime() - start;
        LOGGER.info(message + ": " + ((double) time / NANOSECONDS_IN_SECOND) + " seconds");
    }

    @SuppressWarnings("MagicNumber")
    public static void main(String[] args) {
        int n = 45;
        runTest(() -> firstNFibMultithreaded(n, 2), "Thread pool with two threads");
        runTest(() -> firstNFibMultithreaded(n, 4), "Thread pool with four threads");
        runTest(() -> firstNFibMultithreaded(n, 8), "Thread pool with eight threads");
        runTest(() -> firstNFibMultithreaded(n, 12), "Thread pool with twelve threads");
        runTest(() -> firstNFibSingleThread(n), "Single thread");
        runTest(() -> firstNFibMultithreaded(n, 1), "Thread pool with one thread");
    }

    private Main() {
    }

}
