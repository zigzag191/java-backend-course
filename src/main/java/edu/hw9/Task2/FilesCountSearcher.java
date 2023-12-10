package edu.hw9.Task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

public class FilesCountSearcher extends RecursiveTask<FilesCountSearchResult> {

    private final Path directory;
    private final int minFilesCount;

    public FilesCountSearcher(Path directory, int minFilesCount) {
        this.directory = directory;
        this.minFilesCount = minFilesCount;
    }

    public static List<Path> findByFileCount(Path initialDirectory, int minFilesCount) {
        try (var threadPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1)) {
            return threadPool.invoke(new FilesCountSearcher(initialDirectory, minFilesCount)).directories();
        }
    }

    @Override
    protected FilesCountSearchResult compute() {
        try (var stream = Files.newDirectoryStream(directory)) {
            var tasks = new ArrayList<RecursiveTask<FilesCountSearchResult>>();
            int files = 0;
            for (var p : stream) {
                if (Files.isDirectory(p)) {
                    tasks.add(new FilesCountSearcher(p, minFilesCount));
                } else {
                    ++files;
                }
            }
            var results = ForkJoinTask.invokeAll(tasks).stream()
                .map(ForkJoinTask::join)
                .toList();
            var totalFiles = files + results.stream()
                .mapToInt(FilesCountSearchResult::numberOfFiles)
                .sum();
            var totalDirectories = Stream.concat(
                totalFiles >= minFilesCount
                    ? Stream.of(directory)
                    : Stream.empty(),
                results.stream()
                    .map(FilesCountSearchResult::directories)
                    .flatMap(List::stream)
            ).toList();
            return new FilesCountSearchResult(totalDirectories, totalFiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
