package com.test.minesweeper.dto;

/**
 * Dto class for cell click requests
 */
public class ClickRequest {

    private String gameId;
    private CellDto cellDto;
    private Action action;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public CellDto getCellDto() {
        return cellDto;
    }

    public void setCellDto(CellDto cellDto) {
        this.cellDto = cellDto;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
