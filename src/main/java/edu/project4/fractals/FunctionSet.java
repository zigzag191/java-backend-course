package edu.project4.fractals;

import edu.project4.renderer.Point;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class FunctionSet {

    private final Map<FractalFunction, Double> functions;

    public FunctionSet(Map<FractalFunction, Double> functions) {
        this.functions = functions;
    }

    public TransformResult applyTransform(Point p) {
        var f = getWeightedRandomFunction();
        return f.apply(p);
    }

    private FractalFunction getWeightedRandomFunction() {
        double p = ThreadLocalRandom.current().nextDouble();
        double cumulativeProbability = 0.0;
        for (var entry : functions.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (p <= cumulativeProbability) {
                return entry.getKey();
            }
        }
        return functions.entrySet().stream().findAny().orElseThrow().getKey();
    }

}
