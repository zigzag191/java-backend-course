package edu.project3.logsource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StringSource implements LogSource {

    private final String data;

    public StringSource(String data) {
        this.data = data;
    }

    @Override
    public InputStream newInputStream() {
        return new ByteArrayInputStream(data.getBytes());
    }

}
