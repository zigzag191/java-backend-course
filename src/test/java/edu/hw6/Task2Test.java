package edu.hw6;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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

    static List<String> getDirectoryContents(Path directory) throws IOException {
        try (var stream = Files.newDirectoryStream(directory)) {
            var result = new ArrayList<String>();
            stream.iterator().forEachRemaining(p -> result.add(p.getFileName().toString()));
            return result;
        }
    }

    @Test
    void copiesShouldBeCreatedWithCorrectNames() throws IOException {
        var testFile = TEMP_DIRECTORY.resolve("test");
        createFile(testFile);

        Task2.clonePath(testFile);
        Task2.clonePath(testFile);
        var contents = getDirectoryContents(TEMP_DIRECTORY);
        assertThat(contents).containsExactlyInAnyOrder("test - копия", "test - копия (2)", "test");
//        var copy = TEMP_DIRECTORY.resolve("test - копия");
//        assertThat(Files.exists(copy)).isTrue();
//        assertThat(Files.mismatch(testFile, copy)).isEqualTo(-1);
//
//        Task2.clonePath(copy);
//        var copyCopy = TEMP_DIRECTORY.resolve("test - копия - копия");
//        assertThat(Files.exists(copyCopy)).isTrue();
//        assertThat(Files.mismatch(testFile, copyCopy)).isEqualTo(-1);
//
//        for (int i = 0; i < 10; ++i) {
//            Task2.clonePath(testFile);
//            copy = TEMP_DIRECTORY.resolve("test - копия " + "(" + (i + 2) + ")");
//            assertThat(Files.exists(copy)).isTrue();
//            assertThat(Files.mismatch(testFile, copy)).isEqualTo(-1);
//        }
    }

    @Test
    void attemptToCopyFileThatDoesNotExistShouldThrow() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Task2.clonePath(TEMP_DIRECTORY.resolve("file")));
    }

}
