package edu.model;

import edu.model.exceptions.JdbcSudokuBoardDaoException;

public class SudokuBoardDaoFactory {
    public Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public Dao<SudokuBoard> getJdbcDao(String boardName) throws JdbcSudokuBoardDaoException {
        JdbcSudokuBoardDao jdbcSudokuBoardDao;
        try {
            jdbcSudokuBoardDao = new JdbcSudokuBoardDao(boardName);
            return jdbcSudokuBoardDao;
        } catch (JdbcSudokuBoardDaoException e) {
            throw new JdbcSudokuBoardDaoException(e);
        }
    }
}