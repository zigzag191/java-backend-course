package edu.hw8.Task1.server;

import java.io.IOException;

public final class Main {

    public static final int PORT = 8081;
    public static final String HOST = "127.0.0.1";

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        var settings = new ServerSettings(PORT, HOST);
        var server = new Server(settings);
        server.start();
        server.close();
    }

}
