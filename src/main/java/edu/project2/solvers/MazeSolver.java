package edu.project2.solvers;

import edu.project2.maze.Maze;
import edu.project2.maze.MazePosition;
import java.util.List;

public interface MazeSolver {

    List<MazePosition> solve(Maze maze, MazePosition start, MazePosition end);

}
