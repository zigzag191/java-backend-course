package edu.hw9.Task2;

import java.nio.file.Path;
import java.util.List;

public record FilesCountSearchResult(List<Path> directories, int numberOfFiles) {
}
