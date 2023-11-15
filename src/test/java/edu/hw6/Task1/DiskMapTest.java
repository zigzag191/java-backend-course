package edu.hw6.Task1;

import edu.hw6.FileIOTestBase;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("MultipleStringLiterals")
public class DiskMapTest extends FileIOTestBase {

    static boolean diskAndMapAreSynced(DiskMap map) {
        return map.entrySet().stream().allMatch(e -> {
            try {
                var content = new String(Files.readAllBytes(TEMP_DIRECTORY.resolve(e.getKey())));
                return content.equals(e.getValue());
            } catch (IOException ignored) {
                return false;
            }
        });
    }

    @Test
    void entriesOnDiskShouldCorrespondToEntriesInMap() throws IOException {
        var diskMap = new DiskMap(TEMP_DIRECTORY.toString());
        diskMap.putAll(Map.of(
            "key1", "value1",
            "key2", "value2",
            "key3", "value3"
        ));
        assertThat(diskAndMapAreSynced(diskMap)).isTrue();
        diskMap.remove("key1");
        assertThat(diskAndMapAreSynced(diskMap)).isTrue();
        diskMap.put("key2", "another value");
        assertThat(diskAndMapAreSynced(diskMap)).isTrue();
    }

    @Test
    void allEntriesShouldBeSaved() throws IOException {
        var diskMap = new DiskMap(TEMP_DIRECTORY.toString());
        var entries = Map.of(
            "key1", "value1",
            "key2", "value2",
            "key3", "value3"
        );
        diskMap.putAll(entries);
        diskMap = new DiskMap(TEMP_DIRECTORY.toString());
        assertThat(diskMap).containsExactlyInAnyOrderEntriesOf(entries);
    }

    @Test
    void entryShouldBeReplacesCorrectly() throws IOException {
        var diskMap = new DiskMap(TEMP_DIRECTORY.toString());
        diskMap.put("key1", "value1");
        assertThat(diskMap.get("key1")).isEqualTo("value1");
        diskMap.put("key1", "hello, world!");
        diskMap = new DiskMap(TEMP_DIRECTORY.toString());
        assertThat(diskMap.get("key1")).isEqualTo("hello, world!");
    }

    @Test
    void entryShouldBeRemovedCorrectly() throws IOException {
        var diskMap = new DiskMap(TEMP_DIRECTORY.toString());
        var entries = Map.of(
            "key1", "value1",
            "key2", "value2",
            "key3", "value3"
        );
        diskMap.putAll(entries);
        diskMap.remove("key3");
        diskMap = new DiskMap(TEMP_DIRECTORY.toString());
        assertThat(diskMap).containsEntry("key1", "value1").containsEntry("key2", "value2");
        assertThat(diskMap).doesNotContainKey("key3");
    }

}
