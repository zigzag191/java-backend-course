package edu.project2;

import edu.project2.generators.MazeGenerator;
import edu.project2.generators.RecursiveBackTracker;
import edu.project2.maze.Maze;
import edu.project2.maze.MazePosition;
import edu.project2.solvers.MazeSolver;
import edu.project2.solvers.MultithreadedDfsSolver;
import java.util.Random;

public final class Main {

    public static final int MAZE_SIDE = 30;
    private static final Random RNG = new Random();

    private Main() {
    }

    public static void main(String[] args) {
        var generator = new RecursiveBackTracker();
        var solver = new MultithreadedDfsSolver();
        runMazeDemo(generator, solver);
    }

    private static void runMazeDemo(MazeGenerator generator, MazeSolver solver) {
        var maze = generator.generate(MAZE_SIDE, MAZE_SIDE);
        var solution = solver.solve(
            maze,
            getRandomPosition(maze),
            getRandomPosition(maze)
        );
        var printer = new MazePrinter(maze, solution);
        printer.printMaze();
    }

    private static MazePosition getRandomPosition(Maze maze) {
        return new MazePosition(RNG.nextInt(0, maze.getWidth()), RNG.nextInt(0, maze.getHeight()));
    }

}
