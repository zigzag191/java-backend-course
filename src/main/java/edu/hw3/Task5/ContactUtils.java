package edu.hw3.Task5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public final class ContactUtils {

    private ContactUtils() {
    }

    public static Contact[] parseContacts(String[] contacts, SortType sortType) {
        Objects.requireNonNull(sortType);

        if (contacts == null) {
            return new Contact[] {};
        }

        return Arrays.stream(contacts)
            .map(Contact::create)
            .sorted(Comparator.nullsLast(Comparator.comparing(
                c -> c.lastName() != null
                    ? c.lastName()
                    : c.firstName(),
                sortType == SortType.ASC
                    ? Comparator.<String>naturalOrder()
                    : Comparator.<String>reverseOrder()
            )))
            .toArray(Contact[]::new);
    }

}
