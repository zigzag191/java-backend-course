package edu.project2;

import edu.project2.generators.MazeGenerator;
import edu.project2.generators.RecursiveBackTracker;
import edu.project2.maze.Maze;
import edu.project2.maze.MazePosition;
import edu.project2.solvers.DfsSolver;
import edu.project2.solvers.DijkstraSolver;
import edu.project2.solvers.MazeSolver;
import edu.project2.solvers.MultithreadedDfsSolver;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
public class MazeTest {

    Maze maze = MazeParser.parse(new int[][] {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    });

    MazePosition start = new MazePosition(0, 0);
    MazePosition end = new MazePosition(4, 4);

    List<MazePosition> path = List.of(
        new MazePosition(0, 0),
        new MazePosition(1, 0),
        new MazePosition(2, 0),
        new MazePosition(2, 1),
        new MazePosition(2, 2),
        new MazePosition(1, 2),
        new MazePosition(0, 2),
        new MazePosition(0, 3),
        new MazePosition(0, 4),
        new MazePosition(1, 4),
        new MazePosition(2, 4),
        new MazePosition(3, 4),
        new MazePosition(4, 4)
    );

    String expectedPrint = "o-o-o-o-o-o" + System.lineSeparator()
        + "|S|  * * *|" + System.lineSeparator()
        + "o o-o o-o o" + System.lineSeparator()
        + "|*|  *|  *|" + System.lineSeparator()
        + "o o-o o o o" + System.lineSeparator()
        + "|* * *| |*|" + System.lineSeparator()
        + "o o-o o o o" + System.lineSeparator()
        + "| | | | |*|" + System.lineSeparator()
        + "o o o-o o o" + System.lineSeparator()
        + "| |     |F|" + System.lineSeparator()
        + "o-o-o-o-o-o" + System.lineSeparator();

    static Stream<MazeSolver> solversShouldProduceExpectedPath() {
        return Stream.of(
            new DfsSolver(),
            new DijkstraSolver(),
            new MultithreadedDfsSolver()
        );
    }

    public static Stream<MazeGenerator> generatorShouldProduceSolvableMazes() {
        return Stream.of(
            new RecursiveBackTracker()
        );
    }

    @Test
    void mazeShouldBePrintedCorrectly() {
        var outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        new MazePrinter(maze, path).printMaze();
        var result = outContent.toString();
        assertThat(result).isEqualTo(expectedPrint);
    }

    @ParameterizedTest
    @MethodSource
    void solversShouldProduceExpectedPath(MazeSolver solver) {
        var solution = solver.solve(maze, start, end);
        assertThat(solution).containsExactlyElementsOf(path);
    }

    @ParameterizedTest
    @MethodSource
    void generatorShouldProduceSolvableMazes(MazeGenerator generator) {
        var generatedMaze = generator.generate(50, 50, 1);
        var solver = new DijkstraSolver();
        var solution = solver.solve(generatedMaze, new MazePosition(0, 0), new MazePosition(49, 49));
        assertThat(solution).isNotNull();
    }

}
