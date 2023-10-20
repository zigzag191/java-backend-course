package edu.hw2.Task3;

import java.util.Random;

public class DefaultConnectionManager implements ConnectionManager {
    private static final Random RNG = new Random();
    private static final int FREQUENCY = 2;
    private int openedConnections = 0;

    public int getOpenedConnections() {
        return openedConnections;
    }

    @Override
    public Connection getConnection() {
        ++openedConnections;
        return RNG.nextInt() % FREQUENCY == 0 ? new StableConnection() : new FaultyConnection();
    }
}
