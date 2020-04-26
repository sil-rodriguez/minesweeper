package com.test.minesweeper;

import com.test.minesweeper.model.Cell;
import com.test.minesweeper.model.Game;
import com.test.minesweeper.model.Status;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the game cell click functionality
 */
public class GameClickTest {

    private static final int NUMBER_OF_ROWS = 3;
    private static final int NUMBER_OF_COLUMNS = 3;
    private static final int NUMBER_OF_BOMBS = 0;

    @Test
    public void shouldRevealSurroundingSafeCellsWhenCellWithoutAdjacentBombsClicked() {
        Game game = initializeGameCustomBoard();
        game.click(0, 2);
        List<List<Cell>> board = game.getBoard();
        assertEquals(board.get(0).get(0).getStatus(), Status.HIDDEN);
        assertEquals(board.get(0).get(1).getStatus(), Status.VISIBLE);
        assertEquals(board.get(0).get(2).getStatus(), Status.VISIBLE);

        assertEquals(board.get(1).get(0).getStatus(), Status.VISIBLE);
        assertEquals(board.get(1).get(1).getStatus(), Status.VISIBLE);
        assertEquals(board.get(1).get(2).getStatus(), Status.VISIBLE);

        assertEquals(board.get(2).get(0).getStatus(), Status.VISIBLE);
        assertEquals(board.get(2).get(1).getStatus(), Status.VISIBLE);
        assertEquals(board.get(2).get(2).getStatus(), Status.VISIBLE);
    }

    @Test
    public void shouldRevealOnlyTheClickedCellWhenCellWithAdjacentBombsClicked() {
        Game game = initializeGameCustomBoard();
        game.click(0, 1);
        List<List<Cell>> board = game.getBoard();
        assertEquals(board.get(0).get(0).getStatus(), Status.HIDDEN);
        assertEquals(board.get(0).get(1).getStatus(), Status.VISIBLE);
        assertEquals(board.get(0).get(2).getStatus(), Status.HIDDEN);

        assertEquals(board.get(1).get(0).getStatus(), Status.HIDDEN);
        assertEquals(board.get(1).get(1).getStatus(), Status.HIDDEN);
        assertEquals(board.get(1).get(2).getStatus(), Status.HIDDEN);

        assertEquals(board.get(2).get(0).getStatus(), Status.HIDDEN);
        assertEquals(board.get(2).get(1).getStatus(), Status.HIDDEN);
        assertEquals(board.get(2).get(2).getStatus(), Status.HIDDEN);
    }

    private Game initializeGameCustomBoard() {
        Game game = new Game.GameBuilder()
                .setBoardSize(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS)
                .setNumberOfBombs(NUMBER_OF_BOMBS)
                .build();
        List<List<Cell>> board = game.getBoard();
        Cell bombCell = new Cell();
        bombCell.setBomb(true);
        board.get(0).set(0, bombCell);
        board.get(0).get(1).setAdjacentBombs(1);
        board.get(1).get(0).setAdjacentBombs(1);
        board.get(1).get(1).setAdjacentBombs(1);
        return game;
    }

}
