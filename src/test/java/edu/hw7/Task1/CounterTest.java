package edu.hw7.Task1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CounterTest {

    public static final int THREADS = 10;
    public static final int INCREMENT = 10000;

    @Test
    void counterShouldBeThreadSafe() throws InterruptedException {
        var counter = new Counter();
        int threads = THREADS;
        int increment = INCREMENT;
        IncrementUtils.runMultithreadedIncrement(counter, threads, increment);
        assertThat(counter.getValue()).isEqualTo(threads * increment);
    }

}
