package com.test.minesweeper;

import com.test.minesweeper.dto.Action;
import com.test.minesweeper.dto.CellDto;
import com.test.minesweeper.dto.ClickRequest;
import com.test.minesweeper.validation.ClickRequestValidation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the game id validation
 */
public class ClickRequestValidationTest {

    private ClickRequestValidation requestValidator = new ClickRequestValidation();

    @Test
    public void shouldReturnEmptyErrorListWhenValidRequest(){
        ClickRequest request = new ClickRequest();
        request.setAction(Action.CLICK);
        request.setCell(new CellDto());
        request.setGameId(UUID.randomUUID().toString());
        List<String> errors = requestValidator.isValid(request);
        assertEquals(0, errors.size());
    }

    @Test
    public void shouldFailWhenInvalidGameId(){
        ClickRequest request = new ClickRequest();
        request.setAction(Action.CLICK);
        request.setCell(new CellDto());
        request.setGameId("Invalid id");
        List<String> errors = requestValidator.isValid(request);
        assertTrue(errors.contains("Request must contain a valid game id in UUID format"));
    }

    @Test
    public void shouldFailWhenNullGameId(){
        ClickRequest request = new ClickRequest();
        request.setAction(Action.CLICK);
        request.setCell(new CellDto());
        List<String> errors = requestValidator.isValid(request);
        assertTrue(errors.contains("Request must contain a valid game id in UUID format"));
    }

    @Test
    public void shouldFailWhenNullAction(){
        ClickRequest request = new ClickRequest();
        request.setCell(new CellDto());
        request.setGameId(UUID.randomUUID().toString());
        List<String> errors = requestValidator.isValid(request);
        assertTrue(errors.contains("Request must contain a valid action"));
    }

    @Test
    public void shouldFailWhenNullCellDto(){
        ClickRequest request = new ClickRequest();
        request.setGameId(UUID.randomUUID().toString());
        List<String> errors = requestValidator.isValid(request);
        assertTrue(errors.contains("Request must contain the cell coordinates in which to perform an action"));
    }
}
