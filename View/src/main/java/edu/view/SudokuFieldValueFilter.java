package edu.view;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class SudokuFieldValueFilter implements UnaryOperator<TextFormatter.Change> {
    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        if (change.getControlNewText().matches("[1-9]")) {
            return change;
        } else if (change.getText().matches(" ")) {
            change.setAnchor(0);
            change.setText("");
            return change;
        } else {
            return null;
        }
    }
}
