package edu.model;

import edu.model.exceptions.CloneException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.*;

public class SudokuBoard implements PropertyChangeListener, Serializable, Cloneable {
    private SudokuField[][] board = new SudokuField[9][9];

    private static final Logger logger = LogManager.getLogger(SudokuField.class);

    private final SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver sudokuSolverConstructor) {
        this.sudokuSolver = sudokuSolverConstructor;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                this.board[i][j] = new SudokuField();
                this.board[i][j].addPropertyChangeListener(this);
            }
        }
    }

    public void eraseFields(Difficulty difficulty) {
        ArrayList<Integer> excludedBoxes = new ArrayList<>();
        int column;
        int row;
        int boxNumber;
        int boardWidth = this.getBoard().length;
        int boxWidth = (int) Math.sqrt(boardWidth);
        Random random = new Random();
        int numbersToRemove = difficulty.getNumberOfCellsToRemove();
        do {
            for (int i = 0; i < boardWidth; i += boxWidth) {
                for (int j = 0; j < boardWidth; j += boxWidth) {
                    do {
                        row = random.nextInt(boxWidth) + i;
                        column = random.nextInt(boxWidth) + j;
                    } while (this.get(row, column) == 0);
                    this.set(row, column, 0);
                    numbersToRemove -= 1;
                }
            }
        } while (numbersToRemove > boxWidth);
        do {
            do {
                row = random.nextInt(boardWidth - 1);
                column = random.nextInt(boardWidth - 1);
                boxNumber = row / boxWidth * boxWidth + column / boxWidth;
            } while (this.get(row, column) == 0 || excludedBoxes.contains(boxNumber));
            this.set(row, column, 0);
            excludedBoxes.add(boxNumber);
            numbersToRemove -= 1;
        } while (numbersToRemove > 0);
    }

    public int get(int x, int y) {
        return this.board[x][y].getFieldValue();
    }

    public void set(int x, int y, int value) {
        this.board[x][y].setFieldValue(value);
    }

    public SudokuField[][] getBoard() {
        return this.board;
    }

    private boolean checkBoard() {
        int boxLength = (int) Math.sqrt(this.board.length);
        for (int i = 0; i < this.board.length; i++) {
            if (!getRow(i).verify()) {
                return false;
            } else if (!getColumn(i).verify()) {
                return false;
            }
            if (i % boxLength == 0) {
                for (int j = 0; j < this.board.length; j += boxLength) {
                    if (!getBox(i, j).verify()) {
                        return false;
                    }
                }
            }
        }
       return true;
    }

    public SudokuRow getRow(int y) {
        List<SudokuField> fields = Arrays.asList(new SudokuField[this.board.length]);
        for (int column = 0; column < fields.size(); column++) {
            fields.set(column, new SudokuField(this.get(y, column)));
        }
        return new SudokuRow(fields);
    }

    public SudokuColumn getColumn(int x) {
        List<SudokuField> fields = Arrays.asList(new SudokuField[this.board.length]);
        for (int row = 0; row < fields.size(); row++) {
            fields.set(row, new SudokuField(this.get(row, x)));
        }
        return new SudokuColumn(fields);
    }

    public SudokuBox getBox(int x, int y) {
        int boardLength = this.board.length;
        int boxLength = (int) Math.sqrt(boardLength);
        List<SudokuField> fields = Arrays.asList(new SudokuField[boardLength]);
        int i = x % boxLength;
        int j = y % boxLength;
        for (int row = x - i; row < x - i + boxLength; row++) {
            for (int column = y - j; column < y - j + boxLength; column++) {
                int index = (row % boxLength) * boxLength + column % boxLength;
                fields.set(index, new SudokuField(this.get(row, column)));
            }
        }
        return new SudokuBox(fields);
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SudokuField changedField = (SudokuField) evt.getSource();
        int oldFieldValue = (int) evt.getOldValue();
        if (!checkBoard()) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle");
            logger.info(resourceBundle.getString("SudokuFieldWrongValue"));
            changedField.setFieldValue(oldFieldValue);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("board", this.board)
                .append("sudokuSolver", this.sudokuSolver)
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
        SudokuBoard rhs = (SudokuBoard) obj;
        return new EqualsBuilder()
                .append(this.board, rhs.board)
                .append(this.sudokuSolver, rhs.sudokuSolver)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19,39)
                .append(this.board)
                .append(this.sudokuSolver)
                .toHashCode();
    }

    @Override
    public SudokuBoard clone() {
            try {
                SudokuBoard sudokuBoardClone = (SudokuBoard) super.clone();
                sudokuBoardClone.board = this.board.clone();
                for (int i = 0; i < sudokuBoardClone.board.length; i++) {
                    sudokuBoardClone.board[i] = this.board[i].clone();
                    for (int j = 0; j < sudokuBoardClone.board.length; j++) {
                        sudokuBoardClone.board[i][j] = this.board[i][j].clone();
                        sudokuBoardClone.board[i][j].addPropertyChangeListener(sudokuBoardClone);
                    }
                }
                return sudokuBoardClone;
            } catch (CloneNotSupportedException e) {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle");
                logger.error(resourceBundle.getString("CloneException"));
                throw new CloneException(resourceBundle.getString("CloneException"), e);
            }
    }
}
