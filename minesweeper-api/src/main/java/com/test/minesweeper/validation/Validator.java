package com.test.minesweeper.validation;

import java.util.List;

/**
 * Interface to validate object state
 */
public interface Validator<T> {
    List<String> isValid(T object);
}
