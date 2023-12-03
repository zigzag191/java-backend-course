package edu.hw8.Task1.server;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class RequestHandler {

    public static final Map<String, List<String>> PHRASES = Map.of(
        "личности",
        List.of("Не переходи на личности там, где их нет"),
        "оскорбления",
        List.of("Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами"),
        "глупый",
        List.of("А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма"),
        "интеллект",
        List.of("Чем ниже интеллект, тем громче оскорбления")
    );
    public static final int NUMBER_OF_THREADS = 12;
    public static final int SLEEP_TIME = 1000;
    private final ExecutorService threadPool;
    private final Server server;

    public RequestHandler(Server server) {
        this.server = server;
        threadPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    public void handleRequest(Request request, SocketChannel socket) {
        threadPool.execute(() -> {
            imitateWork();
            try {
                server.registerResponse(getResponse(request), socket);
            } catch (ClosedChannelException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Response getResponse(Request request) {
        var responses = PHRASES.get(request.keyword());
        if (responses == null) {
            return new Response("ответить тут нечего...");
        }
        var rng = ThreadLocalRandom.current();
        int index = rng.nextInt(0, responses.size());
        return new Response(responses.get(index));
    }

    private void imitateWork() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException ignored) {
        }
    }

}
