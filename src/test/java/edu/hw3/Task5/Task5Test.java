package edu.hw3.Task5;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.hw3.Task5.ContactUtils.parseContacts;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task5Test {

    @SuppressWarnings({"MultipleStringLiterals", "MagicNumber"})
    static Stream<Arguments> contactsShouldBeSorted() {
        return Stream.of(
            arguments(
                new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"}, SortType.ASC,
                Stream.of("Thomas Aquinas", "Rene Descartes", "David Hume", "John Locke")
                    .map(Contact::create)
                    .toArray(Contact[]::new)
            ),
            arguments(
                new String[] {"Paul Erdos", "Leonhard Euler", "Carl Gauss"}, SortType.DESC,
                Stream.of("Carl Gauss", "Leonhard Euler", "Paul Erdos")
                    .map(Contact::create)
                    .toArray(Contact[]::new)
            ),
            arguments(new String[] {}, SortType.DESC, new Contact[] {}),
            arguments(null, SortType.DESC, new Contact[] {})
        );
    }

    @ParameterizedTest
    @DisplayName("Контакты должны быть отсортированы в заданном порядке")
    @MethodSource
    void contactsShouldBeSorted(String[] names, SortType sortType, Contact[] expected) {
        assertThat(parseContacts(names, sortType)).containsExactly(expected);
    }

}
