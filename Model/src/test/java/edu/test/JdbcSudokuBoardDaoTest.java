package edu.test;

import edu.model.Dao;
import edu.model.SudokuBoard;
import edu.model.SudokuBoardDaoFactory;
import edu.model.SudokuBoardFactory;
import edu.model.exceptions.JdbcSudokuBoardDaoException;
import edu.model.exceptions.JdbcSudokuBoardDaoReadException;
import edu.model.exceptions.JdbcSudokuBoardDaoWriteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class JdbcSudokuBoardDaoTest {

    @Test
    public void JdbcSudokuBoardWriteTest() throws JdbcSudokuBoardDaoException {
        SudokuBoardFactory sudokuBoardFactory = new SudokuBoardFactory();
        SudokuBoard sudokuBoard = sudokuBoardFactory.createInstance();
        SudokuBoard sudokuBoard1 = sudokuBoardFactory.createInstance();
        sudokuBoard.solveGame();
        sudokuBoard1.solveGame();
        String boardName = "sudokuBoardTest";
        try (Dao<SudokuBoard> jdbcSudokuBoardDaoTest = new SudokuBoardDaoFactory().getJdbcDao(boardName)) {
            jdbcSudokuBoardDaoTest.write(sudokuBoard);
            jdbcSudokuBoardDaoTest.write(sudokuBoard1);
            SudokuBoard sudokuBoard3 = jdbcSudokuBoardDaoTest.read();
            try (Connection connection = DriverManager.getConnection("jdbc:derby:Sudoku")) {
                connection.setAutoCommit(false);
                try (PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM SudokuBoards WHERE boardName = ?"
                )) {
                    preparedStatement.setString(1, boardName);
                    preparedStatement.execute();
                    connection.commit();
                } catch (Exception e) {
                    connection.rollback();
                    throw new JdbcSudokuBoardDaoException(e);
                }
            }
            Assertions.assertEquals(sudokuBoard1, sudokuBoard3);
            Assertions.assertNotSame(sudokuBoard1, sudokuBoard3);
        } catch (Exception e) {
            throw new JdbcSudokuBoardDaoException(e);
        }
    }

    @Test
    public void JdbcSudokuBoardReadExceptionTest() throws JdbcSudokuBoardDaoException {
        try (Dao<SudokuBoard> jdbcSudokuBoardDaoTest = new SudokuBoardDaoFactory().getJdbcDao("sudokuBoardTest")) {
            Assertions.assertThrows(JdbcSudokuBoardDaoReadException.class, jdbcSudokuBoardDaoTest::read);
        } catch (Exception e) {
            throw new JdbcSudokuBoardDaoException(e);
        }
    }

    @Test
    public void JdbcSudokuBoardWriteExceptionTest() throws JdbcSudokuBoardDaoException {
        try (Dao<SudokuBoard> jdbcSudokuBoardDaoTest = new SudokuBoardDaoFactory().getJdbcDao("sudokuBoardTest")) {
            Assertions.assertThrows(JdbcSudokuBoardDaoWriteException.class, () -> jdbcSudokuBoardDaoTest.write(null));
        } catch (Exception e) {
            throw new JdbcSudokuBoardDaoException(e);
        }
    }
 }

