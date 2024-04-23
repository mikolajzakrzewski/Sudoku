package edu.view;

import javafx.util.converter.IntegerStringConverter;

public class SudokuFieldValueConverter extends IntegerStringConverter {
    @Override
    public String toString(Integer value) {
        if (value == 0) {
            return "";
        } else {
            return super.toString(value);
        }
    }

    @Override
    public Integer fromString(String s) {
        if (s.isEmpty()) {
            return 0;
        } else {
            return super.fromString(s);
        }
    }
}
