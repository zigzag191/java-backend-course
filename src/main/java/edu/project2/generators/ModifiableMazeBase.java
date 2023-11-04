package edu.project2.generators;

import edu.project2.maze.Cell;
import edu.project2.maze.Maze;
import edu.project2.maze.MazePosition;

public abstract class ModifiableMazeBase implements Maze {

    protected abstract Cell[][] getCells();

    @Override
    public int getWidth() {
        return getCells()[0].length;
    }

    @Override
    public int getHeight() {
        return getCells().length;
    }

    @Override
    public Cell getCell(int row, int col) {
        return getCells()[row][col];
    }

    @Override
    public Cell getCell(MazePosition position) {
        return getCell(position.row(), position.col());
    }

}
