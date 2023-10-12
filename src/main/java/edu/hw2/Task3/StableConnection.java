package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StableConnection implements Connection {

    private static final Logger LOGGER = LogManager.getLogger();

    public StableConnection() {
        LOGGER.info("stable connection was opened");
    }

    @Override
    public void execute(String command) {
        LOGGER.info("command " + command + " was executed");
    }

    @Override
    public void close() {
        LOGGER.info("stable connection was closed");
    }
}
