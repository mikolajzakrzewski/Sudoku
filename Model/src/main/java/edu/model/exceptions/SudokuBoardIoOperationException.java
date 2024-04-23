package edu.model.exceptions;

import java.io.IOException;

public class SudokuBoardIoOperationException extends IOException {
    public SudokuBoardIoOperationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public SudokuBoardIoOperationException(String message) {
        super(message);
    }
}
