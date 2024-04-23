package edu.view;

import edu.model.*;
import edu.view.exceptions.BackToMenuException;
import edu.view.exceptions.FillPaneException;
import edu.view.exceptions.SaveGameException;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class SudokuBoardController  {

    @FXML
    private GridPane sudokuPane;

    protected final List<IntegerProperty> objectProperties = new ArrayList<>();

    private static final Logger logger = LogManager.getLogger(MenuController.class);

    private SudokuBoard currentSudokuBoard;

    public void createBoard(Difficulty difficulty) {
        SudokuBoardFactory sudokuBoardFactory = new SudokuBoardFactory();
        this.currentSudokuBoard = sudokuBoardFactory.createInstance();
        this.currentSudokuBoard.solveGame();
        this.currentSudokuBoard.eraseFields(difficulty);
        ResourceBundle localeBundle = ResourceBundle.getBundle("LocaleBundle");
        logger.debug(localeBundle.getString("boardCreated"));
    }

    public void loadBoard(SudokuBoard board) {
        this.currentSudokuBoard = board;
    }

    public void fillPane() throws FillPaneException {
        int boardWidth = this.currentSudokuBoard.getBoard().length;
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (this.currentSudokuBoard.get(i, j) != 0) {
                    TextField textField = new TextField(Integer.toString(this.currentSudokuBoard.get(i, j)));
                    textField.setEditable(false);
                    textField.setPrefSize(100, 100);
                    textField.setAlignment(Pos.CENTER);
                    textField.setBorder(Border.stroke(Color.LIGHTGREY));
                    this.sudokuPane.add(textField, j, i);
                } else {
                    TextField textField = new TextField();
                    textField.setEditable(true);
                    textField.setPrefSize(100, 100);
                    textField.setAlignment(Pos.CENTER);
                    textField.setBorder(Border.stroke(Color.LIGHTGREY));
                    this.sudokuPane.add(textField, j, i);
                    TextFormatter<Integer> sudokuFieldTextFormatter = new TextFormatter<>(
                            new SudokuFieldValueConverter(), 0, new SudokuFieldValueFilter()
                    );
                    SudokuField sudokuField = this.currentSudokuBoard.getBoard()[i][j];
                    try {
                        ObjectProperty<Integer> sudokuFieldProperty = JavaBeanIntegerPropertyBuilder
                                .create()
                                .bean(sudokuField)
                                .name("fieldValue").build().asObject();
                        objectProperties.add(IntegerProperty.integerProperty(sudokuFieldProperty));
                        textField.setTextFormatter(sudokuFieldTextFormatter);
                        sudokuFieldTextFormatter.valueProperty().bindBidirectional(sudokuFieldProperty);
                        sudokuFieldTextFormatter.valueProperty().addListener(observable -> Platform.runLater(() -> {
                            if (sudokuField.getFieldValue() != sudokuFieldTextFormatter.getValue()) {
                                textField.setBorder(Border.stroke(Color.RED));
                            } else if (sudokuField.getFieldValue() == 0 && sudokuFieldTextFormatter.getValue() == 0) {
                                textField.setBorder(Border.stroke(Color.LIGHTGREY));
                            } else {
                                textField.setBorder(Border.stroke(Color.GREEN));
                            }
                        }));
                    } catch (NoSuchMethodException e) {
                        ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
                        throw new FillPaneException(resourceBundle.getString("sudokuFieldPropertyException"), e);
                    }
                }
            }
        }
    }

    @FXML
    protected void saveGame() throws SaveGameException {
        FileChooser saveFileChooser = new FileChooser();
        File selectedSaveFile = saveFileChooser.showSaveDialog(this.sudokuPane.getScene().getWindow());
        if (selectedSaveFile != null) {
            SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
            try (Dao<SudokuBoard> dao = sudokuBoardDaoFactory.getFileDao(selectedSaveFile.getAbsolutePath())) {
                dao.write(this.currentSudokuBoard);
            } catch (Exception e) {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle");
                logger.error(resourceBundle.getString("gameSaveException"));
                throw new SaveGameException(resourceBundle.getString("gameSaveException"), e);
            }
        }
        ResourceBundle localeBundle = ResourceBundle.getBundle("LocaleBundle");
        logger.debug(localeBundle.getString("gameSavedInfo"));
    }

    @FXML
    protected void saveGameDb() throws SaveGameException {
        TextInputDialog textInputDialog = new TextInputDialog();
        ResourceBundle localeBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
        textInputDialog.setTitle(localeBundle.getString("saveGameToDatabase"));
        textInputDialog.setHeaderText(null);
        textInputDialog.setGraphic(null);
        textInputDialog.setContentText(localeBundle.getString("boardName"));
        textInputDialog.showAndWait();
        String boardName = textInputDialog.getResult();
        try (Dao<SudokuBoard> jdbcSudokuBoardDao = new SudokuBoardDaoFactory().getJdbcDao(boardName)) {
            jdbcSudokuBoardDao.write(this.currentSudokuBoard);
        } catch (Exception e) {
            logger.error(localeBundle.getString("gameSaveException"));
            throw new SaveGameException(localeBundle.getString("gameSaveException"), e);
        }
        logger.debug(localeBundle.getString("gameSavedInfo"));
    }

    @FXML
    protected void exit() {
        Platform.exit();
    }

    @FXML
    protected void backToMenu() throws BackToMenuException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader(
                SudokuGameApplication.class.getResource("Menu-view.fxml"), resourceBundle
        );
        try {
            Scene scene = this.sudokuPane.getScene();
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new BackToMenuException(resourceBundle.getString("backToMenuException"), e);
        }
    }
}
