package edu.hw6.Task6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final int MAX_PORT = 10000;

    private Main() {
    }

    public static void main(String[] args) {
        LOGGER.info("Сканируем порты...");
        var ports = SocketScanner.getBoundPorts(1, MAX_PORT);
        ports.forEach(LOGGER::info);
    }

}
