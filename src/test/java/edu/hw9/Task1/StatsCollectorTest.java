package edu.hw9.Task1;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StatsCollectorTest {

    @Test
    @SuppressWarnings("MagicNumber")
    void collectorShouldBeThreadSafe() throws InterruptedException {
        var collector = new StatsCollector(12);

        var threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; ++i) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 10; ++j) {
                    double v1 = (j * 4);
                    double v2 = (j * 4) + 1;
                    double v3 = (j * 4) + 2;
                    double v4 = (j * 4) + 3;
                    collector.push("metric1", v1, v2, v3, v4);
                    collector.push("metric2", v4, v3, v2, v1);
                    collector.push("metric3", v2, v4, v3, v1);
                }
            }));
        }

        threads.forEach(Thread::start);
        for (var thread : threads) {
            thread.join();
        }

        Thread.sleep(200);

        var stats = collector.stats();

        for (var stat : stats.entrySet()) {
            assertThat(stat.getValue())
                .extracting("average", "sum", "max", "min", "count")
                .containsExactly(19.5, 7800.0, 39.0, 0.0, 400);
        }
    }

}
