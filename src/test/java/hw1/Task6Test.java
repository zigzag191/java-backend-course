package hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {

    @ParameterizedTest
    @CsvSource({
        "3524, 3",
        "6621, 5",
        "6554, 4",
        "1234, 3"
    })
    @DisplayName("Тест с различными аргументами")
    void differentValues(int input, int expectedResult) {
        int result = Task6.countK(input);
        assertThat(result).isEqualTo(expectedResult);
    }

}
