package edu.hw3.Task7;

import java.util.Comparator;

public class NullsLastComparator<T> implements Comparator<T> {

    private final Comparator<T> base;

    public static <U extends Comparable<? super U>> NullsLastComparator<U> naturalOrder() {
        return new NullsLastComparator<>(Comparator.<U>naturalOrder());
    }

    public NullsLastComparator(Comparator<T> base) {
        this.base = base;
    }

    @Override
    public int compare(T o1, T o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null) {
            return 1;
        }
        if (o2 == null) {
            return -1;
        }
        return base.compare(o1, o2);
    }

}
