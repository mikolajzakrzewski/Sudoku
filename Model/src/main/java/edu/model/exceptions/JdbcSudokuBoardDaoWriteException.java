package edu.model.exceptions;

public class JdbcSudokuBoardDaoWriteException extends SudokuBoardIoOperationException {
    public JdbcSudokuBoardDaoWriteException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public JdbcSudokuBoardDaoWriteException(String message) {
        super(message);
    }
}
