package edu.project4.renderer;

import java.awt.Color;

public class Canvas {

    private final HistogramEntry[][] pixels;
    private final int width;
    private final int height;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new HistogramEntry[height][];
        for (int i = 0; i < height; ++i) {
            pixels[i] = new HistogramEntry[width];
        }
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                pixels[y][x] = new HistogramEntry();
            }
        }
    }

    public HistogramEntry getPixel(int x, int y) {
        return pixels[y][x];
    }

    public void hitPixel(int x, int y, Color color) {
        pixels[y][x].hit(color);
    }

    public void setPixel(int x, int y, HistogramEntry entry) {
        pixels[y][x] = entry;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
