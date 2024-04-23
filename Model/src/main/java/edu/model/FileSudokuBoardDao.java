package edu.model;

import edu.model.exceptions.SudokuBoardReadException;
import edu.model.exceptions.SudokuBoardWriteException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    private final String fileName;

    private static final Logger logger = LogManager.getLogger(SudokuField.class);

    FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws SudokuBoardReadException {
        try (FileInputStream fis = new FileInputStream(this.fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (SudokuBoard) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
            logger.error(resourceBundle.getString("FileSudokuBoardReadException"));
            throw new SudokuBoardReadException(resourceBundle.getString("FileSudokuBoardReadException"), e);
        }
    }

    @Override
    public void write(SudokuBoard obj) throws SudokuBoardWriteException {
        try (FileOutputStream fis = new FileOutputStream(this.fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fis)) {
            oos.writeObject(obj);
        } catch (IOException e) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
            logger.error(resourceBundle.getString("FileSudokuBoardWriteException"));
            throw new SudokuBoardWriteException(resourceBundle.getString("FileSudokuBoardWriteException"), e);
        }
    }

    @Override
    public void close() {

    }
}
