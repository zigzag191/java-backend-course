package edu.hw9.Task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("MagicNumber")
public class FilesCountSearcherTest {

    public static final String TEST_FILE_NAME = "test";
    @TempDir
    static Path tempDir;

    @BeforeAll
    static void initTempDir() throws IOException {
        var dirA = tempDir.resolve("a");
        Files.createDirectory(dirA);
        for (int i = 0; i < 5; ++i) {
            Files.createFile(dirA.resolve(i + TEST_FILE_NAME));
        }

        var dirAA = dirA.resolve("aa");
        Files.createDirectory(dirAA);
        for (int i = 0; i < 5; ++i) {
            Files.createFile(dirAA.resolve(i + TEST_FILE_NAME));
        }

        var dirB = tempDir.resolve("b");
        Files.createDirectory(dirB);
        for (int i = 0; i < 9; ++i) {
            Files.createFile(dirB.resolve(i + TEST_FILE_NAME));
        }

        var dirC = tempDir.resolve("c");
        Files.createDirectory(dirC);
        var dirCA = dirC.resolve("a");
        Files.createDirectory(dirCA);
        var dirCB = dirC.resolve("b");
        Files.createDirectory(dirCB);

        for (int i = 0; i < 5; ++i) {
            Files.createFile(dirCA.resolve(i + TEST_FILE_NAME));
            Files.createFile(dirCB.resolve(i + TEST_FILE_NAME));
        }
    }

    @Test
    void directoriesSatisfyingConditionsShouldBeFound() {
        var result = FilesCountSearcher.findByFileCount(tempDir, 10);
        assertThat(result)
            .containsExactlyInAnyOrder(tempDir, tempDir.resolve("a"), tempDir.resolve("c"));
    }

}
