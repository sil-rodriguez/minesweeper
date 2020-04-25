package com.test.minesweeper.dto;

public class GameDto {

    private boolean isOver;
    private boolean isWon;

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }
}
