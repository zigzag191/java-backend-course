package edu.hw8.Task2;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("MagicNumber")
public class ThreadPoolTest {

    @Test
    void allTasksShouldBeExecuted() throws InterruptedException {
        var threadPool = FixedThreadPool.create(4);
        var counter = new AtomicInteger();
        for (int i = 0; i < 10; ++i) {
            threadPool.execute(() -> {
                for (int j = 0; j < 10; ++j) {
                    counter.incrementAndGet();
                }
            });
        }
        threadPool.start();
        threadPool.close();
        assertThat(counter).hasValue(100);
    }

}
