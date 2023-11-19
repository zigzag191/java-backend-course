package edu.hw6.Task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class SocketScanner {

    private static final Map<Integer, PortInfo> TCP_PORTS_INFO;
    private static final Map<Integer, PortInfo> UDP_PORTS_INFO;
    public static final String HOSTNAME = "localhost";
    public static final String UNKNOWN_DESCRIPTION = "Неизвестно";
    public static final int NUMBER_OF_PORT_INFO_COMPONENTS = 3;

    static {
        TCP_PORTS_INFO = new HashMap<>();
        UDP_PORTS_INFO = new HashMap<>();
        loadPortsData();
    }

    private SocketScanner() {
    }

    public static List<PortInfo> getBoundPorts(int start, int end) {
        var result = new ArrayList<PortInfo>();
        for (int i = start; i <= end; ++i) {
            try (var tcpSocket = new ServerSocket()) {
                tcpSocket.bind(new InetSocketAddress(HOSTNAME, i));
            } catch (IOException e) {
                var info = TCP_PORTS_INFO.get(i);
                if (info != null) {
                    result.add(info);
                } else {
                    result.add(new PortInfo(i, Protocol.TCP, UNKNOWN_DESCRIPTION));
                }
            }
            try (var udpSocket = new DatagramSocket(null)) {
                udpSocket.bind(new InetSocketAddress(HOSTNAME, i));
            } catch (IOException e) {
                var info = UDP_PORTS_INFO.get(i);
                if (info != null) {
                    result.add(info);
                } else {
                    result.add(new PortInfo(i, Protocol.UDP, UNKNOWN_DESCRIPTION));
                }
            }
        }
        return result;
    }

    private static void loadPortsData() {
        var scanner = getPortsDataScanner();
        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            var portInfo = parsePortInfo(line);
            portInfo.forEach(info -> {
                if (info.protocol() == Protocol.TCP) {
                    TCP_PORTS_INFO.put(info.port(), info);
                } else {
                    UDP_PORTS_INFO.put(info.port(), info);
                }
            });
        }
    }

    private static List<PortInfo> parsePortInfo(String line) {
        var split = line.split("\\|");

        var portInfo = split[0].split(",");
        var description = split[1];
        int port = Integer.parseInt(portInfo[0]);

        if (portInfo.length == NUMBER_OF_PORT_INFO_COMPONENTS) {
            return List.of(
                new PortInfo(port, Protocol.UDP, description),
                new PortInfo(port, Protocol.TCP, description)
            );
        }

        return List.of(new PortInfo(port, getProtocol(portInfo[1]), description));
    }

    private static Protocol getProtocol(String protocolName) {
        if ("TCP".equals(protocolName)) {
            return Protocol.TCP;
        }
        return Protocol.UDP;
    }

    private static Scanner getPortsDataScanner() {
        var classloader = Thread.currentThread().getContextClassLoader();
        var is = classloader.getResourceAsStream("ports.txt");
        if (is == null) {
            throw new RuntimeException();
        }
        return new Scanner(is);
    }

}
