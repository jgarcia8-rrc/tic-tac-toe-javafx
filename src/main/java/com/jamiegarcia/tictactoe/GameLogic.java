package com.jamiegarcia.tictactoe;

/**
 * Holds the Tic Tac Toe board state and the rules for detecting
 * a winner or a draw. This class knows nothing about JavaFX or
 * the UI - it's pure game logic, which makes it easy to read,
 * test, and reuse independently of how the board is displayed.
 */
public class GameLogic {

    public static final int BOARD_SIZE = 3;
    public static final String CAT = "CAT";
    public static final String DOG = "DOG";

    // null = empty cell, otherwise CAT or DOG
    private final String[][] board = new String[BOARD_SIZE][BOARD_SIZE];

    /**
     * Places the given player's mark at the specified cell.
     * Assumes the caller has already checked the cell is empty
     * and the game isn't over - this class trusts its inputs
     * to keep the logic simple.
     */
    public void placeMark(int row, int col, String player) {
        board[row][col] = player;
    }

    /**
     * Returns true if the given cell is empty and available to play.
     */
    public boolean isCellEmpty(int row, int col) {
        return board[row][col] == null;
    }

    /**
     * Checks whether the given player has won by completing any
     * row, column, or diagonal.
     */
    public boolean checkWinner(String player) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (player.equals(board[row][0])
                    && player.equals(board[row][1])
                    && player.equals(board[row][2])) {
                return true;
            }
        }

        for (int col = 0; col < BOARD_SIZE; col++) {
            if (player.equals(board[0][col])
                    && player.equals(board[1][col])
                    && player.equals(board[2][col])) {
                return true;
            }
        }

        if (player.equals(board[0][0])
                && player.equals(board[1][1])
                && player.equals(board[2][2])) {
            return true;
        }

        if (player.equals(board[0][2])
                && player.equals(board[1][1])
                && player.equals(board[2][0])) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if every cell on the board has been filled.
     * Combined with checkWinner() returning false, this means a draw.
     */
    public boolean isBoardFull() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Clears the board back to an empty state.
     */
    public void reset() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = null;
            }
        }
    }
}