package edu.project4.fractals;

import edu.project4.renderer.Point;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FractalFunctionTest {

    public static final int NUMBER_OF_SIMULATIONS = 10;

    @Test
    @SuppressWarnings("MagicNumber")
    void fractalFunctionShouldUseAllVariations() {
        var variation1Flag = new AtomicInteger(0);
        var variation2Flag = new AtomicInteger(0);

        var function = FractalFunction.builder()
            .addVariation((x, y, r, theta, phi) -> {
                variation1Flag.incrementAndGet();
                return new Point(0, 0);
            }, 0.5)
            .addVariation((x, y, r, theta, phi) -> {
                variation2Flag.incrementAndGet();
                return new Point(0, 0);
            }, 0.5)
            .build();

        for (int i = 0; i < NUMBER_OF_SIMULATIONS; ++i) {
            function.apply(new Point(0, 0));
        }

        assertThat(variation1Flag).hasValue(NUMBER_OF_SIMULATIONS);
        assertThat(variation2Flag).hasValue(NUMBER_OF_SIMULATIONS);
    }

}
