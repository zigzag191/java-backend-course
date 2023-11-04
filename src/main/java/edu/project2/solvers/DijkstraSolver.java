package edu.project2.solvers;

import edu.project2.maze.Maze;
import edu.project2.maze.MazePosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraSolver implements MazeSolver {

    @Override
    public List<MazePosition> solve(Maze maze, MazePosition start, MazePosition end) {
        var queue = new PriorityQueue<>(Comparator.comparing(Node::getDistance));
        var nodes = initNodes(maze, start, queue);

        while (!queue.isEmpty()) {
            var next = queue.poll();
            if (next.position == end) {
                break;
            }
            var neighbours = getNeighbours(maze, nodes, next);
            for (var neighbour : neighbours) {
                if (neighbour.distance > next.distance + 1 && !neighbour.isVisited) {
                    neighbour.distance = next.distance + 1;
                    neighbour.previous = next;
                    queue.add(neighbour);
                }
            }
            next.isVisited = true;
        }

        return buildPath(nodes, end);
    }

    private static List<MazePosition> buildPath(Node[][] nodes, MazePosition end) {
        var result = new ArrayList<MazePosition>();
        var current = nodes[end.row()][end.col()];
        if (current.previous == null) {
            return null;
        }
        while (current != null) {
            result.add(current.position);
            current = current.previous;
        }
        for (int l = 0, r = result.size() - 1; l < r; ++l, --r) {
            var tmp = result.get(l);
            result.set(l, result.get(r));
            result.set(r, tmp);
        }
        return result;
    }

    private static Node[][] initNodes(Maze maze, MazePosition start, PriorityQueue<Node> queue) {
        Node[][] nodes = new Node[maze.getHeight()][maze.getWidth()];
        for (int i = 0; i < maze.getHeight(); ++i) {
            for (int j = 0; j < maze.getWidth(); ++j) {
                nodes[i][j] = new Node();
                if (start.row() == i && start.col() == j) {
                    nodes[i][j].distance = 0;
                } else {
                    nodes[i][j].distance = Integer.MAX_VALUE;
                }
                nodes[i][j].position = new MazePosition(i, j);
            }
        }
        queue.add(nodes[start.row()][start.col()]);
        return nodes;
    }

    private static Collection<Node> getNeighbours(Maze maze, Node[][] nodes, Node node) {
        var result = new ArrayList<Node>();
        var pos = node.position;
        if (pos.row() > 0 && maze.getCell(pos.row(), pos.col()).connectedUp()) {
            result.add(nodes[pos.row() - 1][pos.col()]);
        }
        if (pos.col() < nodes[0].length - 1 && maze.getCell(pos.row(), pos.col()).connectedRight()) {
            result.add(nodes[pos.row()][pos.col() + 1]);
        }
        if (pos.row() < nodes.length - 1 && maze.getCell(pos.row(), pos.col()).connectedDown()) {
            result.add(nodes[pos.row() + 1][pos.col()]);
        }
        if (pos.col() > 0 && maze.getCell(pos.row(), pos.col()).connectedLeft()) {
            result.add(nodes[pos.row()][pos.col() - 1]);
        }
        return result;
    }

    private static final class Node {
        boolean isVisited;
        int distance;
        Node previous;
        MazePosition position;

        int getDistance() {
            return distance;
        }
    }

}
