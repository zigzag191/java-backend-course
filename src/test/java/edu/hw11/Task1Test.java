package edu.hw11;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {

    static final String EXPECTED_MESSAGE = "Hello, ByteBuddy!";

    @Test
    void toStringShouldReturnCorrectMessage() {
        var object = Task1.createObjectWithToString();
        var toStringCallResult = object.toString();
        assertThat(toStringCallResult).isEqualTo(EXPECTED_MESSAGE);
    }

}
