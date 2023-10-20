package edu.hw2.Task2;

public class Rectangle {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("both sides of the rectangle must have a length of at least 1");
        }
        this.width = width;
        this.height = height;
    }

    public Rectangle setWidth(int newWidth) {
        return new Rectangle(newWidth, height);
    }

    public Rectangle setHeight(int newHeight) {
        return new Rectangle(width, newHeight);
    }

    public double area() {
        return width * height;
    }
}
