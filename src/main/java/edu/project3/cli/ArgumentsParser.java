package edu.project3.cli;

import edu.project3.exceptions.WrongArgumentsException;
import edu.project3.logsource.FileSource;
import edu.project3.logsource.HttpSource;
import edu.project3.logsource.LogSource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ArgumentsParser {

    private ArgumentsParser() {
    }

    public static CommandLineArguments parse(String[] arguments) {
        var extracted = extractArguments(arguments);
        var builder = CommandLineArguments.builder();

        var from = extracted.get("--from");
        if (from == null) {
            builder.setFrom(LocalDate.MIN);
        } else {
            try {
                builder.setFrom(LocalDate.parse(from, DateTimeFormatter.ISO_LOCAL_DATE));
            } catch (DateTimeException e) {
                throw new WrongArgumentsException("wrong date time format in --from", e);
            }
        }

        var to = extracted.get("--to");
        if (to == null) {
            builder.setTo(LocalDate.MAX);
        } else {
            try {
                builder.setTo(LocalDate.parse(to, DateTimeFormatter.ISO_LOCAL_DATE));
            } catch (DateTimeException e) {
                throw new WrongArgumentsException("wrong date time format in --to", e);
            }
        }

        var outputFormat = extracted.get("--format");
        if (outputFormat == null) {
            builder.setFormat(OutputFormat.MARKDOWN);
        } else {
            builder.setFormat(getFormat(outputFormat));
        }

        var path = extracted.get("--path");
        if (path == null) {
            throw new WrongArgumentsException("--path is required");
        }
        builder.setSources(Arrays.stream(path.split("\\s"))
            .map(ArgumentsParser::getLogSources)
            .flatMap(List::stream)
            .toList());
        builder.setResource(path);

        return builder.build();
    }

    private static List<LogSource> getLogSources(String path) {
        if (path.startsWith("http://") || path.startsWith("https://")) {
            try {
                return List.of(new HttpSource(new URI(path)));
            } catch (URISyntaxException e) {
                throw new WrongArgumentsException("wrong url formal", e);
            }
        }

        int globIndex = path.indexOf('*');
        int startDirEndIndex = path.lastIndexOf('/', globIndex);
        var startDir = Path.of(path.substring(0, startDirEndIndex));
        var glob = path.substring(startDirEndIndex + 1);

        var pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
        var result = new ArrayList<LogSource>();

        try {
            Files.walkFileTree(startDir, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(startDir.relativize(file))) {
                        result.add(new FileSource(file));
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new WrongArgumentsException("wrong glob pattern", e);
        }

        return result;
    }

    private static OutputFormat getFormat(String format) {
        if ("markdown".equals(format)) {
            return OutputFormat.MARKDOWN;
        }
        if ("adoc".equals(format)) {
            return OutputFormat.ADOC;
        }
        throw new WrongArgumentsException("format must be either markdown or adoc. actual: " + format);
    }

    private static Map<String, String> extractArguments(String[] arguments) {
        if (arguments.length % 2 != 0) {
            throw new WrongArgumentsException("inconsistent argument name-value pairs");
        }

        var result = new HashMap<String, String>();
        for (int i = 0; i < arguments.length; i += 2) {
            if (!arguments[i].startsWith("--")) {
                throw new WrongArgumentsException(
                    "argument name must start with \"--\". actual: " + arguments[i]
                );
            }
            result.put(arguments[i], arguments[i + 1]);
        }

        return result;
    }

}
