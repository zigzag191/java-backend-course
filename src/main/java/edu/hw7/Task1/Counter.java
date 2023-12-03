package edu.hw7.Task1;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private final AtomicInteger value;

    public Counter() {
        value = new AtomicInteger();
    }

    public synchronized void increment() {
        value.incrementAndGet();
    }

    public int getValue() {
        return value.intValue();
    }

}
