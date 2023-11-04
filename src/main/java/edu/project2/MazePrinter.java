package edu.project2;

import edu.project2.maze.Maze;
import edu.project2.maze.MazePosition;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("RegexpSinglelineJava")
public class MazePrinter {

    private final Maze maze;
    private final List<MazePosition> path;
    private int turn = 0;
    private final MazePosition start;
    private final MazePosition end;

    public MazePrinter(Maze maze, List<MazePosition> path) {
        this.maze = maze;
        this.path = new ArrayList<>(path);
        this.start = path.get(0);
        this.end = path.get(path.size() - 1);
        this.path.sort(Comparator.comparing(MazePosition::row).thenComparing(MazePosition::col));
    }

    public void printMaze() {
        for (int i = 0; i < maze.getHeight(); ++i) {
            printRow(i);
        }
        printLastBorders();
    }

    private void printRow(int row) {
        printUpperBorders(row);
        printMiddleBorders(row);
    }

    private void printMiddleBorders(int row) {
        System.out.print(maze.getCell(row, 0).connectedLeft() ? ' ' : '|');
        for (int i = 0; i < maze.getWidth(); ++i) {
            if (turn < path.size() && path.get(turn).row() == row && path.get(turn).col() == i) {
                if (start.row() == row && start.col() == i) {
                    System.out.print('S');
                } else if (end.row() == row && end.col() == i) {
                    System.out.print('F');
                } else {
                    System.out.print('*');
                }
                ++turn;
            } else {
                System.out.print(' ');
            }
            System.out.print(maze.getCell(row, i).connectedRight() ? ' ' : '|');
        }
        System.out.println();
    }

    private void printUpperBorders(int row) {
        System.out.print('o');
        for (int i = 0; i < maze.getWidth(); ++i) {
            System.out.print(maze.getCell(row, i).connectedUp() ? ' ' : '-');
            System.out.print('o');
        }
        System.out.println();
    }

    private void printLastBorders() {
        System.out.print('o');
        for (int i = 0; i < maze.getWidth(); ++i) {
            System.out.print(maze.getCell(maze.getHeight() - 1, i).connectedDown() ? ' ' : '-');
            System.out.print('o');
        }
        System.out.println();
    }

}
