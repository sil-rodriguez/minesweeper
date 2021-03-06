package com.test.minesweeper.repository;

import com.test.minesweeper.exception.GameNotFoundException;
import com.test.minesweeper.model.Game;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Repository for Game persistence and retrieval
 */
@Repository
public class GameRepository {

    Map<String, Game> gameMap = new HashMap<>();

    public Game save(Game game){
        String id = game.getGameId();
        if(id == null){
            id = UUID.randomUUID().toString();
            game.setGameId(id);
        }
        gameMap.put(id,game);
        return game;
    }

    public Game findById(String id){
        Game game = gameMap.get(id);
        if(game == null){
            throw new GameNotFoundException("No game found for game id: " + id);
        }
        return game;
    }
}
