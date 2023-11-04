package edu.project2.maze;

public interface Maze {

    int getWidth();

    int getHeight();

    Cell getCell(int row, int col);

    Cell getCell(MazePosition position);

}
