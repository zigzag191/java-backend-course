package edu.hw8.Task1;

import edu.hw8.Task1.client.Client;
import edu.hw8.Task1.server.Main;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings({"MultipleStringLiterals", "MagicNumber"})
public class ServerTest {

    static Process serverProcess;

    public static Stream<Arguments> serverShouldReturnRightPhrases() {
        return Stream.of(
            arguments("личности", "Не переходи на личности там, где их нет"),
            arguments(
                "оскорбления",
                "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами"
            ),
            arguments(
                "глупый",
                "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма"
            ),
            arguments("интеллект", "Чем ниже интеллект, тем громче оскорбления"),
            arguments("qwerty1234", "ответить тут нечего...")
        );
    }

    @BeforeAll
    static void startServer() throws IOException, InterruptedException {
        var javaHome = System.getProperty("java.home");
        var javaBin = Paths.get(javaHome, "bin", "java").toString();
        var classpath = System.getProperty("java.class.path");
        var className = Main.class.getCanonicalName();
        var builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
        serverProcess = builder.start();
        Thread.sleep(1000);
    }

    @AfterAll
    static void terminateServer() {
        serverProcess.destroy();
    }

    @ParameterizedTest
    @MethodSource
    void serverShouldReturnRightPhrases(String keyword, String expected) throws IOException {
        var client = new Client("127.0.0.1", Main.PORT);
        var phrase = client.getCatchPhrase(keyword);
        assertThat(phrase).isEqualTo(expected);
    }

    @Test
    void serverShouldSupportMultipleClients() {
        var responses = Stream
            .generate(() -> {
                try {
                    return new Client("127.0.0.1", Main.PORT);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .limit(15)
            .parallel()
            .map(c -> {
                try {
                    return c.getCatchPhrase("интеллект");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
        assertThat(responses).containsOnly("Чем ниже интеллект, тем громче оскорбления");
    }

}
