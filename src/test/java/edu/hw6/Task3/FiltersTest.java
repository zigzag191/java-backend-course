package edu.hw6.Task3;

import edu.hw6.FileIOTestBase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
public class FiltersTest extends FileIOTestBase {

    @BeforeEach
    void createTestFiles() throws IOException {
        Files.createFile(TEMP_DIRECTORY.resolve("test.txt"));
        Files.createFile(TEMP_DIRECTORY.resolve("qwerty.md"));
        Files.write(TEMP_DIRECTORY.resolve("pseudo-png.png"), new byte[] {89, 'P', 'N', 'G'});
        Files.write(TEMP_DIRECTORY.resolve("5bytes.dat"), new byte[5]);
        Files.write(TEMP_DIRECTORY.resolve("500bytes.dat"), new byte[500]);
        Files.write(TEMP_DIRECTORY.resolve("600bytes.dat"), new byte[600]);
        Files.write(TEMP_DIRECTORY.resolve("250bytes.dat"), new byte[250]);
    }

    @Test
    void magicNumbersShouldBeFilteredCorrectly() throws IOException {
        var filter = Filters.hasMagicNumber(new byte[] {89, 'P', 'N', 'G'})
            .add(Filters.hasExtension("png"))
            .add(Filters.hasSize(4));
        try (var stream = Files.newDirectoryStream(TEMP_DIRECTORY, filter)) {
            var iterator = stream.iterator();
            assertThat(iterator).hasNext();
            var file = iterator.next();
            assertThat(file).hasFileName("pseudo-png.png");
        }
    }

    @Test
    void sizeShouldBeFilteredCorrectly() throws IOException {
        var filter = Filters.largerThan(10)
            .add(Filters.smallerThan(600));
        try (var stream = Files.newDirectoryStream(TEMP_DIRECTORY, filter)) {
            var results = new ArrayList<String>();
            stream.iterator().forEachRemaining(p -> results.add(p.getFileName().toString()));
            assertThat(results).containsExactlyInAnyOrder("500bytes.dat", "250bytes.dat");
        }
    }

    @Test
    void regexShouldBeAppliedCorrectly() throws IOException {
        var filter = Filters.regexMatches("[0-9]+[a-z]+.[a-z]+");
        try (var stream = Files.newDirectoryStream(TEMP_DIRECTORY, filter)) {
            var results = new ArrayList<String>();
            stream.iterator().forEachRemaining(p -> results.add(p.getFileName().toString()));
            assertThat(results).containsExactlyInAnyOrder(
                "500bytes.dat", "250bytes.dat", "5bytes.dat", "600bytes.dat"
            );
        }
    }

    @Test
    void attributeFilterShouldBeAppliedCorrectly() throws IOException {
        var filter = Filters.checkAttributes(BasicFileAttributes.class, attrs ->
            attrs.size() == 600 && attrs.creationTime().toInstant().isBefore(Instant.now()));

        try (var stream = Files.newDirectoryStream(TEMP_DIRECTORY, filter)) {
            var iterator = stream.iterator();
            assertThat(iterator).hasNext();
            var file = iterator.next();
            assertThat(file).hasFileName("600bytes.dat");
        }
    }

}
