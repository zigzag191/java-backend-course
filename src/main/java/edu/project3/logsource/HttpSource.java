package edu.project3.logsource;

import edu.project3.exceptions.LogSourceException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class HttpSource implements LogSource {

    private final URI url;

    public HttpSource(URI url) {
        this.url = url;
    }

    @Override
    public InputStream newInputStream() {
        try {
            var client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
            var request = HttpRequest.newBuilder()
                .GET()
                .uri(url)
                .build();
            return client
                .send(request, HttpResponse.BodyHandlers.ofInputStream())
                .body();
        } catch (IOException | InterruptedException e) {
            throw new LogSourceException("failed to load data: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpSource that = (HttpSource) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
