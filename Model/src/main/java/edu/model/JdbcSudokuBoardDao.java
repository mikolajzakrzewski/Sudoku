package edu.model;

import edu.model.exceptions.JdbcSudokuBoardDaoCloseConnectionException;
import edu.model.exceptions.JdbcSudokuBoardDaoConnectionException;
import edu.model.exceptions.JdbcSudokuBoardDaoReadException;
import edu.model.exceptions.JdbcSudokuBoardDaoWriteException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());

    static final Logger logger = LogManager.getLogger(JdbcSudokuBoardDao.class);

    private static final String URL = "jdbc:derby:Sudoku;create=true";

    private final Connection connection;

    private final Statement statement;

    private final String boardName;

    public JdbcSudokuBoardDao(String boardName) throws JdbcSudokuBoardDaoConnectionException {
        this.boardName = boardName;
        try {
            connection = DriverManager.getConnection(URL);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            logger.debug(resourceBundle.getString("JdbcConnectionSuccessful"));
        } catch (SQLException e) {
            logger.error(resourceBundle.getString("JdbcConnectionFailed"));
            throw new JdbcSudokuBoardDaoConnectionException(resourceBundle.getString("JdbcConnectionFailed"), e);
        }
    }

    @Override
    public SudokuBoard read() throws JdbcSudokuBoardDaoReadException {
        SudokuBoard sudokuBoard = new SudokuBoardFactory().createInstance();
        int boardID;
        String fields;
        try (PreparedStatement boardQueryStatement = connection.prepareStatement(
                "SELECT SudokuBoards.boardID FROM SudokuBoards WHERE SudokuBoards.boardName = ?"
        )) {
            boardQueryStatement.setString(1, this.boardName);
            try (ResultSet boardQueryResultSet = boardQueryStatement.executeQuery()) {
                if (!boardQueryResultSet.next()) {
                    throw new SQLException();
                } else {
                    boardID = boardQueryResultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
                logger.error(resourceBundle.getString("JdbcReadFailed"));
                throw new JdbcSudokuBoardDaoReadException(resourceBundle.getString("JdbcReadFailed"), e);
            } catch (SQLException ex) {
                logger.error(resourceBundle.getString("JdbcReadFailed"));
                throw new JdbcSudokuBoardDaoReadException(resourceBundle.getString("JdbcReadFailed"), e);
            }
        }
        try (PreparedStatement layoutQueryStatement = connection.prepareStatement(
                "SELECT SudokuLayouts.fields FROM SudokuLayouts WHERE SudokuLayouts.boardID = ?"
        )) {
            layoutQueryStatement.setInt(1, boardID);
            try (ResultSet layoutQueryResultSet = layoutQueryStatement.executeQuery()) {
                layoutQueryResultSet.next();
                fields = layoutQueryResultSet.getString(1);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                logger.error(resourceBundle.getString("JdbcReadFailed"));
                throw new JdbcSudokuBoardDaoReadException(resourceBundle.getString("JdbcReadFailed"), e);
            } catch (SQLException ex) {
                logger.error(resourceBundle.getString("JdbcReadFailed"));
                throw new JdbcSudokuBoardDaoReadException(resourceBundle.getString("JdbcReadFailed"), e);
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int position = i * 9 + j;
                char charValue = fields.charAt(position);
                int intValue = Character.getNumericValue(charValue);
                sudokuBoard.set(i, j, intValue);
            }
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) throws JdbcSudokuBoardDaoWriteException {
        if (obj == null) {
            throw new JdbcSudokuBoardDaoWriteException(resourceBundle.getString("JdbcWriteFailed"));
        }
        String fields = obj.toString().replaceAll("\\D+","");
        try {
            statement.execute(
                    "CREATE TABLE SudokuLayouts ("
                            + "boardID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + "fields varchar(81) UNIQUE,"
                            + "PRIMARY KEY (boardID))"
            );
            statement.execute(
                    "CREATE TABLE SudokuBoards ("
                            + "boardName varchar(50) NOT NULL,"
                            + "boardID int,"
                            + "PRIMARY KEY (boardName),"
                            + "FOREIGN KEY (boardID) REFERENCES SudokuLayouts(boardID))"
            );
            connection.commit();
        } catch (SQLException e) {
            if (!e.getSQLState().equals("X0Y32")) {
                logger.error(resourceBundle.getString("JdbcWriteFailed"));
                throw new JdbcSudokuBoardDaoWriteException(resourceBundle.getString("JdbcWriteFailed"), e);
            }
        }
        try {
            int boardId;
            try (PreparedStatement layoutQueryStatement = connection.prepareStatement(
                    "SELECT SudokuLayouts.boardID FROM SudokuLayouts WHERE SudokuLayouts.fields = ?"
            )) {
                layoutQueryStatement.setString(1, fields);
                try (ResultSet layoutQueryResultSet = layoutQueryStatement.executeQuery()) {
                    if (!layoutQueryResultSet.next()) {
                        try (PreparedStatement layoutInsertStatement = connection.prepareStatement(
                                "INSERT INTO SudokuLayouts values (DEFAULT, ?)"
                        )) {
                            layoutInsertStatement.setString(1, fields);
                            layoutInsertStatement.executeUpdate();
                        }
                    }
                }
                try (ResultSet updatedLayoutQueryResultSet = layoutQueryStatement.executeQuery()) {
                    updatedLayoutQueryResultSet.next();
                    boardId = updatedLayoutQueryResultSet.getInt(1);
                }
            }
            try (PreparedStatement boardQueryStatement = connection.prepareStatement(
                    "SELECT * FROM SudokuBoards WHERE SudokuBoards.boardName = ?"
            )) {
                boardQueryStatement.setString(1, this.boardName);
                try (ResultSet boardQueryResultSet = boardQueryStatement.executeQuery()) {
                    if (!boardQueryResultSet.next()) {
                        try (PreparedStatement boardInsertStatement = connection.prepareStatement(
                                "INSERT INTO SudokuBoards values (?, ?)"
                        )) {
                            boardInsertStatement.setString(1, this.boardName);
                            boardInsertStatement.setInt(2, boardId);
                            boardInsertStatement.executeUpdate();
                        }
                    } else {
                        try (PreparedStatement boardUpdateStatement = connection.prepareStatement(
                                "UPDATE SudokuBoards SET boardID = ? WHERE boardName = ?"
                        )) {
                            boardUpdateStatement.setInt(1, boardId);
                            boardUpdateStatement.setString(2, this.boardName);
                            boardUpdateStatement.executeUpdate();
                        }
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                logger.error(resourceBundle.getString("JdbcWriteFailed"));
                throw new JdbcSudokuBoardDaoWriteException(resourceBundle.getString("JdbcWriteFailed"), e);
            } catch (SQLException ex) {
                logger.error(resourceBundle.getString("JdbcWriteFailed"));
                throw new JdbcSudokuBoardDaoWriteException(resourceBundle.getString("JdbcWriteFailed"), e);
            }
        }
    }

    @Override
    public void close() throws JdbcSudokuBoardDaoCloseConnectionException {
        try {
            statement.close();
            connection.close();
            logger.debug(resourceBundle.getString("JdbcCloseConnectionSuccessful"));
        } catch (SQLException e) {
            logger.error(resourceBundle.getString("JdbcCloseConnectionFailed"));
            throw new JdbcSudokuBoardDaoCloseConnectionException(
                    resourceBundle.getString("JdbcCloseConnectionFailed"), e
            );
        }
    }
}
