package edu.project4.image;

import edu.project4.renderer.Canvas;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class ImageUtilsTest {

    @TempDir
    static Path tempDir;

    @Test
    @SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
    void savingPNGWithoutGammaCorrectionShouldPreserveColors() throws IOException {
        var canvas = new Canvas(100, 100);

        canvas.hitPixel(50, 50, Color.ORANGE);
        var savedColor = canvas.getPixel(50, 50).getColor().getRGB();

        ImageUtils.save(canvas, tempDir.resolve("test"), ImageFormat.PNG);
        var image = ImageIO.read(tempDir.resolve("test").toFile());

        var loadedColor = image.getRGB(50, 50);

        assertThat(savedColor).isEqualTo(loadedColor);
    }

}
