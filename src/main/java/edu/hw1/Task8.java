package edu.hw1;

import java.util.Objects;

public final class Task8 {

    private static final int BOARD_SIDE = 8;

    private static final int[][] KNIGHT_MOVES = {
        {1, -2},
        {2, -1},
        {2, 1},
        {1, 2},
        {-1, 2},
        {-2, 1},
        {-2, -1},
        {-1, -2},
    };

    public static boolean knightBoardCapture(int[][] board) {
        assureBoardIsValid(board);
        for (int y = 0; y < BOARD_SIDE; ++y) {
            for (int x = 0; x < BOARD_SIDE; ++x) {
                if (board[y][x] == 1 && canHit(board, x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean canHit(int[][] board, int x, int y) {
        for (int[] move : KNIGHT_MOVES) {
            int moveX = x + move[0];
            int moveY = y + move[1];
            if (positionIsOnBoard(moveX, moveY) && board[moveY][moveX] == 1) {
                return true;
            }
        }
        return false;
    }

    private static boolean positionIsOnBoard(int x, int y) {
        return x >= 0 && x < BOARD_SIDE && y >= 0 && y < BOARD_SIDE;
    }

    private static void assureBoardIsValid(int[][] board) {
        Objects.requireNonNull(board);
        boolean boardIsValid = board.length == BOARD_SIDE;
        if (boardIsValid) {
            for (int[] row : board) {
                if (row == null || row.length != BOARD_SIDE) {
                    boardIsValid = false;
                    break;
                }
            }
        }
        if (!boardIsValid) {
            throw new IllegalArgumentException("board should be 8x8 grid");
        }
    }

    private Task8() {
    }

}
