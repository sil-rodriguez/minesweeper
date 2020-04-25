package com.test.minesweeper.model;

public class SafeCell extends Cell {

    private int adjacentBombs;

    public int getAdjacentBombs() {
        return adjacentBombs;
    }

    public void setAdjacentBombs(int adjacentBombs) {
        this.adjacentBombs = adjacentBombs;
    }

    public void increaseAdjacentBombsBy(int quantity){
        adjacentBombs++;
    }
}