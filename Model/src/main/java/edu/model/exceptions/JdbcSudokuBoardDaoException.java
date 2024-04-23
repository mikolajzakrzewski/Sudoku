package edu.model.exceptions;

import java.sql.SQLException;

public class JdbcSudokuBoardDaoException extends SQLException {
    public JdbcSudokuBoardDaoException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public JdbcSudokuBoardDaoException(Throwable throwable) {
        super(throwable);
    }
}
