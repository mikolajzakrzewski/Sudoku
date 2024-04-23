package edu.test;

import edu.model.*;
import edu.model.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public class FileSudokuBoardDaoTest {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());

    @Test
    public void SudokuBoardWriteReadTest() throws SudokuBoardIoOperationException {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest1 = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest1.solveGame();
        SudokuBoard sudokuBoardTest2;
        SudokuBoardDaoFactory sudokuBoardDaoFactoryTest = new SudokuBoardDaoFactory();
        String fileName = "fileTest.dat";

        Dao<SudokuBoard> fileDao = sudokuBoardDaoFactoryTest.getFileDao(fileName);
        fileDao.write(sudokuBoardTest1);
        sudokuBoardTest2 = fileDao.read();

        File fileTest = new File(fileName);
        fileTest.deleteOnExit();

        Assertions.assertEquals(sudokuBoardTest1, sudokuBoardTest2);
    }

    @Test
    public void SudokuBoardReadExceptionTest() {
        SudokuBoardDaoFactory sudokuBoardDaoFactoryTest = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> fileDao;
        fileDao = sudokuBoardDaoFactoryTest.getFileDao("z");
        SudokuBoardReadException sudokuBoardReadException = Assertions.assertThrows(SudokuBoardReadException.class, fileDao::read);
        Assertions.assertEquals(resourceBundle.getString("FileSudokuBoardReadException"), sudokuBoardReadException.getMessage());
    }
}
