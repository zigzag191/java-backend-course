package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task4Test {

    static final String TEMP_FILE_NAME = "tmp";
    static final Path TEMP_FILE_PATH = Path.of(TEMP_FILE_NAME);

    @AfterEach
    void deleteTempFile() throws IOException {
        Files.deleteIfExists(TEMP_FILE_PATH);
    }

    @Test
    void fileWithQuoteShouldBeCreated() throws IOException {
        Task4.saveQuoteToFile(TEMP_FILE_NAME);
        assertThat(Files.exists(TEMP_FILE_PATH)).isTrue();
        try (var is = Files.newInputStream(TEMP_FILE_PATH)) {
            var scanner = new Scanner(is);
            var quote = scanner.nextLine();
            assertThat(quote).isEqualTo(Task4.QUOTE);
        }
    }

    @Test
    void ifFileExistsExceptionShouldBeThrown() throws IOException {
        Files.createFile(TEMP_FILE_PATH);
        assertThatExceptionOfType(RuntimeException.class)
            .isThrownBy(() -> Task4.saveQuoteToFile(TEMP_FILE_NAME));
    }

}
