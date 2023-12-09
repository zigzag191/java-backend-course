package edu.project4.renderer;

import java.awt.Color;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SuppressWarnings("MagicNumber")
public class RendererTest {

    @Test
    void sampledCanvasShouldHaveCorrectDimensions() {
        var viewport = new Rectangle(new Point(100.0, 100.0), 30, 30);
        var renderer = new Renderer(viewport, 2, 5);
        var sampledCanvas = renderer.getMultisampledCanvas();
        assertThat(sampledCanvas.getWidth()).isEqualTo(30 * 2);
        assertThat(sampledCanvas.getHeight()).isEqualTo(30 * 2);
    }

    @Test
    void rendererShouldNotThrowIfPointOutsideViewport() {
        var viewport = new Rectangle(new Point(100.0, 100.0), 30, 30);
        var renderer = new Renderer(viewport, 2, 5);
        assertThatNoException().isThrownBy(() -> renderer.drawPoint(new Point(0, 0), Color.BLACK));
    }

}
