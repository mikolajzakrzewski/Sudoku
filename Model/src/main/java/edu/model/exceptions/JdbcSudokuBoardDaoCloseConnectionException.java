package edu.model.exceptions;

public class JdbcSudokuBoardDaoCloseConnectionException extends JdbcSudokuBoardDaoException {
    public JdbcSudokuBoardDaoCloseConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
