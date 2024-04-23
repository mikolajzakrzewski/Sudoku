package edu.model;

public class SudokuBoardFactory {
    private static final SudokuBoard sudokuBoardPrototypeEmpty = new SudokuBoard(new BacktrackingSudokuSolver());

    public SudokuBoard createInstance() {
        return sudokuBoardPrototypeEmpty.clone();
    }
}
