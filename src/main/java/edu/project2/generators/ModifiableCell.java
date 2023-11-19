package edu.project2.generators;

import edu.project2.maze.Cell;

public class ModifiableCell implements Cell {

    protected boolean up;
    protected boolean right;
    protected boolean down;
    protected boolean left;

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    @Override
    public boolean connectedUp() {
        return up;
    }

    @Override
    public boolean connectedRight() {
        return right;
    }

    @Override
    public boolean connectedDown() {
        return down;
    }

    @Override
    public boolean connectedLeft() {
        return left;
    }

}
