package edu.hw10.Task2.callcache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCallCache implements CallCache {

    private final Map<List<Object>, Object> cache = new HashMap<>();

    @Override
    public boolean put(Object[] args, Object result) {
        return cache.putIfAbsent(List.of(args), result) == null;
    }

    @Override
    public Object get(Object[] args) {
        return cache.get(List.of(args));
    }

}
