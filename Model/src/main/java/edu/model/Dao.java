package edu.model;

import edu.model.exceptions.SudokuBoardIoOperationException;

public interface Dao<T> extends AutoCloseable {
    T read() throws SudokuBoardIoOperationException;

    void write(T obj) throws SudokuBoardIoOperationException;
}
