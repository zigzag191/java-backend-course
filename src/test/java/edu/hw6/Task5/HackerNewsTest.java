package edu.hw6.Task5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

public class HackerNewsTest {

    @ParameterizedTest
    @CsvSource({
        "37570037, JDK 21 Release Notes",
        "375701, The cost of goods and services offered by cybercriminals",
        "375702, Twitter rebuffs Facebook's advances"
    })
    void titlesShouldBeLoadedCorrectly(long id, String expectedTitle) {
        assertThat(HackerNews.news(id)).isEqualTo(expectedTitle);
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 0, 100000000})
    void invalidIdsShouldProduceEmptyStrings(long id) {
        assertThat(HackerNews.news(id)).isEmpty();
    }

}
