package edu.model.exceptions;

public class JdbcSudokuBoardDaoReadException extends SudokuBoardIoOperationException {
    public JdbcSudokuBoardDaoReadException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
