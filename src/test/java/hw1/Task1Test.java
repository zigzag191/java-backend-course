package hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task1Test {

    @Test
    @DisplayName("Одна минута")
    void oneMinute() {
        String videoLength = "01:00";
        int seconds = Task1.minutesToSeconds(videoLength);
        assertThat(seconds).isEqualTo(60);
    }

    @Test
    @DisplayName("Количество минут больше 60")
    void moreThan60Minutes() {
        String videoLength = "999:59";
        int seconds = Task1.minutesToSeconds(videoLength);
        assertThat(seconds).isEqualTo(59999);
    }

    @Test
    @DisplayName("Некорректный формат входной строки")
    void invalidFormat() {
        String videoLength = "10:60";
        int seconds = Task1.minutesToSeconds(videoLength);
        assertThat(seconds).isEqualTo(-1);
    }

}
