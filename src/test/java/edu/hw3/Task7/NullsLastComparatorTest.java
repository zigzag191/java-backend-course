package edu.hw3.Task7;

import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("MultipleStringLiterals")
public class NullsLastComparatorTest {

    @Test
    @DisplayName("null можно добавить в TreeMap")
    void nullCanBeAddedToTreeMap() {
        var tree = new TreeMap<String, String>(NullsLastComparator.naturalOrder());
        tree.put(null, "test");

        assertThat(tree.get(null)).isEqualTo("test");
    }

    @Test
    @DisplayName("null должен быть последним")
    void nullShouldBeLast() {
        var tree = new TreeMap<String, String>(NullsLastComparator.naturalOrder());
        tree.put(null, "test1");
        tree.put("test2", "test3");
        tree.put("test4", "test5");

        assertThat(tree.lastEntry().getKey()).isNull();
    }

}
