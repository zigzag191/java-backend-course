package edu.project4;

import edu.project4.fractals.FractalFunction;
import edu.project4.fractals.FractalGenerator;
import edu.project4.fractals.FunctionSet;
import edu.project4.fractals.variations.Variations;
import edu.project4.image.ImageFormat;
import edu.project4.image.ImageUtils;
import edu.project4.renderer.Point;
import edu.project4.renderer.Rectangle;
import edu.project4.renderer.Renderer;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public final class UsageExample {

    private UsageExample() {
    }

    @SuppressWarnings("MagicNumber")
    public static void example(Path outputDirectory) throws IOException {
        var viewport = new Rectangle(new Point(-1.0, 1.0), 2, 2);
        var renderer = new Renderer(viewport, 450, 5);

        var functions = Map.of(
            FractalFunction.builder()
                .addVariation(Variations.SWIRL, 0.4)
                .addVariation(Variations.SINUSOIDAL, 0.2)
                .addVariation(Variations.SPHERICAL, 0.3)
                .addVariation(Variations.LINEAR, 0.1)
                .setColor(Color.RED)
                .setCoefficients(1.481, 0.204, -0.110, 0.396, -0.731, 0.367)
                .build(),
            0.3,
            FractalFunction.builder()
                .addVariation(Variations.LINEAR, 0.4)
                .addVariation(Variations.SWIRL, 0.3)
                .addVariation(Variations.SINUSOIDAL, 0.2)
                .addVariation(Variations.JULIA, 0.4)
                .setColor(Color.GREEN)
                .setCoefficients(0.670, 0.872, 0.162, -0.554, 1.090, -0.234)
                .build(),
            0.45,
            FractalFunction.builder()
                .addVariation(Variations.SWIRL, 0.4)
                .addVariation(Variations.LINEAR, 0.4)
                .setColor(Color.BLUE)
                .setCoefficients(0.105, 1.178, -1.193, 0.262, -0.205, -0.621)
                .build(),
            0.25
        );

        var functionsSet = new FunctionSet(functions);
        var generator = new FractalGenerator(renderer, functionsSet, 100000, 5000, 12, 1);
        var canvas = generator.generate();

        ImageUtils.applyGammaCorrectionAndSave(canvas, outputDirectory.resolve("example.png"), ImageFormat.PNG, 1.1);
    }

}
