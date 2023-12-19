package edu.project4.fractals;

import edu.project4.renderer.Canvas;
import edu.project4.renderer.Point;
import edu.project4.renderer.Renderer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class FractalGenerator {

    public static final int SKIPPED = 20;
    public static final double DEGREES_IN_CIRCLE = 360.0;
    private final Renderer renderer;
    private final FunctionSet functionSet;
    public final int iterations;
    public final int samples;
    public final int numberOfThreads;
    public final int samplesPerThread;
    public final int symmetryRotations;
    public final double rotation;

    public FractalGenerator(
        Renderer renderer, FunctionSet functionSet, int iterations, int samples,
        int numberOfThreads,
        int symmetryRotations
    ) {
        this.iterations = iterations;
        this.samples = samples;
        this.renderer = renderer;
        this.functionSet = functionSet;
        this.numberOfThreads = numberOfThreads;
        this.symmetryRotations = symmetryRotations;
        this.samplesPerThread = Math.ceilDiv(samples, numberOfThreads);
        this.rotation = DEGREES_IN_CIRCLE / symmetryRotations;
    }

    public Canvas generate() {
        try (var service = Executors.newFixedThreadPool(numberOfThreads)) {
            for (int i = 0; i < numberOfThreads; ++i) {
                service.submit(this::generatePartialImage);
            }
        }
        return renderer.getMultisampledCanvas();
    }

    private void generatePartialImage() {
        for (int i = 0; i < samplesPerThread; ++i) {
            var point = getRandomPoint();
            for (int j = -SKIPPED; j < iterations; ++j) {
                var transform = functionSet.applyTransform(point);
                point = transform.next();
                if (j >= 0) {
                    var current = point;
                    for (int k = 0; k < symmetryRotations; ++k) {
                        renderer.drawPoint(current, transform.color());
                        current = rotate(current, rotation);
                    }
                }
            }
        }
    }

    private Point rotate(Point p, double degrees) {
        double radians = Math.toRadians(degrees);
        double sin = Math.sin(radians);
        double cos = Math.cos(radians);
        return new Point(
            p.x() * cos - p.y() * sin,
            p.x() * sin + p.y() * cos
        );
    }

    private Point getRandomPoint() {
        var rng = ThreadLocalRandom.current();
        var viewport = renderer.getViewport();
        return new Point(
            rng.nextDouble(viewport.topLeft().x(), viewport.topLeft().x() + viewport.width()),
            rng.nextDouble(viewport.topLeft().y() - viewport.height(), viewport.topLeft().y())
        );
    }

}
