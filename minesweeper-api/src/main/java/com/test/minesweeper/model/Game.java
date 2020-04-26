package com.test.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Model class to represent a game
 */
public class Game {

    private String gameId;
    private List<List<Cell>> board;
    private boolean over;

    /**
     * This method handles a user click.
     * If the cell is already visible or flagged, no action is performed.
     * If the cell was hidden and contains a bomb, the game is set as over.
     * If the user clicked on a safe cell, the cell status will be set as visible,
     * and the surrounding cells will be revealed if required.
     */
    public void click(int row, int column) {
        Cell cell = board.get(row).get(column);
        if (Status.HIDDEN.equals(cell.getStatus())) {
            if (cell.isBomb()) {
                this.setOver(true);
                return;
            }
            setVisibleAndIterateThroughAdjacentSafeCells(row, column);
        }
    }

    /**
     * This method sets a cell as visible and iterates through its surrounding cells.
     * If the coordinates are within the board limits,
     * and the cell is hidden and is not a bomb, then it is set a visible.
     * If in addition to that, the numberOfAdjacentBombs equals zero,
     * the process is repeated recursively on its surrounding cells.
     */
    private void setVisibleAndIterateThroughAdjacentSafeCells(int row, int column) {
        if(isWithinBoardLimits(row, column)){
            Cell cell = board.get(row).get(column);
            if (Status.HIDDEN.equals(cell.getStatus()) && !cell.isBomb()){
                cell.setStatus(Status.VISIBLE);
                if (cell.getAdjacentBombs() == 0) {
                    for (int i = row - 1; i <= row + 1; i++) {
                        for (int j = column - 1; j <= column + 1; j++) {
                            setVisibleAndIterateThroughAdjacentSafeCells(i, j);
                        }
                    }
                }
            }
        }
    }

    private boolean isWithinBoardLimits(int row, int column) {
        return row >= 0 && row < board.size()
                && column >= 0 && column < board.get(0).size();
    }


    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    private Game(List<List<Cell>> board) {
        this.board = board;
    }

    public List<List<Cell>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }

    public static class GameBuilder {

        private List<List<Cell>> board;

        public GameBuilder setBoardSize(int rows, int columns) {
            board = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                List<Cell> row = new ArrayList<>();
                for (int j = 0; j < columns; j++) {
                    row.add(new Cell());
                }
                board.add(row);
            }
            return this;
        }

        public GameBuilder setNumberOfBombs(int bombs) {
            while (bombs > 0) {
                int row = getRandomIntBetweenZeroAnd(board.size() - 1);
                int column = getRandomIntBetweenZeroAnd(board.get(0).size() - 1);
                Cell cell = board.get(row).get(column);
                if (!cell.isBomb()) {
                    cell.setBomb(true);
                    increaseBombCountInAdjacentCells(row, column);
                    bombs--;
                }
            }
            return this;
        }

        private int getRandomIntBetweenZeroAnd(int max) {
            return ThreadLocalRandom.current().nextInt(0, max + 1);
        }

        /**
         * This method iterates through the surrounding cells of a bomb,
         * checks if the coordinates are within the board limits,
         * and increases the neighbour cell bomb count by one.
         */
        private void increaseBombCountInAdjacentCells(int row, int column) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    if (isWithinBoardLimits(i, j)) {
                        board.get(i).get(j).increaseAdjacentBombCountByOne();
                    }
                }
            }
        }

        private boolean isWithinBoardLimits(int row, int column) {
            return row >= 0 && row < board.size()
                    && column >= 0 && column < board.get(0).size();
        }

        public Game build() {
            return new Game(board);
        }
    }
}
