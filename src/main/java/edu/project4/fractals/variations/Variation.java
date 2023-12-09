package edu.project4.fractals.variations;

import edu.project4.renderer.Point;

public interface Variation {

    default Point compute(Point p) {
        double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
        double theta = Math.atan(p.x() / p.y());
        double phi = Math.atan(p.y() / p.x());
        return computeImpl(p.x(), p.y(), r, theta, phi);
    }

    Point computeImpl(double x, double y, double r, double theta, double phi);

}
