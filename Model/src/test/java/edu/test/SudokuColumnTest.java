package edu.test;

import edu.model.SudokuColumn;
import edu.model.SudokuField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SudokuColumnTest {
    @Test
    public void SudokuColumnToStringTest() {
        List<SudokuField> sudokuFieldsTest1 = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < sudokuFieldsTest1.size(); i++) {
            sudokuFieldsTest1.set(i, new SudokuField(i + 1));
        }
        SudokuColumn sudokuColumnTest1 = new SudokuColumn(sudokuFieldsTest1);
        Assertions.assertEquals("SudokuColumn[fields=[SudokuField[value=1], SudokuField[value=2], SudokuField[value=3], SudokuField[value=4], SudokuField[value=5], SudokuField[value=6], SudokuField[value=7], SudokuField[value=8], SudokuField[value=9]]]", sudokuColumnTest1.toString());
    }

    @Test
    public void SudokuColumnEqualsTest() {
        List<SudokuField> sudokuFieldsTest1 = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < sudokuFieldsTest1.size(); i++) {
            sudokuFieldsTest1.set(i, new SudokuField(i + 1));
        }
        List<SudokuField> sudokuFieldsTest2 = Arrays.asList(new SudokuField[9]);
        sudokuFieldsTest2.replaceAll(ignored -> new SudokuField(5));
        SudokuColumn sudokuColumnTest1 = new SudokuColumn(sudokuFieldsTest1);
        SudokuColumn sudokuColumnTest2 = new SudokuColumn(sudokuFieldsTest1);
        SudokuColumn sudokuColumnTest3 = new SudokuColumn(sudokuFieldsTest2);
        Assertions.assertTrue(sudokuColumnTest1.equals(sudokuColumnTest2));
        Assertions.assertFalse(sudokuColumnTest1.equals(sudokuColumnTest3));
        Assertions.assertTrue(sudokuColumnTest1.equals(sudokuColumnTest1));
        Assertions.assertFalse(sudokuColumnTest1.equals(null));
        Assertions.assertFalse(sudokuColumnTest2.equals(new Object()));
    }

    @Test
    public void SudokuColumnHashTest() {
        List<SudokuField> sudokuFieldsTest1 = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < sudokuFieldsTest1.size(); i++) {
            sudokuFieldsTest1.set(i, new SudokuField(i + 1));
        }
        List<SudokuField> sudokuFieldsTest2 = Arrays.asList(new SudokuField[9]);
        sudokuFieldsTest2.replaceAll(ignored -> new SudokuField(5));
        SudokuColumn sudokuColumnTest1 = new SudokuColumn(sudokuFieldsTest1);
        SudokuColumn sudokuColumnTest2 = new SudokuColumn(sudokuFieldsTest1);
        SudokuColumn sudokuColumnTest3 = new SudokuColumn(sudokuFieldsTest2);
        Assertions.assertTrue(sudokuColumnTest1.equals(sudokuColumnTest2));
        Assertions.assertEquals(sudokuColumnTest1.hashCode(), sudokuColumnTest2.hashCode());
        Assertions.assertNotEquals(sudokuColumnTest1.hashCode(), sudokuColumnTest3.hashCode());
    }

    @Test
    public void SudokuColumnCloneTest() {
        List<SudokuField> sudokuFieldsTest = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < sudokuFieldsTest.size(); i++) {
            sudokuFieldsTest.set(i, new SudokuField(i + 1));
        }
        SudokuColumn sudokuColumnTest = new SudokuColumn(sudokuFieldsTest);
        SudokuColumn sudokuColumnClonedTest = sudokuColumnTest.clone();
        Assertions.assertEquals(sudokuColumnTest, sudokuColumnClonedTest);
        sudokuFieldsTest.get(4).setFieldValue(9);
        Assertions.assertNotEquals(sudokuColumnTest, sudokuColumnClonedTest);
    }
}
