package edu.hw9.Task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class PredicateSearcher extends RecursiveTask<List<Path>> {

    private final Path directory;
    private final Predicate<Path> predicate;

    public PredicateSearcher(Path directory, Predicate<Path> predicate) {
        this.directory = directory;
        this.predicate = predicate;
    }

    public static List<Path> findByPredicate(Path initialDirectory, Predicate<Path> predicate) {
        try (var threadPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1)) {
            return threadPool.invoke(new PredicateSearcher(initialDirectory, predicate));
        }
    }

    @Override
    protected List<Path> compute() {
        try (var directoryStream = Files.newDirectoryStream(directory)) {
            var actions = new ArrayList<RecursiveTask<List<Path>>>();
            var result = new ArrayList<Path>();
            for (var p : directoryStream) {
                if (Files.isDirectory(p)) {
                    actions.add(new PredicateSearcher(p, predicate));
                } else if (predicate.test(p)) {
                    result.add(p);
                }
            }
            result.addAll(ForkJoinTask.invokeAll(actions).stream()
                .map(RecursiveTask::join)
                .flatMap(List::stream)
                .toList());
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
