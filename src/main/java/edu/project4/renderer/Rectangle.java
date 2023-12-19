package edu.project4.renderer;

public record Rectangle(Point topLeft, int width, int height) {

    public boolean contains(Point p) {
        return p.x() >= topLeft().x() && p.x() <= topLeft().x() + width
            && p.y() >= topLeft().y() - height && p.y() <= topLeft().y();
    }

}
