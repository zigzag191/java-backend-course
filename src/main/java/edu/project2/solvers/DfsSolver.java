package edu.project2.solvers;

import edu.project2.maze.Maze;
import edu.project2.maze.MazePosition;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DfsSolver implements MazeSolver {

    @Override
    public List<MazePosition> solve(Maze maze, MazePosition start, MazePosition end) {
        var solution = new ArrayList<MazePosition>();
        var stack = new ArrayDeque<MazePosition>();
        var visited = new HashSet<MazePosition>();
        stack.push(start);
        if (!dfs(maze, solution, stack, end, visited)) {
            return null;
        }
        for (int l = 0, r = solution.size() - 1; l < r; ++l, --r) {
            var tmp = solution.get(l);
            solution.set(l, solution.get(r));
            solution.set(r, tmp);
        }
        return solution;
    }

    @SuppressWarnings("ReturnCount")
    private boolean dfs(Maze maze, List<MazePosition> solution,
        Deque<MazePosition> stack,
        MazePosition end,
        Set<MazePosition> visited) {
        var current = stack.peek();
        if (visited.contains(current)) {
            return false;
        }
        visited.add(current);
        if (current.equals(end)) {
            solution.add(current);
            return true;
        }
        if (current.row() > 0 && maze.getCell(current).connectedUp()) {
            stack.push(new MazePosition(current.row() - 1, current.col()));
            if (dfs(maze, solution, stack, end, visited)) {
                solution.add(current);
                return true;
            }
            stack.pop();
        }
        if (current.col() < maze.getWidth() - 1 && maze.getCell(current).connectedRight()) {
            stack.push(new MazePosition(current.row(), current.col() + 1));
            if (dfs(maze, solution, stack, end, visited)) {
                solution.add(current);
                return true;
            }
            stack.pop();
        }
        if (current.row() < maze.getHeight() - 1 && maze.getCell(current).connectedDown()) {
            stack.push(new MazePosition(current.row() + 1, current.col()));
            if (dfs(maze, solution, stack, end, visited)) {
                solution.add(current);
                return true;
            }
            stack.pop();
        }
        if (current.col() > 0 && maze.getCell(current).connectedLeft()) {
            stack.push(new MazePosition(current.row(), current.col() - 1));
            if (dfs(maze, solution, stack, end, visited)) {
                solution.add(current);
                return true;
            }
            stack.pop();
        }
        return false;
    }

}
