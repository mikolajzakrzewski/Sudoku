package edu.test;

import edu.model.SudokuField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SudokuFieldTest {
    @Test
    public void SudokuFieldToStringTest() {
        SudokuField sudokuFieldTest = new SudokuField(5);
        Assertions.assertEquals("SudokuField[value=5]", sudokuFieldTest.toString());
    }

    @Test
    public void SudokuFieldEqualsTest() {
        SudokuField sudokuFieldTest1 = new SudokuField(5);
        SudokuField sudokuFieldTest2 = new SudokuField(8);
        SudokuField sudokuFieldTest3 = new SudokuField(5);
        Assertions.assertTrue(sudokuFieldTest1.equals(sudokuFieldTest3));
        Assertions.assertFalse(sudokuFieldTest1.equals(sudokuFieldTest2));
        Assertions.assertTrue(sudokuFieldTest1.equals(sudokuFieldTest1));
        Assertions.assertFalse(sudokuFieldTest1.equals(null));
        Assertions.assertFalse(sudokuFieldTest1.equals(new Object()));
    }

    @Test
    public void SudokuFieldHashTest() {
        SudokuField sudokuFieldTest1 = new SudokuField(5);
        SudokuField sudokuFieldTest2 = new SudokuField(8);
        SudokuField sudokuFieldTest3 = new SudokuField(5);
        Assertions.assertTrue(sudokuFieldTest1.equals(sudokuFieldTest3));
        Assertions.assertEquals(sudokuFieldTest1.hashCode(), sudokuFieldTest3.hashCode());
        Assertions.assertNotEquals(sudokuFieldTest1.hashCode(), sudokuFieldTest2.hashCode());
    }

    @Test
    public void SudokuFieldCloneTest() {
        SudokuField sudokuFieldTest = new SudokuField(5);
        SudokuField sudokuFieldClonedTest = sudokuFieldTest.clone();
        Assertions.assertEquals(sudokuFieldTest, sudokuFieldClonedTest);
        sudokuFieldTest.setFieldValue(2);
        Assertions.assertNotEquals(sudokuFieldTest, sudokuFieldClonedTest);
    }
    @Test
    public void SudokuFieldCompareToTest () {
        SudokuField sudokuFieldTest1 = new SudokuField(5);
        SudokuField sudokuFieldTest2 = new SudokuField(7);
        SudokuField sudokuFieldTest3 = new SudokuField(5);
        SudokuField nullSudokuFieldTest = null;
        Assertions.assertTrue(sudokuFieldTest1.compareTo(sudokuFieldTest2) < 0);
        Assertions.assertNotEquals(sudokuFieldTest1, sudokuFieldTest2);
        Assertions.assertTrue(sudokuFieldTest2.compareTo(sudokuFieldTest1) > 0);
        Assertions.assertEquals(sudokuFieldTest1, sudokuFieldTest3);
        Assertions.assertEquals(sudokuFieldTest1.compareTo(sudokuFieldTest3),0);
        Assertions.assertEquals(sudokuFieldTest1.compareTo(sudokuFieldTest2), -sudokuFieldTest2.compareTo(sudokuFieldTest1));
        Assertions.assertThrows(NullPointerException.class, () -> sudokuFieldTest1.compareTo(nullSudokuFieldTest));
        Assertions.assertThrows(NullPointerException.class, () -> nullSudokuFieldTest.compareTo(sudokuFieldTest1));
    }
}