package edu.hw8.Task1.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server implements AutoCloseable {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final int BUFFER_SIZE = 128;
    private final ServerSettings settings;
    private final Selector selector = Selector.open();
    private final ServerSocketChannel serverSocket = ServerSocketChannel.open();
    private final RequestHandler handler;
    private final AtomicBoolean isRunning = new AtomicBoolean();

    private final ConcurrentHashMap<SocketChannel, ByteBuffer> clientReadConnections = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<SocketChannel, ByteBuffer> clientWriteConnections = new ConcurrentHashMap<>();

    public Server(ServerSettings settings) throws IOException {
        this.settings = settings;
        handler = new RequestHandler(this);
        setup();
    }

    public void start() throws IOException {
        isRunning.set(true);
        while (isRunning.get()) {
            selector.select();
            var selectedKeys = selector.selectedKeys();
            var iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                processKey(iterator.next());
                iterator.remove();
            }
        }
    }

    public void stop() {
        isRunning.set(false);
    }

    public void registerResponse(Response response, SocketChannel socket) throws ClosedChannelException {
        var key = socket.keyFor(selector);
        key.interestOps(SelectionKey.OP_WRITE);
        clientWriteConnections.put(socket, ByteBuffer.wrap(response.catchPhrase().concat("\n").getBytes()));
        selector.wakeup();
    }

    private void setup() throws IOException {
        serverSocket.bind(new InetSocketAddress(settings.host(), settings.port()));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
    }

    private void processKey(SelectionKey key) {
        try {
            if (key.isAcceptable()) {
                accept();
            } else if (key.isReadable()) {
                read(key);
            } else if (key.isWritable()) {
                write(key);
            }
        } catch (IOException e) {
            var socket = (SocketChannel) key.channel();
            clientReadConnections.remove(socket);
            clientWriteConnections.remove(socket);
            key.cancel();
            LOGGER.error(e);
        }
    }

    private void accept() throws IOException {
        var socket = serverSocket.accept();
        socket.configureBlocking(false);
        socket.register(selector, SelectionKey.OP_READ);
        clientReadConnections.put(socket, ByteBuffer.allocate(BUFFER_SIZE));
        LOGGER.info("Connected to " + socket.getRemoteAddress());
    }

    private void read(SelectionKey key) throws IOException {
        var socket = (SocketChannel) key.channel();
        var buffer = clientReadConnections.get(socket);
        int bytesRead = socket.read(buffer);
        if (bytesRead == -1) {
            clientReadConnections.remove(socket);
            socket.close();
            LOGGER.warn("Connection was closed on read with " + socket.getRemoteAddress());
        } else {
            var request = tryGetRequest(buffer);
            if (request == null) {
                return;
            }
            buffer.clear();
            handler.handleRequest(request, socket);
        }
    }

    private Request tryGetRequest(ByteBuffer buffer) {
        var message = new String(buffer.array());
        var scanner = new Scanner(message);
        if (!message.contains("\n")) {
            LOGGER.info("read partial request");
            return null;
        }
        return new Request(scanner.nextLine().strip());
    }

    private void write(SelectionKey key) throws IOException {
        var socket = (SocketChannel) key.channel();
        var response = clientWriteConnections.get(socket);
        socket.write(response);
        if (response.remaining() == 0) {
            clientWriteConnections.remove(socket);
            key.interestOps(SelectionKey.OP_READ);
        } else {
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
        selector.close();
    }

}
