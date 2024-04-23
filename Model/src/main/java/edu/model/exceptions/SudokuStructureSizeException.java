package edu.model.exceptions;

public class SudokuStructureSizeException extends IllegalArgumentException {
    public SudokuStructureSizeException(String message) {
        super(message);
    }
}
