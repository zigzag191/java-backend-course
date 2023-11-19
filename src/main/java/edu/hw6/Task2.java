package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public final class Task2 {

    private Task2() {
    }

    public static void clonePath(Path path) {
        if (!Files.exists(path) || Files.isDirectory(path)) {
            throw new IllegalArgumentException("path must represent a file");
        }

        var folder = path.toAbsolutePath().getParent();
        var fullName = path.getFileName().toString().split("\\.");
        var name = fullName[0];
        var extension = fullName.length == 2 ? fullName[1] : "";

        var pattern = Pattern
            .compile("^" + name + "(?: - копия(?: \\((?:[2-9]|[1-9][0-9]+)\\))?)?(?:\\." + extension + ")?$");

        try (var copies = Files.find(folder, 1, (p, a) -> pattern.matcher(p.getFileName().toString()).matches())) {
            long count = copies.count();
            var builder = new StringBuilder();
            builder.append(folder).append('/').append(name).append(" - копия");
            if (count > 1) {
                builder.append(" (").append(count).append(")");
            }
            if (!extension.isEmpty()) {
                builder.append(".");
            }
            builder.append(extension);
            var newName = builder.toString();
            Files.copy(path, Paths.get(newName));
        } catch (IOException e) {
            throw new RuntimeException("failed to copy file", e);
        }
    }

}
