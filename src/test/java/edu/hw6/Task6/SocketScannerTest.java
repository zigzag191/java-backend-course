package edu.hw6.Task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

public class SocketScannerTest {

    @ParameterizedTest
    @ValueSource(ints = {50000, 50001, 60001})
    void boundTcpPortShouldBeDeterminedCorrectly(int port) throws IOException {
        try (var ignored = new ServerSocket(port)) {
            var ports = SocketScanner.getBoundPorts(port, port);
            assertThat(ports)
                .hasSize(1)
                .first()
                .extracting(PortInfo::port, PortInfo::protocol)
                .containsExactly(port, Protocol.TCP);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {50006, 50007, 60308})
    void boundUdpPortShouldBeDeterminedCorrectly(int port) throws IOException {
        try (var ignored = new DatagramSocket(port)) {
            var ports = SocketScanner.getBoundPorts(port, port);
            assertThat(ports)
                .hasSize(1)
                .first()
                .extracting(PortInfo::port, PortInfo::protocol)
                .containsExactly(port, Protocol.UDP);
        }
    }

}
