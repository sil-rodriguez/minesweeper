package com.test.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private List<List<Cell>> board;

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
                if (!(cell instanceof Bomb)) {
                    board.get(row).set(column, new Bomb());
                    increaseBombCountInAdjacentCells(row, column);
                    bombs--;
                }
            }
            return this;
        }

        private int getRandomIntBetweenZeroAnd(int max) {
            return ThreadLocalRandom.current().nextInt(0, max + 1);
        }

        private void increaseBombCountInAdjacentCells(int row, int column) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    if (isWithinBoardLimits(i, j)) {
                        Cell cell = board.get(i).get(j);
                        if (cell instanceof SafeCell) {
                            ((SafeCell)cell).increaseAdjacentBombsBy(1);
                        }
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
