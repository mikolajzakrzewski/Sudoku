package edu.model.exceptions;

public class JdbcSudokuBoardDaoConnectionException extends JdbcSudokuBoardDaoException {
    public JdbcSudokuBoardDaoConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
