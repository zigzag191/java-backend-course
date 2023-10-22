package edu.hw3.Task8;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

public class BackwardIterator<T> implements Iterator<T> {

    private final ListIterator<T> base;

    public BackwardIterator(List<T> list) {
        base = list.listIterator(list.size());
    }

    @Override
    public boolean hasNext() {
        return base.hasPrevious();
    }

    @Override
    public T next() {
        return base.previous();
    }

    @Override
    public void remove() {
        base.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        while (hasNext()) {
            action.accept(next());
        }
    }

}
