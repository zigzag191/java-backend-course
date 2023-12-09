package edu.project4.renderer;

import java.awt.Color;

public class HistogramEntry {

    private Color color = new Color(0, 0, 0);
    private int hitCount;

    public HistogramEntry(Color color, int hitCount) {
        this.color = color;
        this.hitCount = hitCount;
    }

    public HistogramEntry() {
    }

    public synchronized Color getColor() {
        return color;
    }

    public synchronized int getHitCount() {
        return hitCount;
    }

    public synchronized void hit(Color color) {
        ++hitCount;
        blend(color);
    }

    private void blend(Color other) {
        color = new Color(
            (color.getRed() + other.getRed()) / 2,
            (color.getGreen() + other.getGreen()) / 2,
            (color.getBlue() + other.getBlue()) / 2
        );
    }

}
