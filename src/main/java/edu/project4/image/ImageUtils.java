package edu.project4.image;

import edu.project4.renderer.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public final class ImageUtils {

    private ImageUtils() {
    }

    public static boolean applyGammaCorrectionAndSave(Canvas canvas, Path file, ImageFormat outputFormat, double gamma)
        throws IOException {
        return save(canvas, file, outputFormat, gamma, true);
    }

    public static boolean save(Canvas canvas, Path file, ImageFormat outputFormat)
        throws IOException {
        return save(canvas, file, outputFormat, 0.0, false);
    }

    private static boolean save(
        Canvas canvas,
        Path file,
        ImageFormat outputFormat,
        double gamma,
        boolean shouldApplyGammaCorrection
    ) throws IOException {
        var image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        int maxFrequency = shouldApplyGammaCorrection ? getMaxFrequency(canvas) : 0;

        for (int y = 0; y < canvas.getHeight(); ++y) {
            for (int x = 0; x < canvas.getWidth(); ++x) {
                var pixel = canvas.getPixel(x, y);
                var color = pixel.getColor();
                if (shouldApplyGammaCorrection) {
                    double alpha = Math.log(pixel.getHitCount()) / Math.log(maxFrequency);
                    double correctionCoefficient = Math.pow(alpha, 1.0 / gamma);
                    color = new Color(
                        (int) (color.getRed() * correctionCoefficient),
                        (int) (color.getGreen() * correctionCoefficient),
                        (int) (color.getBlue() * correctionCoefficient)
                    );
                }
                image.setRGB(x, y, color.getRGB());
            }
        }

        return ImageIO.write(image, outputFormat.getFormatName(), file.toFile());
    }

    private static int getMaxFrequency(Canvas canvas) {
        int maxFrequency = -1;
        for (int y = 0; y < canvas.getHeight(); ++y) {
            for (int x = 0; x < canvas.getWidth(); ++x) {
                var pixel = canvas.getPixel(x, y);
                if (maxFrequency < pixel.getHitCount()) {
                    maxFrequency = pixel.getHitCount();
                }
            }
        }
        return maxFrequency;
    }

}
