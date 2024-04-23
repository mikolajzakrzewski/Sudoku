package edu.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver {

    @Override
    public void solve(SudokuBoard board) {
        Random number = new Random();
        int boardLength = board.getBoard().length;
        int randomNumber;
        ArrayList<Integer> excludedNumbers = new ArrayList<>();
        for (int row = 0; row < boardLength; row++) {
            for (int column = 0; column < boardLength; column++) {
                if (board.get(row, column) == 0) {
                    if (!verifyOptions(row, column, board)) {
                        return;
                    }   else    {
                        while (true) {
                            do {
                                randomNumber = number.nextInt(boardLength) + 1;
                            } while (excludedNumbers.contains(randomNumber));
                            if (checkAll(row, column, randomNumber, board)) {
                                board.set(row, column, randomNumber);
                                solve(board);
                                if (boardCheck(board)) {
                                    return;
                                }
                                excludedNumbers.add(board.get(row, column));
                                Collections.sort(excludedNumbers);
                                board.set(row, column, 0);
                                if (excludedNumbers.equals(returnAllPossibleOptions(row, column, board))) {
                                    excludedNumbers.clear();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean rowCheck(int rowNumber, int numberToCheck, SudokuBoard board) {
        for (int column = 0; column < board.getBoard().length; column++) {
            if (board.get(rowNumber, column) == numberToCheck) {
                return false;
            }
        }
        return true;
    }

    private boolean columnCheck(int columnNumber, int numberToCheck, SudokuBoard board) {
        for (int row = 0; row < board.getBoard().length; row++) {
            if (board.get(row, columnNumber) == numberToCheck) {
                return false;
            }
        }
        return true;
    }

    private boolean boxCheck(int rowNumber, int columnNumber, int numberToCheck, SudokuBoard board) {
        int boxLength = (int) Math.sqrt(board.getBoard().length);
        int i = rowNumber % boxLength;
        int j = columnNumber % boxLength;
        for (int row = rowNumber - i; row < rowNumber + boxLength - i; row++) {
            for (int column = columnNumber - j; column < columnNumber + boxLength - j; column++) {
                if (board.get(row, column) == numberToCheck) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkAll(int rowNumber, int columnNumber, int numberToCheck, SudokuBoard board) {
        if (!rowCheck(rowNumber, numberToCheck, board)) {
            return false;
        } else if (!columnCheck(columnNumber, numberToCheck, board)) {
            return false;
        } else {
            return boxCheck(rowNumber, columnNumber, numberToCheck, board);
        }
    }

    private boolean boardCheck(SudokuBoard board) {
        int boardWidth = board.getBoard().length;
        for (int row = 0; row < boardWidth; row++) {
            for (int column = 0; column < boardWidth; column++) {
                if (board.get(row, column) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean verifyOptions(int rowNumber, int columnNumber, SudokuBoard board) {
        for (int i = 1; i < board.getBoard().length + 1; i++) {
            if (this.checkAll(rowNumber, columnNumber, i, board)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Integer> returnAllPossibleOptions(int rowNumber, int columnNumber, SudokuBoard board) {
        ArrayList<Integer> options = new ArrayList<>();
        for (int i = 1; i < board.getBoard().length + 1; i++) {
            if (this.checkAll(rowNumber, columnNumber, i, board)) {
                options.add(i);
            }
        }
        Collections.sort(options);
        return options;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        return new EqualsBuilder()
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .toHashCode();
    }
}
