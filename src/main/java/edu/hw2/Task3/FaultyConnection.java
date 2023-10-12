package edu.hw2.Task3;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Random RNG = new Random();
    private static final int FREQUENCY = 2;

    public FaultyConnection() {
        LOGGER.info("faulty connection was opened");
    }

    @Override
    public void execute(String command) {
        if (RNG.nextInt() % FREQUENCY == 0) {
            throw new ConnectionException("unable to connect");
        }
        LOGGER.info("command " + command + " was executed");
    }

    @Override
    public void close() {
        LOGGER.info("faulty connection was closed");
    }

}
