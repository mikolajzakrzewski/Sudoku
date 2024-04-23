package edu.test;

import edu.model.SudokuField;
import edu.model.SudokuRow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SudokuRowTest {
    @Test
    public void SudokuRowToStringTest() {
        List<SudokuField> sudokuFieldsTest1 = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < sudokuFieldsTest1.size(); i++) {
            sudokuFieldsTest1.set(i, new SudokuField(i + 1));
        }
        SudokuRow sudokuRowTest1 = new SudokuRow(sudokuFieldsTest1);
        Assertions.assertEquals("SudokuRow[fields=[SudokuField[value=1], SudokuField[value=2], SudokuField[value=3], SudokuField[value=4], SudokuField[value=5], SudokuField[value=6], SudokuField[value=7], SudokuField[value=8], SudokuField[value=9]]]", sudokuRowTest1.toString());
    }

    @Test
    public void SudokuRowEqualsTest() {
        List<SudokuField> sudokuFieldsTest1 = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < sudokuFieldsTest1.size(); i++) {
            sudokuFieldsTest1.set(i, new SudokuField(i + 1));
        }
        List<SudokuField> sudokuFieldsTest2 = Arrays.asList(new SudokuField[9]);
        sudokuFieldsTest2.replaceAll(ignored -> new SudokuField(5));
        SudokuRow sudokuRowTest1 = new SudokuRow(sudokuFieldsTest1);
        SudokuRow sudokuRowTest2 = new SudokuRow(sudokuFieldsTest1);
        SudokuRow sudokuRowTest3 = new SudokuRow(sudokuFieldsTest2);
        Assertions.assertTrue(sudokuRowTest1.equals(sudokuRowTest2));
        Assertions.assertFalse(sudokuRowTest1.equals(sudokuRowTest3));
        Assertions.assertTrue(sudokuRowTest1.equals(sudokuRowTest1));
        Assertions.assertFalse(sudokuRowTest1.equals(null));
        Assertions.assertFalse(sudokuRowTest1.equals(new Object()));
    }

    @Test
    public void SudokuRowHashTest() {
        List<SudokuField> sudokuFieldsTest1 = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < sudokuFieldsTest1.size(); i++) {
            sudokuFieldsTest1.set(i, new SudokuField(i + 1));
        }
        List<SudokuField> sudokuFieldsTest2 = Arrays.asList(new SudokuField[9]);
        sudokuFieldsTest2.replaceAll(ignored -> new SudokuField(5));
        SudokuRow sudokuRowTest1 = new SudokuRow(sudokuFieldsTest1);
        SudokuRow sudokuRowTest2 = new SudokuRow(sudokuFieldsTest1);
        SudokuRow sudokuRowTest3 = new SudokuRow(sudokuFieldsTest2);
        Assertions.assertTrue(sudokuRowTest1.equals(sudokuRowTest2));
        Assertions.assertEquals(sudokuRowTest1.hashCode(), sudokuRowTest2.hashCode());
        Assertions.assertNotEquals(sudokuRowTest1.hashCode(), sudokuRowTest3.hashCode());
    }

    @Test
    public void SudokuRowCloneTest() {
        List<SudokuField> sudokuFieldsTest = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < sudokuFieldsTest.size(); i++) {
            sudokuFieldsTest.set(i, new SudokuField(i + 1));
        }
        SudokuRow sudokuRowTest = new SudokuRow(sudokuFieldsTest);
        SudokuRow sudokuRowClonedTest = sudokuRowTest.clone();
        Assertions.assertEquals(sudokuRowTest, sudokuRowClonedTest);
        sudokuFieldsTest.get(3).setFieldValue(1);
        Assertions.assertNotEquals(sudokuRowTest, sudokuRowClonedTest);
    }
}
