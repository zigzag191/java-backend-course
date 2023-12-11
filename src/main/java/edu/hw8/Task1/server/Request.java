package edu.hw8.Task1.server;

import java.io.Serializable;

public record Request(String keyword) implements Serializable {
}
