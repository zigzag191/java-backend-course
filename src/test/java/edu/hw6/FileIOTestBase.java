package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class FileIOTestBase {

    public static final Path TEMP_DIRECTORY = Path.of("tmp");

    @BeforeEach
    void createTempDirectory() throws IOException {
        Files.createDirectory(TEMP_DIRECTORY);
    }

    @AfterEach
    void deleteTempDirectory() throws IOException {
        try (var stream = Files.walk(TEMP_DIRECTORY)) {
            stream.filter(p -> !Files.isDirectory(p)).forEach(p -> {
                try {
                    Files.delete(p);
                } catch (IOException e) {
                    throw new RuntimeException("", e);
                }
            });
            Files.delete(TEMP_DIRECTORY);
        }
    }

}
