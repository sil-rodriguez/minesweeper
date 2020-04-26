package com.test.minesweeper;

import com.test.minesweeper.model.Cell;
import com.test.minesweeper.model.Game;
import com.test.minesweeper.model.Status;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for determining if a game is won
 */
public class GameIsWonTest {

    private static final int NUMBER_OF_ROWS = 10;
    private static final int NUMBER_OF_COLUMNS = 10;
    private static final int NUMBER_OF_BOMBS = 10;

    @Test
    public void gameShouldBeWonWhenAllBombsAreFlagged(){
        Game game = new Game.GameBuilder()
                .setBoardSize(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS)
                .setNumberOfBombs(NUMBER_OF_BOMBS)
                .build();
        flagAllGameBombs(game);
        assertTrue(game.isWon());
    }

    private void flagAllGameBombs(Game game){
        game.getBoard().stream()
                .flatMap(Collection::stream)
                .filter(Cell::isBomb)
                .forEach(cell -> cell.setStatus(Status.FLAGGED));
    }

    @Test
    public void gameShouldBeWonWhenHiddenCellsEqualsNumberOfBombs(){
        Game game = new Game.GameBuilder()
                .setBoardSize(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS)
                .setNumberOfBombs(NUMBER_OF_BOMBS)
                .build();
        setAllSafeCellsVisible(game);
        assertTrue(game.isWon());
    }

    private void setAllSafeCellsVisible(Game game){
        game.getBoard().stream()
                .flatMap(Collection::stream)
                .filter(cell -> !cell.isBomb())
                .forEach(cell -> cell.setStatus(Status.VISIBLE));
    }


    @Test
    public void gameShouldNotBeWonWhenBombsNotFlaggedAndHiddenCellsNotEqualToBombs(){
        Game game = new Game.GameBuilder()
                .setBoardSize(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS)
                .setNumberOfBombs(NUMBER_OF_BOMBS)
                .build();
        assertFalse(game.isWon());
    }

}
