package edu.model.exceptions;

public class SudokuBoardReadException extends SudokuBoardIoOperationException {
    public SudokuBoardReadException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
