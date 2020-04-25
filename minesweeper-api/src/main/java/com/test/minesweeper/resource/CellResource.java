package com.test.minesweeper.resource;

import com.test.minesweeper.dto.ClickRequest;
import com.test.minesweeper.model.Game;
import com.test.minesweeper.service.CellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CellResource {

    @Autowired
    private CellService cellService;

    @GetMapping("/cells")
    private Game intializeGame() {
        return cellService.initializeGame();
    }

    @PostMapping("/cells")
    private Game intializeGame(@RequestBody ClickRequest request) {
        return cellService.clickCell(request);
    }
}
