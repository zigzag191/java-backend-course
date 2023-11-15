package edu.hw6.Task6;

public record PortInfo(int port, Protocol protocol, String description) {

    @Override
    public String toString() {
        return String.format("%s port %d: %s", protocol.name(), port, description);
    }

}
