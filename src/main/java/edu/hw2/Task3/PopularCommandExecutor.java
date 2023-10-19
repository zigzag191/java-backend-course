package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PopularCommandExecutor {

    private static final Logger LOGGER = LogManager.getLogger();
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    private void tryExecute(String command) {
        int attempt = 0;
        while (attempt < maxAttempts) {
            try (var connection = manager.getConnection()) {
                connection.execute(command);
                return;
            } catch (ConnectionException e) {
                if (attempt == maxAttempts - 1) {
                    LOGGER.error("unable to execute command after " + maxAttempts + " attempts");
                    throw new ConnectionException("unable to execute command", e);
                }
                LOGGER.warn("failed to execute command, trying again...");
            }
            ++attempt;
        }
    }

}
