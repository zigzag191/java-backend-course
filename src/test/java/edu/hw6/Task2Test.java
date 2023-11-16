package edu.hw6;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SuppressWarnings("MagicNumber")
public class Task2Test extends FileIOTestBase {

    static void createFile(Path path) throws IOException {
        try (var writer = new PrintWriter(Files.newOutputStream(path))) {
            writer.print("hello, world");
        }
    }

    @Test
    void copiesShouldBeCreatedWithCorrectNames() throws IOException {
        var testFile = TEMP_DIRECTORY.resolve("test");
        createFile(testFile);

        Task2.clonePath(testFile);
        var copy = TEMP_DIRECTORY.resolve("test - копия");
        assertThat(Files.exists(copy)).isTrue();
        assertThat(Files.mismatch(testFile, copy)).isEqualTo(-1);

        Task2.clonePath(copy);
        var copyCopy = TEMP_DIRECTORY.resolve("test - копия - копия");
        assertThat(Files.exists(copyCopy)).isTrue();
        assertThat(Files.mismatch(testFile, copyCopy)).isEqualTo(-1);

        for (int i = 0; i < 10; ++i) {
            Task2.clonePath(testFile);
            copy = TEMP_DIRECTORY.resolve("test - копия " + "(" + (i + 2) + ")");
            assertThat(Files.exists(copy)).isTrue();
            assertThat(Files.mismatch(testFile, copy)).isEqualTo(-1);
        }
    }

    @Test
    void attemptToCopyFileThatDoesNotExistShouldThrow() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Task2.clonePath(TEMP_DIRECTORY.resolve("file")));
    }

}
