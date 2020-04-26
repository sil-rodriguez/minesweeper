package com.test.minesweeper.resource;

import com.test.minesweeper.dto.ClickRequest;
import com.test.minesweeper.exception.InvalidRequestException;
import com.test.minesweeper.model.Game;
import com.test.minesweeper.service.CellService;
import com.test.minesweeper.validation.ClickRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CellResource {

    @Autowired
    private CellService cellService;

    private ClickRequestValidation requestValidation = new ClickRequestValidation();

    @GetMapping("/cells")
    private Game intializeGame(){
        return cellService.initializeGame();
    }

    @PostMapping("/cells")
    private Game clickCell(@RequestBody ClickRequest request) {
        List<String> errors = requestValidation.isValid(request);
        if(errors.isEmpty()){
            return cellService.clickCell(request);
        }
        throw new InvalidRequestException(String.join(";",errors));
    }
}
