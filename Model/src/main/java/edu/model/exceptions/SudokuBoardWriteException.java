package edu.model.exceptions;

public class SudokuBoardWriteException extends SudokuBoardIoOperationException {
    public SudokuBoardWriteException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
