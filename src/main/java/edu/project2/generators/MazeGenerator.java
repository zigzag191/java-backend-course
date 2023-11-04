package edu.project2.generators;

import edu.project2.maze.Maze;

public interface MazeGenerator {

    default Maze generate(int width, int height) {
        return generate(width, height, -1);
    }

    Maze generate(int width, int height, int seed);

}
