package com.test.minesweeper.dto;

/**
 * Dto class to represent a board cell
 */
public class CellDto {

    private int column;
    private int row;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
