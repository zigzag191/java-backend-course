package edu.hw7.Task1;

import java.util.ArrayList;

public final class IncrementUtils {

    private IncrementUtils() {
    }

    public static void runMultithreadedIncrement(Counter counter, int numberOfThreads, int incrementPerThread)
        throws InterruptedException {
        var threads = new ArrayList<Thread>();
        for (int i = 0; i < numberOfThreads; ++i) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < incrementPerThread; ++j) {
                    counter.increment();
                }
            }));
        }
        threads.forEach(Thread::start);
        for (var thread : threads) {
            thread.join();
        }
    }

}
