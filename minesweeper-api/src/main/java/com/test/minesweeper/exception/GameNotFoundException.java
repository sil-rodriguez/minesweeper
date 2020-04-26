package com.test.minesweeper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to represent a game id that can't be found
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String message) {
        super(message);
    }
}
