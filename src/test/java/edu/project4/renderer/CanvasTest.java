package edu.project4.renderer;

import java.awt.Color;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CanvasTest {

    public static final int NUMBER_OF_SIMULATIONS = 10;
    public static final int CANVAS_DIMENSIONS = 100;
    public static final int N_THREADS = 12;
    public static final int TARGET_PIXEL = 50;

    @Test
    void canvasShouldBeThreadSafe() {
        var canvas = new Canvas(CANVAS_DIMENSIONS, CANVAS_DIMENSIONS);
        try (var service = Executors.newFixedThreadPool(N_THREADS)) {
            for (int i = 0; i < NUMBER_OF_SIMULATIONS; ++i) {
                service.submit(() -> {
                    for (int j = 0; j < NUMBER_OF_SIMULATIONS; ++j) {
                        canvas.hitPixel(TARGET_PIXEL, TARGET_PIXEL, Color.RED);
                    }
                });
            }
        }
        assertThat(canvas.getPixel(TARGET_PIXEL, TARGET_PIXEL).getHitCount())
            .isEqualTo(NUMBER_OF_SIMULATIONS * NUMBER_OF_SIMULATIONS);
    }

}
