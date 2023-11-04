package edu.project2.generators;

import edu.project2.maze.Cell;
import edu.project2.maze.Maze;
import edu.project2.maze.MazePosition;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class RecursiveBackTracker implements MazeGenerator {

    private static List<NeighbourDirection> findNeighbours(Deque<MazePosition> stack, GeneratedMaze maze) {
        var top = stack.peek();
        var neighbours = new ArrayList<NeighbourDirection>();
        if (top.row() > 0 && !maze.cells[top.row() - 1][top.col()].isVisited) {
            neighbours.add(NeighbourDirection.UP);
        }
        if (top.col() < maze.getWidth() - 1 && !maze.cells[top.row()][top.col() + 1].isVisited) {
            neighbours.add(NeighbourDirection.RIGHT);
        }
        if (top.row() < maze.getHeight() - 1 && !maze.cells[top.row() + 1][top.col()].isVisited) {
            neighbours.add(NeighbourDirection.DOWN);
        }
        if (top.col() > 0 && !maze.cells[top.row()][top.col() - 1].isVisited) {
            neighbours.add(NeighbourDirection.LEFT);
        }
        return neighbours;
    }

    private static void addNextNeighbour(List<NeighbourDirection> neighbours,
        GeneratedMaze maze, Deque<MazePosition> stack, Random rng) {
        var top = stack.peek();
        var nextDir = neighbours.get(rng.nextInt(0, neighbours.size()));
        switch (nextDir) {
            case UP -> {
                maze.cells[top.row()][top.col()].up = true;
                maze.cells[top.row() - 1][top.col()].down = true;
                stack.push(new MazePosition(top.row() - 1, top.col()));
            }
            case RIGHT -> {
                maze.cells[top.row()][top.col()].right = true;
                maze.cells[top.row()][top.col() + 1].left = true;
                stack.push(new MazePosition(top.row(), top.col() + 1));
            }
            case DOWN -> {
                maze.cells[top.row()][top.col()].down = true;
                maze.cells[top.row() + 1][top.col()].up = true;
                stack.push(new MazePosition(top.row() + 1, top.col()));
            }
            case LEFT -> {
                maze.cells[top.row()][top.col()].left = true;
                maze.cells[top.row()][top.col() - 1].right = true;
                stack.push(new MazePosition(top.row(), top.col() - 1));
            }
            default -> throw new RuntimeException();
        }
    }

    @Override
    public Maze generate(int width, int height, int seed) {
        Random rng;
        if (seed < 0) {
            rng = new Random();
        } else {
            rng = new Random(seed);
        }

        var maze = new GeneratedMaze(width, height);

        var stack = new ArrayDeque<MazePosition>();
        stack.push(new MazePosition(0, 0));
        maze.cells[0][0].isVisited = true;
        int visitedCells = 1;

        while (visitedCells < maze.getWidth() * maze.getHeight()) {
            var neighbours = findNeighbours(stack, maze);
            if (!neighbours.isEmpty()) {
                addNextNeighbour(neighbours, maze, stack, rng);
                ++visitedCells;
                var newTop = stack.peek();
                maze.cells[newTop.row()][newTop.col()].isVisited = true;
            } else {
                stack.pop();
            }
        }

        return maze;
    }

    private enum NeighbourDirection {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    private static final class VisitableCell extends ModifiableCell {
        private boolean isVisited;
    }

    private static final class GeneratedMaze extends ModifiableMazeBase {
        private final VisitableCell[][] cells;

        private GeneratedMaze(int width, int height) {
            cells = new VisitableCell[height][width];
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    cells[i][j] = new VisitableCell();
                }
            }
        }

        @Override
        protected Cell[][] getCells() {
            return cells;
        }
    }

}
