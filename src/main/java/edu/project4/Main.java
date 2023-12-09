package edu.project4;

import edu.project4.fractals.FractalFunction;
import edu.project4.fractals.FractalGenerator;
import edu.project4.fractals.FunctionSet;
import edu.project4.fractals.variations.Variations;
import edu.project4.renderer.Point;
import edu.project4.renderer.Rectangle;
import edu.project4.renderer.Renderer;
import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Random RNG = new Random();
    public static final int MAX_COLOR_VALUE = 256;
    public static final Rectangle TEST_VIEWPORT = new Rectangle(new Point(-0.5, 0.5), 1, 1);
    public static final int TEST_PIXELS_PER_UNIT = 900;
    public static final int TEST_PIXELS_PER_SAMPLE = 4;
    public static final int TEST_ITERATIONS = 50000;
    public static final int TEST_SAMPLES = 500;
    public static final int TEST_SYMMETRY_ROTATIONS = 3;
    public static final int NUMBER_OF_TESTS = 10;
    public static final double NANOSECONDS_IN_SECOND = 1e9;

    private Main() {
    }

    private static Color getRandomColor() {
        return new Color(
            RNG.nextInt(0, MAX_COLOR_VALUE),
            RNG.nextInt(0, MAX_COLOR_VALUE),
            RNG.nextInt(0, MAX_COLOR_VALUE)
        );
    }

    private static void runTest(FunctionSet set, int numberOfThreads, String message) {
        var renderer = new Renderer(TEST_VIEWPORT, TEST_PIXELS_PER_UNIT, TEST_PIXELS_PER_SAMPLE);
        var generator = new FractalGenerator(renderer, set,
            TEST_ITERATIONS, TEST_SAMPLES, numberOfThreads, TEST_SYMMETRY_ROTATIONS
        );
        long start = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_TESTS; ++i) {
            generator.generate();
        }
        long time = System.nanoTime() - start;
        LOGGER.info(message + ": " + (time / NUMBER_OF_TESTS / NANOSECONDS_IN_SECOND) + " seconds");
    }

    @SuppressWarnings("MagicNumber")
    public static void main(String[] args) throws IOException {
        var functions = new HashMap<FractalFunction, Double>();

        functions.put(
            FractalFunction.builder()
                .addVariation(Variations.JULIA, 0.3)
                .addVariation(Variations.SWIRL, 0.4)
                .addVariation(Variations.SPHERICAL, 0.2)
                .addVariation(Variations.HORSESHOE, 0.1)
                .setCoefficients(1.353, -0.550, -0.1, 0.021, -1.309, 0.3)
                .setColor(getRandomColor())
                .build(),
            0.2
        );

        functions.put(
            FractalFunction.builder()
                .addVariation(Variations.HEART, 0.4)
                .addVariation(Variations.DISC, 0.3)
                .addVariation(Variations.SWIRL, 0.2)
                .addVariation(Variations.LINEAR, 0.1)
                .setCoefficients(0.836, -0.232, 0.121, -1.470, -1.236, 0.6)
                .setColor(getRandomColor())
                .build(),
            0.5
        );

        functions.put(
            FractalFunction.builder()
                .addVariation(Variations.POLAR, 0.1)
                .addVariation(Variations.HANDKERCHIEF, 0.4)
                .addVariation(Variations.SINUSOIDAL, 0.1)
                .setCoefficients(1.485, 0.645, -0.1, -0.004, 0.877, 0.5)
                .setColor(getRandomColor())
                .build(),
            0.3
        );

        var functionSet = new FunctionSet(functions);

        runTest(functionSet, 1, "Single thread");
        runTest(functionSet, 2, "Two threads");
        runTest(functionSet, 12, "12 threads");
    }

}
