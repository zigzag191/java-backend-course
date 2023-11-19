package edu.hw5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task5Test {

    @ParameterizedTest
    @ValueSource(strings = {
        "A123BE777",
        "O777OO177",
        "B123AB03"
    })
    void validLicensePlatesShouldBeDeterminedCorrectly(String licensePlate) {
        assertThat(Task5.licensePlateIsValid(licensePlate)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "123ABE777",
        "А123BГ77",
        "A123BE7777"
    })
    void invalidLicensePlatesShouldBeDeterminedCorrectly(String licensePlate) {
        assertThat(Task5.licensePlateIsValid(licensePlate)).isFalse();
    }

}
