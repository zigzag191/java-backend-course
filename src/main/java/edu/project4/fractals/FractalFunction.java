package edu.project4.fractals;

import edu.project4.fractals.variations.Variation;
import edu.project4.renderer.Point;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public final class FractalFunction {

    public static final int NUMBER_OF_COEFFICIENTS = 6;
    private final Map<Variation, Double> variations;
    private final Color color;
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;

    @SuppressWarnings("MagicNumber")
    private FractalFunction(Map<Variation, Double> variations, Color color, double... coefficients) {
        this.variations = variations;
        this.color = color;

        // MagicNumber на это жалуется
        this.a = coefficients[0];
        this.b = coefficients[1];
        this.c = coefficients[2];
        this.d = coefficients[3];
        this.e = coefficients[4];
        this.f = coefficients[5];
    }

    public static FractalFunctionBuilder builder() {
        return new FractalFunctionBuilder();
    }

    public TransformResult apply(Point p) {
        double x = 0;
        double y = 0;
        var transformed = new Point(a * p.x() + b * p.y() + c, d * p.x() + e * p.y() + f);
        for (var entry : variations.entrySet()) {
            double weight = entry.getValue();
            var next = entry.getKey().compute(transformed);
            x += next.x() * weight;
            y += next.y() * weight;
        }
        return new TransformResult(new Point(x, y), color);
    }

    public final static class FractalFunctionBuilder {

        private final Map<Variation, Double> variations = new HashMap<>();
        private Color color = Color.BLACK;
        private double a = 0.0;
        private double b = 0.0;
        private double c = 0.0;
        private double d = 0.0;
        private double e = 0.0;
        private double f = 0.0;

        private FractalFunctionBuilder() {
        }

        public FractalFunctionBuilder addVariation(Variation variation, double weight) {
            variations.put(variation, weight);
            return this;
        }

        public FractalFunctionBuilder setColor(Color color) {
            this.color = color;
            return this;
        }

        @SuppressWarnings("MagicNumber")
        public FractalFunctionBuilder setCoefficients(double... coefficients) {
            if (coefficients.length != NUMBER_OF_COEFFICIENTS) {
                throw new IllegalArgumentException("number of coefficients must be 6 (a, b, c, d, e, f)");
            }

            // и на это
            this.a = coefficients[0];
            this.b = coefficients[1];
            this.c = coefficients[2];
            this.d = coefficients[3];
            this.e = coefficients[4];
            this.f = coefficients[5];

            return this;
        }

        public FractalFunction build() {
            return new FractalFunction(variations, color, a, b, c, d, e, f);
        }

    }

}
