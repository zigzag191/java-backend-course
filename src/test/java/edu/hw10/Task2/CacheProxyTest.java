package edu.hw10.Task2;

import edu.hw10.Task2.callcache.FileCallCache;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class CacheProxyTest {

    public static final int NUMBER_OF_TEST_RUNS = 10;
    @TempDir
    static Path tempDir;
    public static final String CACHE_FILE_NAME = "cache";
    static Path cacheFile = Path.of(CACHE_FILE_NAME);

    @BeforeAll
    static void initCacheFile() {
        cacheFile = tempDir.resolve(CACHE_FILE_NAME);
    }

    @Test
    void inMemoryCacheShouldWorkCorrectly() {
        assertThatNoException().isThrownBy(this::useMethod);
    }

    void useMethod() throws NoSuchMethodException {
        var c = new MyClassImpl();
        var cached = (MyClass) CacheProxy.create(c, MyClass.class, cacheFile);
        for (int i = 0; i < NUMBER_OF_TEST_RUNS; ++i) {
            cached.calculate(i, "a");
        }
        for (int i = 0; i < NUMBER_OF_TEST_RUNS; ++i) {
            cached.calculate(i, "a");
        }
    }

    @Test
    void fileCacheShouldWorkCorrectly() throws NoSuchMethodException, IOException {
        for (int i = 0; i < NUMBER_OF_TEST_RUNS; ++i) {
            useMethod();
        }

        var objectInputStream = new ObjectInputStream(Files.newInputStream(cacheFile));
        boolean hasNext = true;
        var deserializedCache = new ArrayList<FileCallCache.SerializedCache>();
        while (hasNext) {
            try {
                var next = (FileCallCache.SerializedCache) objectInputStream.readObject();
                deserializedCache.add(next);
            } catch (EOFException eof) {
                hasNext = false;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        var expected = new ArrayList<FileCallCache.SerializedCache>();
        for (int i = 0; i < NUMBER_OF_TEST_RUNS; ++i) {
            expected.add(new FileCallCache.SerializedCache(new Object[]{i, "a"}, i + "a"));
        }

        assertThat(deserializedCache).hasSameSizeAs(expected);
        for (int i = 0; i < deserializedCache.size(); ++i) {
            assertThat(deserializedCache.get(i)).usingRecursiveComparison().isEqualTo(expected.get(i));
        }
    }

    private interface MyClass {
        @Cache(persists = true)
        String calculate(int n, String s);
    }

    private static final class MyClassImpl implements MyClass {

        @Override
        public String calculate(int n, String s) {
            return n + s;
        }

    }

}
