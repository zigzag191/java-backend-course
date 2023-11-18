package edu.project3.log;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public record LogStatistics(
    long requestCount,
    Map<String, Long> resourcesFrequencies,
    Map<HttpCode, Long> httpCodesFrequencies,
    Map<String, Long> verbsFrequencies,
    Map<String, Long> userAgentFrequencies,
    long averageBodyBitesSize
) {

    public static LogStatistics calculate(List<LogEntry> entries) {
        long count = entries.size();
        var resourcesFrequencies = entries.stream()
            .collect(Collectors.groupingBy(
                LogEntry::requestedResource,
                TreeMap<String, Long>::new,
                Collectors.counting()
            ));

        var httpCodesFrequencies = entries.stream()
            .collect(Collectors.groupingBy(
                LogEntry::statusCode,
                TreeMap<HttpCode, Long>::new,
                Collectors.counting()
            ));

        var verbsFrequencies = entries.stream()
            .collect(Collectors.groupingBy(
                LogEntry::verb,
                TreeMap<String, Long>::new,
                Collectors.counting()
            ));

        var userAgentFrequencies = entries.stream()
            .collect(Collectors.groupingBy(
                LogEntry::httpUserAgent,
                TreeMap<String, Long>::new,
                Collectors.counting()
            ));

        long averageBodyBitesSize = (long) entries.stream()
            .mapToLong(LogEntry::bodyBitesSend)
            .average()
            .orElse(0);

        return new LogStatistics(
            count,
            resourcesFrequencies,
            httpCodesFrequencies,
            verbsFrequencies,
            userAgentFrequencies,
            averageBodyBitesSize
        );
    }

}
