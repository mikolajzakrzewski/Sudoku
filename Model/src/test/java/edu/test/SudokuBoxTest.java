package edu.test;

import edu.model.SudokuField;
import edu.model.SudokuBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SudokuBoxTest {
    @Test
    public void SudokuBoxToStringTest() {
        List<SudokuField> sudokuFieldsTest1 = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < sudokuFieldsTest1.size(); i++) {
            sudokuFieldsTest1.set(i, new SudokuField(i + 1));
        }
        SudokuBox sudokuBoxTest1 = new SudokuBox(sudokuFieldsTest1);
        Assertions.assertEquals("SudokuBox[fields=[SudokuField[value=1], SudokuField[value=2], SudokuField[value=3], SudokuField[value=4], SudokuField[value=5], SudokuField[value=6], SudokuField[value=7], SudokuField[value=8], SudokuField[value=9]]]", sudokuBoxTest1.toString());
    }

    @Test
    public void SudokuBoxEqualsTest() {
        List<SudokuField> sudokuFieldsTest1 = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < sudokuFieldsTest1.size(); i++) {
            sudokuFieldsTest1.set(i, new SudokuField(i + 1));
        }
        List<SudokuField> sudokuFieldsTest2 = Arrays.asList(new SudokuField[9]);
        sudokuFieldsTest2.replaceAll(ignored -> new SudokuField(5));
        SudokuBox sudokuBoxTest1 = new SudokuBox(sudokuFieldsTest1);
        SudokuBox sudokuBoxTest2 = new SudokuBox(sudokuFieldsTest1);
        SudokuBox sudokuBoxTest3 = new SudokuBox(sudokuFieldsTest2);
        Assertions.assertTrue(sudokuBoxTest1.equals(sudokuBoxTest2));
        Assertions.assertFalse(sudokuBoxTest1.equals(sudokuBoxTest3));
        Assertions.assertTrue(sudokuBoxTest1.equals(sudokuBoxTest1));
        Assertions.assertFalse(sudokuBoxTest1.equals(null));
        Assertions.assertFalse(sudokuBoxTest1.equals(new Object()));
    }

    @Test
    public void SudokuBoxHashTest() {
        List<SudokuField> sudokuFieldsTest1 = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < sudokuFieldsTest1.size(); i++) {
            sudokuFieldsTest1.set(i, new SudokuField(i + 1));
        }
        List<SudokuField> sudokuFieldsTest2 = Arrays.asList(new SudokuField[9]);
        sudokuFieldsTest2.replaceAll(ignored -> new SudokuField(5));
        SudokuBox sudokuBoxTest1 = new SudokuBox(sudokuFieldsTest1);
        SudokuBox sudokuBoxTest2 = new SudokuBox(sudokuFieldsTest1);
        SudokuBox sudokuBoxTest3 = new SudokuBox(sudokuFieldsTest2);
        Assertions.assertTrue(sudokuBoxTest1.equals(sudokuBoxTest2));
        Assertions.assertEquals(sudokuBoxTest1.hashCode(), sudokuBoxTest2.hashCode());
        Assertions.assertNotEquals(sudokuBoxTest1.hashCode(), sudokuBoxTest3.hashCode());
    }

    @Test
    public void SudokuBoxCloneTest() {
        List<SudokuField> sudokuFieldsTest = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < sudokuFieldsTest.size(); i++) {
            sudokuFieldsTest.set(i, new SudokuField(i + 1));
        }
        SudokuBox sudokuBoxTest = new SudokuBox(sudokuFieldsTest);
        SudokuBox sudokuBoxClonedTest = sudokuBoxTest.clone();
        Assertions.assertEquals(sudokuBoxTest, sudokuBoxClonedTest);
        sudokuFieldsTest.get(5).setFieldValue(4);
        Assertions.assertNotEquals(sudokuBoxTest, sudokuBoxClonedTest);
    }
}
