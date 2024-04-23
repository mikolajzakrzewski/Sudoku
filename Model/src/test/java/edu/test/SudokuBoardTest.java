package edu.test;

import edu.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class SudokuBoardTest {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
    @Test
    public void correctSetTest() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest1 = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest1.set(3, 1, 3);
        Assertions.assertEquals(sudokuBoardTest1.get(3, 1), 3);
    }

    @Test
    public void incorrectSetTest() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest1 = sudokuBoardFactoryTest.createInstance();
        int boardLength = sudokuBoardTest1.getBoard().length;
        sudokuBoardTest1.solveGame();
        int incorrectValue1 = boardLength + 1;
        int incorrectValue2 = -2;
        sudokuBoardTest1.set(0, 1, incorrectValue1);
        Assertions.assertNotEquals(sudokuBoardTest1.get(0, 1), incorrectValue1);
        sudokuBoardTest1.set(1, 0, incorrectValue2);
        Assertions.assertNotEquals(sudokuBoardTest1.get(1, 0), incorrectValue2);
        int testFieldValue;
        if (sudokuBoardTest1.get(1, 1) == boardLength) {
            testFieldValue = boardLength - 1;
        } else {
            testFieldValue = boardLength;
        }
        sudokuBoardTest1.set(1, 1, testFieldValue);
        Assertions.assertNotEquals(sudokuBoardTest1.get(1, 1), testFieldValue);
    }

    @Test
    public void getRowTest() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest = sudokuBoardFactoryTest.createInstance();
        int boardLength = sudokuBoardTest.getBoard().length;
        boolean correctGetter = true;
        int index = 1;
        for (int i = 0; i < boardLength; i++) {
            sudokuBoardTest.set(index, i, i + 1);
        }
        SudokuRow rowTest = sudokuBoardTest.getRow(index);
        for (int i = 0; i < boardLength; i++) {
            if (i + 1 != rowTest.getFields().get(i).getFieldValue()) {
                correctGetter = false;
                break;
            }
        }
        Assertions.assertTrue(correctGetter);
    }

    @Test
    public void getBoxTest() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest = sudokuBoardFactoryTest.createInstance();
        int boardLength = sudokuBoardTest.getBoard().length;
        int boxLength = (int) Math.sqrt(sudokuBoardTest.getBoard().length);
        int[] boxData = new int[boardLength];
        boolean correctGetter = true;
        for (int i = 0; i < boardLength; i++) {
            boxData[i] = i + 1;
        }
        int index = 1;
        int value = 0;
        for (int row = 0; row < boxLength; row++) {
            for (int column = 0; column < boxLength; column++) {
                sudokuBoardTest.set(row, column, boxData[value++]);
            }
        }
        SudokuBox boxTest = sudokuBoardTest.getBox(index, index);
        for (int i = 0; i < boardLength; i++) {
            if (i + 1 != boxTest.getFields().get(i).getFieldValue()) {
                correctGetter = false;
                break;
            }
        }
        Assertions.assertTrue(correctGetter);
    }

    @Test
    public void getColumnTest() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest = sudokuBoardFactoryTest.createInstance();
        int boardLength = sudokuBoardTest.getBoard().length;
        boolean correctGetter = true;
        int index = 1;
        for (int i = 0; i < boardLength; i++) {
            sudokuBoardTest.set(i, index, i + 1);
        }
        SudokuColumn columnTest = sudokuBoardTest.getColumn(index);
        for (int i = 0; i < boardLength; i++) {
            if (i + 1 != columnTest.getFields().get(i).getFieldValue()) {
                correctGetter = false;
                break;
            }
        }
        Assertions.assertTrue(correctGetter);
    }

    @Test
    public void SudokuBoardToStringTest() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest.solveGame();
        int boardLength = sudokuBoardTest.getBoard().length;
        StringBuilder test = new StringBuilder("SudokuBoard[board={{");
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                test.append("SudokuField[value=").append(Integer.toString(sudokuBoardTest.get(i, j))).append("]");
                if (j < boardLength - 1) {
                    test.append(",");
                }
            }
            if (i < boardLength - 1) {
                test.append("},{");
            }
        }
        test.append("}},sudokuSolver=BacktrackingSudokuSolver[]]");
        Assertions.assertEquals(test.toString(), sudokuBoardTest.toString());
    }

    @Test
    public void SudokuBoardEqualsTest() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest1 = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest1.set(1, 1, 1);
        SudokuBoard sudokuBoardTest2 = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest2.set(1, 1, 1);
        SudokuBoard sudokuBoardTest3 = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest3.solveGame();
        Assertions.assertTrue(sudokuBoardTest1.equals(sudokuBoardTest2));
        Assertions.assertFalse(sudokuBoardTest1.equals(sudokuBoardTest3));
        Assertions.assertTrue(sudokuBoardTest1.equals(sudokuBoardTest1));
        Assertions.assertFalse(sudokuBoardTest1.equals(null));
        Assertions.assertFalse(sudokuBoardTest1.equals(new Object()));
    }

    @Test
    public void SudokuBoardHashTest() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest1 = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest1.set(1, 1, 1);
        SudokuBoard sudokuBoardTest2 = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest2.set(1, 1, 1);
        SudokuBoard sudokuBoardTest3 = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest3.solveGame();
        Assertions.assertEquals(sudokuBoardTest1, sudokuBoardTest2);
        Assertions.assertEquals(sudokuBoardTest1.hashCode(), sudokuBoardTest2.hashCode());
        Assertions.assertNotEquals(sudokuBoardTest1.hashCode(), sudokuBoardTest3.hashCode());
    }

    @Test
    public void SudokuBoardCloneTest() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest = sudokuBoardFactoryTest.createInstance();
        SudokuBoard sudokuBoardClonedTest = sudokuBoardTest.clone();
        Assertions.assertEquals(sudokuBoardTest, sudokuBoardClonedTest);
        sudokuBoardTest.set(1, 1, 1);
        Assertions.assertNotEquals(sudokuBoardTest, sudokuBoardClonedTest);
    }

    @Test
    public void SudokuBoardEraseTestEasy() {
        int numberOfErasedFields = 0;
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest.solveGame();
        ;
        Difficulty easy = Difficulty.EASY;
        sudokuBoardTest.eraseFields(easy);
        for (int i = 0; i < sudokuBoardTest.getBoard().length; i++) {
            for (int j = 0; j < sudokuBoardTest.getBoard().length; j++) {
                if (sudokuBoardTest.get(i, j) == 0) {
                    numberOfErasedFields += 1;
                }
            }
        }
        Assertions.assertEquals(10, numberOfErasedFields);
    }

    @Test
    public void SudokuBoardEraseTestMedium() {
        int numberOfErasedFields = 0;
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest.solveGame();
        ;
        Difficulty medium = Difficulty.MEDIUM;
        sudokuBoardTest.eraseFields(medium);
        for (int i = 0; i < sudokuBoardTest.getBoard().length; i++) {
            for (int j = 0; j < sudokuBoardTest.getBoard().length; j++) {
                if (sudokuBoardTest.get(i, j) == 0) {
                    numberOfErasedFields += 1;
                }
            }
        }
        Assertions.assertEquals(20, numberOfErasedFields);
    }

    @Test
    public void SudokuBoardEraseTestHard() {
        int numberOfErasedFields = 0;
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest.solveGame();
        ;
        Difficulty hard = Difficulty.HARD;
        sudokuBoardTest.eraseFields(hard);
        for (int i = 0; i < sudokuBoardTest.getBoard().length; i++) {
            for (int j = 0; j < sudokuBoardTest.getBoard().length; j++) {
                if (sudokuBoardTest.get(i, j) == 0) {
                    numberOfErasedFields += 1;
                }
            }
        }
        Assertions.assertEquals(30, numberOfErasedFields);
    }
}