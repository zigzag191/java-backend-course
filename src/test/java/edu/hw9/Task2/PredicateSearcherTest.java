package edu.hw9.Task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PredicateSearcherTest {

    public static final int TEST_PATH_4_SIZE = 100;
    @TempDir static Path tempDir;

    static Path testPath1;
    static Path testPath2;
    static Path testPath3;
    static Path testPath4;

    public static Stream<Arguments> filesSatisfyingConditionsShouldBeFound() {
        return Stream.of(
            arguments(
                (Predicate<Path>) p -> p.toString().endsWith(".bin"),
                List.of(testPath1, testPath4)
            ),
            arguments(
                (Predicate<Path>) p -> {
                    try {
                        return Files.size(p) == 0;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                },
                List.of(testPath1, testPath2, testPath3)
            ),
            arguments(
                (Predicate<Path>) p -> {
                    try {
                        return Files.size(p) == TEST_PATH_4_SIZE;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                },
                List.of(testPath4)
            )
        );
    }

    @BeforeAll
    static void initTempDir() throws IOException {
        testPath1 = tempDir.resolve("test1.bin");

        Files.createDirectory(tempDir.resolve("a"));
        testPath2 = tempDir.resolve("a").resolve("test2.png");
        testPath3 = tempDir.resolve("a").resolve("test3.java");

        Files.createDirectory(tempDir.resolve("b"));
        testPath4 = tempDir.resolve("b").resolve("test4.bin");

        Files.createFile(testPath1);
        Files.createFile(testPath2);
        Files.createFile(testPath3);
        try (var os = Files.newOutputStream(testPath4)) {
            os.write(new byte[TEST_PATH_4_SIZE]);
        }
    }

    @ParameterizedTest
    @MethodSource
    void filesSatisfyingConditionsShouldBeFound(Predicate<Path> predicate, List<Path> expected) {
        var result = PredicateSearcher.findByPredicate(tempDir, predicate);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

}
