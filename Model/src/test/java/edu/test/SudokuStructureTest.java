package edu.test;

import edu.model.*;

import edu.model.exceptions.SudokuStructureSizeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SudokuStructureTest {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
    @Test
    public void incorrectConstructorParameterTest() {
        List<SudokuField> sudokuFieldsTest = Arrays.asList(new SudokuField[33]);
        for (int i = 0; i < sudokuFieldsTest.size(); i++) {
            sudokuFieldsTest.set(i, new SudokuField(i + 1));
        }
        SudokuStructureSizeException incorrectConstructorFieldsException = Assertions.assertThrows(SudokuStructureSizeException.class, () -> new SudokuRow(sudokuFieldsTest));
        Assertions.assertEquals(resourceBundle.getString("SudokuStructureConstructor"), incorrectConstructorFieldsException.getMessage());
        SudokuStructureSizeException nullConstructorFieldsException = Assertions.assertThrows(SudokuStructureSizeException.class, () -> new SudokuRow(null));
        Assertions.assertEquals(resourceBundle.getString("SudokuStructureConstructor"), nullConstructorFieldsException.getMessage());
    }

    @Test
    public void correctVerifyTests() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest1 = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest1.solveGame();
        List<SudokuField> fields = Arrays.asList(new SudokuField[sudokuBoardTest1.getBoard().length]);
        SudokuRow sudokuRowTest1 = new SudokuRow(fields);
        sudokuRowTest1 = sudokuBoardTest1.getRow(1);
        Assertions.assertTrue(sudokuRowTest1.verify());
        SudokuColumn sudokuColumnTest1 = new SudokuColumn(fields);
        sudokuColumnTest1 = sudokuBoardTest1.getColumn(1);
        Assertions.assertTrue(sudokuColumnTest1.verify());
        SudokuBox sudokuBoxTest1 = new SudokuBox(fields);
        sudokuBoxTest1 = sudokuBoardTest1.getBox(1, 1);
        Assertions.assertTrue(sudokuBoxTest1.verify());
    }

    @Test
    public void incorrectVerifyTests() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest1 = sudokuBoardFactoryTest.createInstance();
        int boardLength = sudokuBoardTest1.getBoard().length;
        List<SudokuField> fields1 = Arrays.asList(new SudokuField[boardLength]);
        fields1.replaceAll(ignored -> new SudokuField());
        fields1.get(2).setFieldValue(-2);

        SudokuRow sudokuRowTest1 = new SudokuRow(fields1);
        Assertions.assertFalse(sudokuRowTest1.verify());
        SudokuColumn sudokuColumnTest1 = new SudokuColumn(fields1);
        Assertions.assertFalse(sudokuColumnTest1.verify());
        SudokuBox sudokuBoxTest1 = new SudokuBox(fields1);
        Assertions.assertFalse(sudokuBoxTest1.verify());

        List<SudokuField> fields2 = Arrays.asList(new SudokuField[boardLength]);
        fields2.replaceAll(ignored -> new SudokuField());
        fields2.get(2).setFieldValue(boardLength + 1);
        SudokuRow sudokuRowTest2 = new SudokuRow(fields2);
        Assertions.assertFalse(sudokuRowTest2.verify());
        SudokuColumn sudokuColumnTest2 = new SudokuColumn(fields2);
        Assertions.assertFalse(sudokuColumnTest2.verify());
        SudokuBox sudokuBoxTest2 = new SudokuBox(fields2);
        Assertions.assertFalse(sudokuBoxTest2.verify());

        List<SudokuField> fields3 = Arrays.asList(new SudokuField[boardLength]);
        fields3.replaceAll(ignored -> new SudokuField());
        for (SudokuField sudokuField : fields3) {
            sudokuField.setFieldValue(1);
        }
        SudokuRow sudokuRowTest3 = new SudokuRow(fields3);
        Assertions.assertFalse(sudokuRowTest3.verify());
        SudokuColumn sudokuColumnTest3 = new SudokuColumn(fields3);
        Assertions.assertFalse(sudokuColumnTest3.verify());
        SudokuBox sudokuBoxTest3 = new SudokuBox(fields3);
        Assertions.assertFalse(sudokuBoxTest3.verify());
    }

}
