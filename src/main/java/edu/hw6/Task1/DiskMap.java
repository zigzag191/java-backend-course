package edu.hw6.Task1;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String> {

    private final Map<String, String> cache;
    private final Path storingDirectory;

    public DiskMap(String storingDirectory) throws IOException {
        this.storingDirectory = Path.of(storingDirectory).toAbsolutePath();
        cache = new HashMap<>();
        if (Files.exists(this.storingDirectory)) {
            loadCache();
        } else {
            Files.createDirectory(this.storingDirectory);
        }
    }

    private void loadCache() throws IOException {
        try (var stream = Files.newDirectoryStream(storingDirectory)) {
            stream.forEach(p -> {
                try (var is = Files.newInputStream(p)) {
                    var scanner = new Scanner(is);
                    cache.put(p.getFileName().toString(), scanner.nextLine());
                } catch (IOException e) {
                    throw new RuntimeException("", e);
                }
            });
        }
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return cache.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return cache.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return cache.get(key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        var newFile = storingDirectory.resolve(key);
        try {
            if (!Files.exists(newFile)) {
                Files.createFile(newFile);
            }
            try (var writer = Files.newBufferedWriter(newFile)) {
                var printWriter = new PrintWriter(writer);
                printWriter.print(value);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cache.put(key, value);
    }

    @Override
    public String remove(Object key) {
        var result = cache.remove(key);
        if (result == null) {
            return null;
        }
        removeFile((String) key);
        return result;
    }

    private void removeFile(String key) {
        var oldFile = storingDirectory.resolve(key);
        try {
            Files.delete(oldFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        cache.keySet().forEach(this::removeFile);
        cache.clear();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return cache.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return cache.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return cache.entrySet();
    }

}
