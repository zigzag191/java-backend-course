package edu.hw9.Task1;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatsCollector {

    private final ConcurrentHashMap<String, Statistic> metrics = new ConcurrentHashMap<>();
    private final ExecutorService threadPool;

    public StatsCollector(int numberOfThreads) {
        threadPool = Executors.newFixedThreadPool(numberOfThreads);
    }

    public void push(String metricName, double... values) {
        threadPool.submit(() -> {
            if (values.length == 0) {
                return;
            }
            metrics.compute(metricName, (k, v) -> {
                var result = (v == null) ? new Statistic(values[0]) : v;
                for (int i = (v == null) ? 1 : 0; i < values.length; ++i) {
                    result.update(values[i]);
                }
                return result;
            });
        });
    }

    public Map<String, Statistic> stats() {
        return Collections.unmodifiableMap(metrics);
    }

}
