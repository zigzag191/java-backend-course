package edu.hw2;

import edu.hw2.Task3.DefaultConnectionManager;
import edu.hw2.Task3.FaultyConnection;
import edu.hw2.Task3.FaultyConnectionManager;
import edu.hw2.Task3.PopularCommandExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {

    @Test
    @DisplayName("FaultyConnectionManager должен возвращать FaultyConnection")
    void faultyConnectionManagerShouldReturnFaultyConnection() {
        var manager = new FaultyConnectionManager();
        var connection = manager.getConnection();
        connection.close();
        assertThat(connection).isInstanceOf(FaultyConnection.class);
    }

    @Test
    @DisplayName("PopularCommandExecutor должен использовать хотя бы одно соединение")
    @SuppressWarnings("MagicNumber")
    void popularCommandExecutorShouldUseAtLeastOneConnection() {
        int maxAttempts = 5;
        var connectionManager = new DefaultConnectionManager();
        var executor = new PopularCommandExecutor(connectionManager, maxAttempts);
        try {
            executor.updatePackages();
        } catch (Exception ignored) {
        } finally {
            assertThat(connectionManager.getOpenedConnections())
                .isGreaterThan(0)
                .isLessThanOrEqualTo(maxAttempts);
        }
    }

}
