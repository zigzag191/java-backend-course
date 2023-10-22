package edu.hw3.Task8;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BackwardIteratorTest {

    @Test
    @DisplayName("Элементы списка должны возвращаться в обратном порядке")
    @SuppressWarnings("MagicNumber")
    void listElementsShouldBeBackwards() {
        var list = List.of(1, 2, 3, 4);
        var iterator = new BackwardIterator<>(list);
        var reversedList = new ArrayList<Integer>();
        iterator.forEachRemaining(reversedList::add);
        assertThat(reversedList).containsExactly(4, 3, 2, 1);
    }

}
