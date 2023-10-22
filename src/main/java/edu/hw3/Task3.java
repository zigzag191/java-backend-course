package edu.hw3;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Task3 {

    public static <T> Map<T, Integer> freqDict(List<T> objects) {
        Objects.requireNonNull(objects);
        return objects.stream()
            .collect(Collectors.toMap(
                Function.identity(),
                o -> 1,
                Integer::sum
            ));
    }

    private Task3() {
    }

}
