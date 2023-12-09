package edu.project4.fractals.variations;

import edu.project4.renderer.Point;

public final class Variations {

    public static final Variation LINEAR = (x, y, r, theta, phi) -> new Point(x, y);
    public static final Variation SINUSOIDAL = (x, y, r, theta, phi) -> new Point(Math.sin(x), Math.sin(y));
    public static final Variation SPHERICAL = (x, y, r, theta, phi) -> {
        double inverse = 1.0 / (r * r);
        return new Point(inverse * x, inverse * y);
    };
    public static final Variation SWIRL = (x, y, r, theta, phi) -> {
        double sinR = Math.sin(r * r);
        double cosR = Math.cos(r * r);
        return new Point(x * sinR - y * cosR, x * cosR + y * sinR);
    };
    public static final Variation HORSESHOE = (x, y, r, theta, phi) -> {
        double inverse = 1.0 / r;
        return new Point(inverse * (x - y) * (x + y), 2 * x * y);
    };
    public static final Variation POLAR = (x, y, r, theta, phi) -> new Point(theta / Math.PI, r - 1);
    public static final Variation HANDKERCHIEF =
        (x, y, r, theta, phi) -> new Point(r * Math.sin(theta + r), r * Math.cos(theta - r));
    public static final Variation HEART =
        (x, y, r, theta, phi) -> new Point(r * Math.sin(r * theta), r * -Math.cos(r * theta));
    public static final Variation DISC = (x, y, r, theta, phi) -> {
        double scale = theta / Math.PI;
        return new Point(scale * Math.sin(Math.PI * r), scale * Math.cos(Math.PI * r));
    };
    public static final double JULIA_OMEGA = 5.0;
    public static final Variation JULIA = (x, y, r, theta, phi) -> {
        double sqrt = Math.sqrt(r);
        return new Point(sqrt * Math.cos((theta / 2) + JULIA_OMEGA), sqrt * Math.sin((theta / 2) + JULIA_OMEGA));
    };

    private Variations() {
    }

}
