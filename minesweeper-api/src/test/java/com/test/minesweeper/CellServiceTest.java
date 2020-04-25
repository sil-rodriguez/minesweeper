package com.test.minesweeper;

import com.test.minesweeper.model.Cell;
import com.test.minesweeper.model.Game;
import com.test.minesweeper.repository.GameRepository;
import com.test.minesweeper.service.CellService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the cell service
 */
public class CellServiceTest {

    @Test
    public void shouldReturnValidGameBoard() {
        CellService service = new CellService();
        service.setGameRepository(new GameRepository());
        Game newGame = service.initializeGame();
        List<List<Cell>> board = newGame.getBoard();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                assertEquals(board.get(i).get(j).getAdjacentBombs(), getNumberOfAdjacentBombs(board, i, j));
            }
        }
    }

    private int getNumberOfAdjacentBombs(List<List<Cell>> board, int row, int column) {
        int bombCount = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (isWithinBoardLimits(board, i, j)
                        && board.get(i).get(j).isBomb()) {
                    bombCount++;
                }
            }
        }
        return bombCount;
    }

    private boolean isWithinBoardLimits(List<List<Cell>> board, int row, int column) {
        return row >= 0 && row < board.size()
                && column >= 0 && column < board.get(0).size();
    }
}
