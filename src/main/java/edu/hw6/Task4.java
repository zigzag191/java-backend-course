package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

public final class Task4 {

    public static final String QUOTE = "Programming is learned by writing programs. ― Brian Kernighan";

    private Task4() {
    }

    public static void saveQuoteToFile(String fileName) {
        try (var outputStream = Files.newOutputStream(Paths.get(fileName), CREATE_NEW);
             var checked = new CheckedOutputStream(outputStream, new CRC32());
             var buffered = new BufferedOutputStream(checked);
             var writer = new OutputStreamWriter(buffered);
             var printWriter = new PrintWriter(writer)
        ) {
            printWriter.print(QUOTE);
        } catch (IOException e) {
            throw new RuntimeException("file already exists", e);
        }
    }

}
