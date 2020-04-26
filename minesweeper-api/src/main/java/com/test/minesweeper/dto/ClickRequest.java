package com.test.minesweeper.dto;

/**
 * Dto class for cell click requests
 */
public class ClickRequest {

    private String gameId;
    private CellDto cell;
    private Action action;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public CellDto getCell() {
        return cell;
    }

    public void setCell(CellDto cell) {
        this.cell = cell;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
