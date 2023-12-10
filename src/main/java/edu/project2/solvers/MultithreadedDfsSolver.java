package edu.project2.solvers;

import edu.project2.maze.Maze;
import edu.project2.maze.MazePosition;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class MultithreadedDfsSolver implements MazeSolver {
    @Override
    public List<MazePosition> solve(Maze maze, MazePosition start, MazePosition end) {
        try (var threadPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1)) {
            var result =
                threadPool.invoke(new Dfs(maze, start, end, ConcurrentHashMap.newKeySet(), new AtomicBoolean(true)));
            return new ArrayList<>(result);
        }
    }

    private final static class Dfs extends RecursiveTask<List<MazePosition>> {

        private final Maze maze;
        private final MazePosition current;
        private final MazePosition end;
        private final Set<MazePosition> visited;
        private final AtomicBoolean isRunning;

        private Dfs(
            Maze maze,
            MazePosition current,
            MazePosition end,
            Set<MazePosition> visited,
            AtomicBoolean isRunning
        ) {
            this.maze = maze;
            this.current = current;
            this.end = end;
            this.visited = visited;
            this.isRunning = isRunning;
        }

        @Override
        protected List<MazePosition> compute() {
            if (!isRunning.get() || visited.contains(current)) {
                return null;
            }
            visited.add(current);
            if (current.equals(end)) {
                var path = new LinkedList<MazePosition>();
                path.add(current);
                return path;
            }
            var tasks = new ArrayList<Dfs>();
            if (current.row() > 0 && maze.getCell(current).connectedUp()) {
                var task = new Dfs(maze, new MazePosition(current.row() - 1, current.col()), end, visited, isRunning);
                tasks.add(task);
            }
            if (current.col() < maze.getWidth() - 1 && maze.getCell(current).connectedRight()) {
                var task = new Dfs(maze, new MazePosition(current.row(), current.col() + 1), end, visited, isRunning);
                tasks.add(task);
            }
            if (current.row() < maze.getHeight() - 1 && maze.getCell(current).connectedDown()) {
                var task = new Dfs(maze, new MazePosition(current.row() + 1, current.col()), end, visited, isRunning);
                tasks.add(task);
            }
            if (current.col() > 0 && maze.getCell(current).connectedLeft()) {
                var task = new Dfs(maze, new MazePosition(current.row(), current.col() - 1), end, visited, isRunning);
                tasks.add(task);
            }
            var results = ForkJoinTask.invokeAll(tasks).stream().map(Dfs::join).toList();
            for (var result : results) {
                if (result != null) {
                    isRunning.set(false);
                    result.add(0, current);
                    return result;
                }
            }
            return null;
        }

    }

}
