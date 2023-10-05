package hw1;

import java.util.Objects;

public class Task8 {

    public static boolean knightBoardCapture(int[][] board) {
        assureValidBoard(board);
        for (int y = 0; y < board.length; ++y) {
            for (int x = 0; x < board[0].length; ++x) {
                if (board[y][x] == 1 && canHit(board, x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void assureValidBoard(int[][] board) {
        Objects.requireNonNull(board);
        boolean boardIsValid = board.length == 8;
        if (boardIsValid) {
            for (int[] row : board) {
                if (row.length != 8) {
                    boardIsValid = false;
                    break;
                }
            }
        }
        if (!boardIsValid) {
            throw new IllegalArgumentException("board should be 8x8 grid");
        }
    }

    private static boolean canHit(int[][] board, int x, int y) {
        return true;
    }

    private Task8() {
    }

}
