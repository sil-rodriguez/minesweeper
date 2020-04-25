package com.test.minesweeper.service;

import com.test.minesweeper.model.Game;
import org.springframework.stereotype.Service;

@Service
public class CellService {

    private static final int NUMBER_OF_ROWS = 8;
    private static final int NUMBER_OF_COLUMNS = 8;
    private static final int NUMBER_OF_BOMBS = 10;

    public Game initializeGame() {
        return new Game.GameBuilder()
                .setBoardSize(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS)
                .setNumberOfBombs(NUMBER_OF_BOMBS)
                .build();
    }
}
