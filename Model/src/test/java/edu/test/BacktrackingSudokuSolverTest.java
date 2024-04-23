package edu.test;

import edu.model.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Vector;

public class BacktrackingSudokuSolverTest {
    @Test
    public void correctFillTest() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest1 = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest1.solveGame();
        int boardLength = sudokuBoardTest1.getBoard().length;
        int boxLength = (int) Math.sqrt(boardLength);
        Vector<Integer> testVector = new Vector<>();
        for (int number = 1; number < boardLength + 1; number++) {
            testVector.add(number);
        }
        boolean boardFilledCorrectly = true;
        for (int rowNumber = 0; rowNumber < boardLength; rowNumber++) {
            Vector<Integer> rowContents = new Vector<>();
            for (int columnNumber = 0; columnNumber < boardLength; columnNumber++) {
                rowContents.add(sudokuBoardTest1.get(rowNumber, columnNumber));
            }
            Collections.sort(rowContents);
            if (!rowContents.equals(testVector)) {
                boardFilledCorrectly = false;
            }
        }
        for (int columnNumber = 0; columnNumber < boardLength; columnNumber++) {
            Vector<Integer> columnContents = new Vector<>();
            for (int rowNumber = 0; rowNumber < boardLength; rowNumber++) {
                columnContents.add(sudokuBoardTest1.get(rowNumber, columnNumber));
            }
            Collections.sort(columnContents);
            if (!columnContents.equals(testVector)) {
                boardFilledCorrectly = false;
            }
        }
        int rowModifier = 0;
        int columnModifier = 0;
        for (int boxNumber = 0; boxNumber < boardLength; boxNumber++) {
            Vector<Integer> boxContents = new Vector<>();
            for (int rowNumber = rowModifier; rowNumber < boxLength + rowModifier; rowNumber++) {
                for (int columnNumber = columnModifier; columnNumber < boxLength + columnModifier; columnNumber++) {
                    boxContents.add(sudokuBoardTest1.get(rowNumber, columnNumber));
                }
            }
            Collections.sort(boxContents);
            if (!boxContents.equals(testVector)) {
                boardFilledCorrectly = false;
            }
            if (columnModifier == boxLength * (boxLength - 1)) {
                columnModifier = 0;
                rowModifier += boxLength;
            } else {
                columnModifier += boxLength;
            }
        }
        Assertions.assertTrue(boardFilledCorrectly);
    }

    @Test
    public void randomFillTest() {
        SudokuBoardFactory sudokuBoardFactoryTest = new SudokuBoardFactory();
        SudokuBoard sudokuBoardTest1 = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest1.solveGame();
        SudokuBoard sudokuBoardTest2 = sudokuBoardFactoryTest.createInstance();
        sudokuBoardTest2.solveGame();
        SudokuField [][] boardTest1 = sudokuBoardTest1.getBoard();
        SudokuField [][] boardTest2 = sudokuBoardTest2.getBoard();
        boolean areBoardsEqual = true;
        int boardLength = boardTest1.length;
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                if (boardTest1[i][j].getFieldValue() != boardTest2[i][j].getFieldValue()) {
                    areBoardsEqual = false;
                    break;
                }
            }
        }
        Assertions.assertFalse(areBoardsEqual);
    }

    @Test
    public void BacktrackingSudokuSolverToStringTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolverTest1 = new BacktrackingSudokuSolver();
        Assertions.assertEquals("BacktrackingSudokuSolver[]", backtrackingSudokuSolverTest1.toString());
    }

    @Test
    public void BacktrackingSudokuSolverEqualsTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolverTest1 = new BacktrackingSudokuSolver();
        BacktrackingSudokuSolver backtrackingSudokuSolverTest2 = new BacktrackingSudokuSolver();
        Assertions.assertTrue(backtrackingSudokuSolverTest1.equals(backtrackingSudokuSolverTest2));
        Assertions.assertTrue(backtrackingSudokuSolverTest1.equals(backtrackingSudokuSolverTest1));
        Assertions.assertFalse(backtrackingSudokuSolverTest1.equals(null));
        Assertions.assertFalse(backtrackingSudokuSolverTest1.equals(new Object()));
    }

    @Test
    public void BacktrackingSudokuSolverHashTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolverTest1 = new BacktrackingSudokuSolver();
        BacktrackingSudokuSolver backtrackingSudokuSolverTest2 = new BacktrackingSudokuSolver();
        Assertions.assertTrue(backtrackingSudokuSolverTest1.equals(backtrackingSudokuSolverTest2));
        Assertions.assertEquals(backtrackingSudokuSolverTest1.hashCode(), backtrackingSudokuSolverTest2.hashCode());
    }
}