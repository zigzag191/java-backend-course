package edu.hw6.Task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public final class Filters {

    private Filters() {
    }

    public static <T extends BasicFileAttributes> Filter checkAttributes(
        Class<T> attributesType,
        Predicate<T> checker
    ) {
        return p -> {
            try {
                var attributes = Files.readAttributes(p, attributesType);
                return checker.test(attributes);
            } catch (IOException e) {
                return false;
            }
        };
    }

    public static Filter hasSize(long bytes) {
        return p -> Files.size(p) == bytes;
    }

    public static Filter largerThan(long bytes) {
        return p -> Files.size(p) > bytes;
    }

    public static Filter smallerThan(long bytes) {
        return p -> Files.size(p) < bytes;
    }

    public static Filter hasExtension(String extension) {
        return p -> p.getFileName().toString().endsWith("." + extension);
    }

    public static Filter regexMatches(String regex) {
        return p -> Pattern.compile(regex).matcher(p.getFileName().toString()).matches();
    }

    public static Filter hasMagicNumber(byte... magicNumber) {
        return p -> {
            try (var input = Files.newInputStream(p)) {
                byte[] bytes = new byte[magicNumber.length];
                if (input.read(bytes) != magicNumber.length) {
                    return false;
                }
                return Arrays.equals(bytes, magicNumber);
            } catch (IOException e) {
                return false;
            }
        };
    }

}
