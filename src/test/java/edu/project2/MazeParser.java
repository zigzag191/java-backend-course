package edu.project2;

import edu.project2.generators.ModifiableCell;
import edu.project2.generators.ModifiableMazeBase;
import edu.project2.maze.Cell;
import edu.project2.maze.Maze;

public final class MazeParser {

    private MazeParser() {
    }

    public static Maze parse(int[][] maze) {
        var parsedMaze = new ParsedMaze(maze[0].length, maze.length);
        for (int i = 1; i < maze.length; i += 2) {
            for (int j = 1; j < maze[0].length; j += 2) {
                if (maze[i - 1][j] == 0) {
                    parsedMaze.cells[(i - 1) / 2][(j - 1) / 2].setUp(true);
                }
                if (maze[i][j + 1] == 0) {
                    parsedMaze.cells[(i - 1) / 2][(j - 1) / 2].setRight(true);
                }
                if (maze[i + 1][j] == 0) {
                    parsedMaze.cells[(i - 1) / 2][(j - 1) / 2].setDown(true);
                }
                if (maze[i][j - 1] == 0) {
                    parsedMaze.cells[(i - 1) / 2][(j - 1) / 2].setLeft(true);
                }
            }
        }
        return parsedMaze;
    }

    private static final class ParsedMaze extends ModifiableMazeBase {

        ModifiableCell[][] cells;

        ParsedMaze(int width, int height) {
            cells = new ModifiableCell[height / 2][width / 2];
            for (int i = 0; i < height / 2; ++i) {
                for (int j = 0; j < width / 2; ++j) {
                    cells[i][j] = new ModifiableCell();
                }
            }
        }

        @Override
        protected Cell[][] getCells() {
            return cells;
        }

    }

}
