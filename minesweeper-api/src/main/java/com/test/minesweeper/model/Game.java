package com.test.minesweeper.model;

import com.test.minesweeper.exception.InvalidRequestException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Model class to represent a game
 */
public class Game {

    private String gameId;
    private List<List<Cell>> board;
    private int bombs;
    private int flags;
    private boolean won;
    private boolean over;

    private Game(List<List<Cell>> board, int bombs) {
        this.board = board;
        this.bombs = bombs;
    }

    /**
     * This method handles a user click.
     * If the cell is already visible or flagged, an invalid action exception will be thrown.
     * If the cell was hidden and contains a bomb, the game is set as over.
     * If the user clicked on a safe cell, the cell status will be set as visible,
     * and the surrounding cells will be revealed if required.
     */
    public void click(int row, int column) {
        Optional<Cell> cellOptional = getCellAt(row, column);
        if(cellOptional.isPresent()){
            Cell cell = cellOptional.get();
            if (Status.FLAGGED.equals(cell.getStatus())
                    || Status.VISIBLE.equals(cell.getStatus())) {
                throw new InvalidRequestException("Click action can only be performed on hidden cells");
            }
            if (cell.isBomb()) {
                this.setOver(true);
                return;
            }
            setVisibleAndIterateThroughAdjacentSafeCells(row, column);
        }else{
            throw new InvalidRequestException("Click action can only be performed within the game board limits");
        }
    }

    /**
     * This method handles a user flag action.
     * If the cell is already flagged, the flag status will be removed and set as hidden.
     * If the cell was hidden and the number of set flags is less than the number of bombs,
     * the cell status will be set as flagged.
     * Otherwise, an invalid action exception will be thrown.
     */
    public void flag(int row, int column) {
        Cell cell = board.get(row).get(column);
        if (Status.FLAGGED.equals(cell.getStatus())) {
            cell.setStatus(Status.HIDDEN);
        } else if (flags < bombs) {
            cell.setStatus(Status.FLAGGED);
        } else {
            throw new InvalidRequestException("No more flags to be assigned");
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
        Optional<Cell> cellOptional = getCellAt(row, column);
        if (cellOptional.isPresent()){
            Cell cell = cellOptional.get();
            if (Status.HIDDEN.equals(cell.getStatus()) && !cell.isBomb()) {
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

    public Optional<Cell> getCellAt(int row, int column){
        if(isWithinBoardLimits(row,column)){
            return Optional.of(board.get(row).get(column));
        }
        return Optional.empty();
    }

    private boolean isWithinBoardLimits(int row, int column) {
        return row >= 0 && row < board.size()
                && column >= 0 && column < board.get(0).size();
    }

    public boolean isWon() {
        return numberOfHiddenCellsEqualsNumberOfBombs()
                || allBombsAreFlagged();
    }

    private boolean numberOfHiddenCellsEqualsNumberOfBombs() {
        return board.stream()
                .flatMap(Collection::stream)
                .map(Cell::getStatus)
                .filter(Status.HIDDEN::equals)
                .collect(Collectors.toList())
                .size() == bombs;
    }

    private boolean allBombsAreFlagged() {
        return board.stream()
                .flatMap(Collection::stream)
                .filter(Cell::isBomb)
                .map(Cell::getStatus)
                .allMatch(Status.FLAGGED::equals);
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

    public List<List<Cell>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public static class GameBuilder {

        private List<List<Cell>> board;
        private int bombs;

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

        public GameBuilder setNumberOfBombs(int bombsToSet) {
            bombs = bombsToSet;
            while (bombsToSet > 0) {
                int row = getRandomIntBetweenZeroAnd(board.size() - 1);
                int column = getRandomIntBetweenZeroAnd(board.get(0).size() - 1);
                Cell cell = board.get(row).get(column);
                if (!cell.isBomb()) {
                    cell.setBomb(true);
                    increaseBombCountInAdjacentCells(row, column);
                    bombsToSet--;
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
            return new Game(board, bombs);
        }
    }

}
