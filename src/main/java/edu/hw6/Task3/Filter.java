package edu.hw6.Task3;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public interface Filter extends DirectoryStream.Filter<Path> {

    default Filter add(Filter other) {
        return p -> accept(p) && other.accept(p);
    }

}
