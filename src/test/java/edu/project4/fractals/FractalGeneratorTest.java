package edu.project4.fractals;

import edu.project4.renderer.Point;
import edu.project4.renderer.Rectangle;
import edu.project4.renderer.Renderer;
import java.awt.Color;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FractalGeneratorTest {

    @Test
    @SuppressWarnings("MagicNumber")
    void generatorShouldMakeExpectedAmountOfDrawCalls() {
        var viewport = new Rectangle(new Point(-1.0, 1.0), 2, 2);
        var renderer = new RendererMock(viewport, 100, 4);
        var functions = Map.of(FractalFunction.builder().build(), 1.0);
        var set = new FunctionSet(functions);

        int iterations = 100;
        int samples = 120;
        int symmetryRotations = 3;
        var generator = new FractalGenerator(renderer, set, iterations, samples, 12, symmetryRotations);

        generator.generate();

        assertThat(renderer.drawCallCounter).isEqualTo(iterations * samples * symmetryRotations);
    }

    static class RendererMock extends Renderer {

        int drawCallCounter = 0;

        RendererMock(Rectangle viewport, int pixelsPerUnit, int pixelsPerSample) {
            super(viewport, pixelsPerUnit, pixelsPerSample);
        }

        @Override
        public synchronized void drawPoint(Point p, Color c) {
            ++drawCallCounter;
            super.drawPoint(p, c);
        }

    }

}
