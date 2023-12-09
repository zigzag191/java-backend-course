package edu.project4.renderer;

import java.awt.Color;

public class Renderer {

    public final Rectangle viewport;
    public final Canvas canvas;
    public final int pixelsPerUnit;
    public final int pixelsPerSample;
    public final int scale;

    public Renderer(Rectangle viewport, int pixelsPerUnit, int pixelsPerSample) {
        this.viewport = viewport;
        this.pixelsPerUnit = pixelsPerUnit;
        this.pixelsPerSample = pixelsPerSample;
        this.scale = pixelsPerUnit * pixelsPerSample;
        canvas = new Canvas(
            (int) viewport.width() * scale,
            (int) viewport.height() * scale
        );
    }

    public Rectangle getViewport() {
        return viewport;
    }

    public void drawPoint(Point p, Color c) {
        if (!viewport.contains(p)) {
            return;
        }
        int x = (int) ((p.x() - viewport.topLeft().x()) * scale);
        int y = (int) ((viewport.topLeft().y() - p.y()) * scale);
        canvas.hitPixel(x, y, c);
    }

    public Canvas getMultisampledCanvas() {
        if (pixelsPerSample == 1) {
            return canvas;
        }

        var result = new Canvas(viewport.width() * pixelsPerUnit, viewport.height() * pixelsPerUnit);
        for (int y = 0; y < result.getHeight(); ++y) {
            for (int x = 0; x < result.getWidth(); ++x) {
                var entry = getCombinedEntry(x * pixelsPerSample, y * pixelsPerSample);
                result.setPixel(x, y, entry);
            }
        }

        return result;
    }

    private HistogramEntry getCombinedEntry(int x, int y) {
        int r = 0;
        int g = 0;
        int b = 0;
        int hits = 0;
        for (int i = 0; i < pixelsPerSample; ++i) {
            for (int j = 0; j < pixelsPerSample; ++j) {
                var pixel = canvas.getPixel(x + j, y + i);
                var color = pixel.getColor();
                r += color.getRed();
                g += color.getGreen();
                b += color.getBlue();
                hits += pixel.getHitCount();
            }
        }
        var color = new Color(
            r / (pixelsPerSample * pixelsPerSample),
            g / (pixelsPerSample * pixelsPerSample),
            b / (pixelsPerSample * pixelsPerSample)
        );
        return new HistogramEntry(color, hits);
    }

}
