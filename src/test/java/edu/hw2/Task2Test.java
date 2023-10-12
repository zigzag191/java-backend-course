package edu.hw2;

import edu.hw2.Task2.Rectangle;
import edu.hw2.Task2.Square;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("MagicNumber")
public class Task2Test {

    @Test
    @DisplayName("При отрицательной длине стороны должно выбрасываться исключение")
    void settingNegativeSideShouldThrow() {
        var square = new Square(10);
        assertThrows(IllegalArgumentException.class, () -> square.setHeight(-5));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Площадь прямоугольника должна быть одинаковой для всех производных классов")
    void areaShouldBeSameForAllSubclasses(Rectangle rectangle) {
        var newRectangle = rectangle
            .setWidth(20)
            .setHeight(10);
        assertThat(newRectangle.area()).isEqualTo(200.0);
    }

    static Stream<Rectangle> areaShouldBeSameForAllSubclasses() {
        return Stream.of(
            new Rectangle(20, 30),
            new Square(50)
        );
    }

}
