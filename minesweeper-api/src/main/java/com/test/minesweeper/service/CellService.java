package com.test.minesweeper.service;

import com.test.minesweeper.dto.ClickRequest;
import com.test.minesweeper.exception.InvalidRequestException;
import com.test.minesweeper.model.Game;
import com.test.minesweeper.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for the cell operations
 */
@Service
public class CellService {

    @Autowired
    private GameRepository gameRepository;

    private static final int NUMBER_OF_ROWS = 10;
    private static final int NUMBER_OF_COLUMNS = 10;
    private static final int NUMBER_OF_BOMBS = 10;

    public Game initializeGame() {
        return gameRepository.save(new Game.GameBuilder()
                .setBoardSize(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS)
                .setNumberOfBombs(NUMBER_OF_BOMBS)
                .build());
    }

    public Game clickCell(ClickRequest request) {
        Game game = gameRepository.findById(request.getGameId());
        if(game.isOver()){
            throw new InvalidRequestException("Game is over. No further actions are allowed");
        }
        if(game.isWon()){
            throw new InvalidRequestException("Game is already won. No further actions are allowed");
        }
        switch (request.getAction()){
            case FLAG:
                game.flag(request.getCell().getRow(), request.getCell().getColumn());
                break;
            case CLICK:
                game.click(request.getCell().getRow(), request.getCell().getColumn());
        }
        gameRepository.save(game);
        return game;
    }

    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
}
