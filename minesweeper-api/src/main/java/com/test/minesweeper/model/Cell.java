package com.test.minesweeper.model;

/**
 * Model class for the board cells
 */
public class Cell {

    private Status status;
    private int adjacentBombs;
    private boolean bomb;

    public Cell() {
        status = Status.HIDDEN;
    }

    public int getAdjacentBombs() {
        return adjacentBombs;
    }

    public void setAdjacentBombs(int adjacentBombs) {
        this.adjacentBombs = adjacentBombs;
    }

    public void increaseAdjacentBombCountByOne() {
        adjacentBombs++;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isBomb() {
        return bomb;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }
}
