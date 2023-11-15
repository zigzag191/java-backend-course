package edu.hw6.Task5;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.regex.Pattern;

public final class HackerNews {

    private static final String TOP_STORIES_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    private static final String ITEM_TEMPLATE = "https://hacker-news.firebaseio.com/v0/item/%d.json";
    private static final Pattern TITLE_PATTERN = Pattern.compile("\"title\":\"([^\"]*)\"");
    public static final int OK_200 = 200;

    public static long[] hackerNewsTopStories() {
        var client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

        var request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(TOP_STORIES_URL))
            .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != OK_200) {
                return new long[]{};
            }
            var body = response.body();
            return parseBody(body);
        } catch (IOException | InterruptedException e) {
            return new long[]{};
        }
    }

    public static String news(long id) {
        var client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

        var request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(String.format(ITEM_TEMPLATE, id)))
            .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != OK_200) {
                return "";
            }
            var body = response.body();
            return parseTitle(body);
        } catch (IOException | InterruptedException e) {
            return "";
        }
    }

    private static long[] parseBody(String body) {
        var ids = body.split(",");
        ids[0] = ids[0].substring(1);
        ids[ids.length - 1] = ids[ids.length - 1].substring(0, ids[ids.length - 1].length() - 1);
        return Arrays.stream(ids).mapToLong(Long::parseLong).toArray();
    }

    private static String parseTitle(String json) {
        var matcher = TITLE_PATTERN.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private HackerNews() {
    }

}
