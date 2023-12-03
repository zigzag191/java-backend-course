package edu.hw8.Task1.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final Socket socket;

    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    public String getCatchPhrase(String keyWord) throws IOException {
        sendRequest(keyWord);
        return receiveResponse();
    }

    private void sendRequest(String request) throws IOException {
        socket.getOutputStream().write(request.concat("\n").getBytes());
    }

    private String receiveResponse() throws IOException {
        var scanner = new Scanner(socket.getInputStream());
        return scanner.nextLine();
    }

}
