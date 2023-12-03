package edu.project3.logsource;

import edu.project3.exceptions.LogSourceException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileSource implements LogSource {

    private final Path path;

    public FileSource(Path path) {
        this.path = path;
    }

    @Override
    public InputStream newInputStream() {
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new LogSourceException("failed to open file: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FileSource that = (FileSource) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
