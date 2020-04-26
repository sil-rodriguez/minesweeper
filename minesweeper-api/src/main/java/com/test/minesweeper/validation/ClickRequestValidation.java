package com.test.minesweeper.validation;

import com.test.minesweeper.dto.ClickRequest;
import org.hibernate.validator.internal.metadata.facets.Validatable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Validation class for the click request
 */
public class ClickRequestValidation implements Validator<ClickRequest> {

    public List<String> isValid(ClickRequest request){
        List<String> errors = new ArrayList<>();
        if(StringUtils.isEmpty(request.getGameId())
            || !isValidGameId(request.getGameId())){
            errors.add("Request must contain a valid game id in UUID format");
        }
        if(request.getCell() == null){
            errors.add("Request must contain the cell coordinates in which to perform an action");
        }
        if(request.getAction() == null){
            errors.add("Request must contain a valid action");
        }
        return errors;
    }

    private boolean isValidGameId(String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            return false;
        }
        return true;
    }
}
