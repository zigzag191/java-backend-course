package edu.hw7.Task4;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public final class PiCalculationUtils {

    public static final int RATIO = 4;

    private PiCalculationUtils() {
    }

    public static double calculatePi(long iterations) {
        long totalCount = 0;
        long circleCount = 0;
        var rng = new Random();
        for (long i = 0; i < iterations; ++i) {
            double x = rng.nextDouble(-1.0, 1.0);
            double y = rng.nextDouble(-1.0, 1.0);
            ++totalCount;
            if (x * x + y * y < 1) {
                ++circleCount;
            }
        }
        return (double) (RATIO * circleCount) / totalCount;
    }

    public static double calculatePiMultithreaded(long iterations, long numberOfThreads) throws InterruptedException {
        var totalCount = new AtomicLong();
        var circleCount = new AtomicLong();
        long iterationsPerThread = Math.ceilDiv(iterations, numberOfThreads);

        var threads = new ArrayList<Thread>();
        for (int i = 0; i < numberOfThreads; ++i) {
            threads.add(new Thread(() -> {
                var rng = ThreadLocalRandom.current();
                for (int j = 0; j < iterationsPerThread; ++j) {
                    double x = rng.nextDouble(-1.0, 1.0);
                    double y = rng.nextDouble(-1.0, 1.0);
                    totalCount.incrementAndGet();
                    if (x * x + y * y < 1) {
                        circleCount.incrementAndGet();
                    }
                }
            }));
        }

        threads.forEach(Thread::start);
        for (var thread : threads) {
            thread.join();
        }

        return (double) (RATIO * circleCount.get()) / totalCount.get();
    }

    public static double calculatePiMultithreadedWithFutures(long iterations, long numberOfThreads)
        throws ExecutionException, InterruptedException {
        long iterationsPerThread = Math.ceilDiv(iterations, numberOfThreads);

        var futures = new ArrayList<CompletableFuture<PiCalculationResult>>();
        for (long i = 0; i < numberOfThreads; ++i) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                long totalCount = 0;
                long circleCount = 0;

                var rng = ThreadLocalRandom.current();
                for (long j = 0; j < iterationsPerThread; ++j) {
                    double x = rng.nextDouble(-1.0, 1.0);
                    double y = rng.nextDouble(-1.0, 1.0);
                    ++totalCount;
                    if (x * x + y * y < 1) {
                        ++circleCount;
                    }
                }

                return new PiCalculationResult(totalCount, circleCount);
            }));
        }

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));

        long totalCount = 0;
        long circleCount = 0;
        for (var future : futures) {
            var result = future.get();
            totalCount += result.totalCount();
            circleCount += result.circleCount();
        }

        return (double) (RATIO * circleCount) / totalCount;
    }

    private record PiCalculationResult(long totalCount, long circleCount) {
    }

}
