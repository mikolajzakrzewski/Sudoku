package edu.view;

import edu.model.*;
import edu.view.exceptions.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController {
    @FXML
    private Label chooseDifficultyLabel;

    private static final Logger logger = LogManager.getLogger(MenuController.class);

    @FXML
    protected void onPlButtonClick() throws GameReloadException {
        Locale localePL = new Locale.Builder()
                .setLanguage("pl")
                .setRegion("PL").build();
        Locale.setDefault(localePL);
        this.reload();
        ResourceBundle bundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
        logger.info(bundle.getString("chosenLanguage") + bundle.getString("polishVersion"));
    }

    @FXML
    protected void onEnButtonClick() throws GameReloadException {
        Locale localeUS = new Locale.Builder()
                .setLanguage("en")
                .setRegion("PL").build();
        Locale.setDefault(localeUS);
        this.reload();
        ResourceBundle bundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
        logger.info(bundle.getString("chosenLanguage") + bundle.getString("englishVersion"));
    }

    @FXML
    protected void onEasyButtonClick() throws GameStartException {
        this.startGame(Difficulty.EASY);
        ResourceBundle localeBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
        logger.info(localeBundle.getString("chosenDifficulty") + localeBundle.getString("easy"));
    }

    @FXML
    protected void onMediumButtonClick() throws GameStartException {
        this.startGame(Difficulty.MEDIUM);
        ResourceBundle localeBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
        logger.info(localeBundle.getString("chosenDifficulty") + localeBundle.getString("medium"));
    }

    @FXML
    protected void onHardButtonClick() throws GameStartException {
        this.startGame(Difficulty.HARD);
        ResourceBundle localeBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
        logger.info(localeBundle.getString("chosenDifficulty") + localeBundle.getString("hard"));
    }

    private void reload() throws GameReloadException {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
            Scene scene = this.chooseDifficultyLabel.getScene();
            scene.setRoot(FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("Menu-view.fxml")),resourceBundle)
            );
        } catch (Exception e) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle");
            logger.error(resourceBundle.getString("reloadException"));
            throw new GameReloadException(resourceBundle.getString("reloadException"), e);
        }
    }

    private void startGame(Difficulty difficulty) throws GameStartException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SudokuBoard-view.fxml"), resourceBundle);
        try {
            Parent root = fxmlLoader.load();
            SudokuBoardController controller = fxmlLoader.getController();
            controller.createBoard(difficulty);
            controller.fillPane();
            this.chooseDifficultyLabel.getScene().setRoot(root);
        } catch (IOException e) {
            throw new GameStartException(resourceBundle.getString("gameStartException"), e);
        }
    }

    @FXML
    protected void loadGame() throws GameLoadException {
        FileChooser loadFileChooser = new FileChooser();
        File selectedLoadFile = loadFileChooser.showOpenDialog(this.chooseDifficultyLabel.getScene().getWindow());
        if (selectedLoadFile != null) {
            SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
            try (Dao<SudokuBoard> dao = sudokuBoardDaoFactory.getFileDao(selectedLoadFile.getAbsolutePath())) {
                SudokuBoard savedSudokuBoard = dao.read();
                ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SudokuBoard-view.fxml"), resourceBundle);
                Parent root = fxmlLoader.load();
                SudokuBoardController controller = fxmlLoader.getController();
                controller.loadBoard(savedSudokuBoard);
                controller.fillPane();
                this.chooseDifficultyLabel.getScene().setRoot(root);
            } catch (Exception e) {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle");
                logger.error(resourceBundle.getString("gameLoadException"));
                throw new GameLoadException(resourceBundle.getString("gameLoadException"), e);
            }
            ResourceBundle localeBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
            logger.debug(localeBundle.getString("gameLoadedInfo"));
        }
    }

    @FXML
    protected void loadGameDb() throws GameLoadException {
        TextInputDialog textInputDialog = new TextInputDialog();
        ResourceBundle localeBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
        textInputDialog.setTitle(localeBundle.getString("loadGameFromDatabase"));
        textInputDialog.setHeaderText(null);
        textInputDialog.setGraphic(null);
        textInputDialog.setContentText(localeBundle.getString("boardName"));
        textInputDialog.showAndWait();
        String boardName = textInputDialog.getResult();
        try (Dao<SudokuBoard> jdbcSudokuBoardDao = new SudokuBoardDaoFactory().getJdbcDao(boardName)) {
            SudokuBoard savedSudokuBoard = jdbcSudokuBoardDao.read();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SudokuBoard-view.fxml"), localeBundle);
            Parent root = fxmlLoader.load();
            SudokuBoardController controller = fxmlLoader.getController();
            controller.loadBoard(savedSudokuBoard);
            controller.fillPane();
            this.chooseDifficultyLabel.getScene().setRoot(root);
        } catch (Exception e) {
            logger.error(localeBundle.getString("gameLoadException"));
            throw new GameLoadException(localeBundle.getString("gameLoadException"), e);
        }
        logger.debug(localeBundle.getString("gameLoadedInfo"));
    }

    @FXML
    protected void exit() {
        Platform.exit();
    }

    @FXML
    protected void displayAuthors() throws DisplayAuthorsException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle");
        ResourceBundle resourceBundleAuthors = ResourceBundle.getBundle("edu.view.AboutResources");
        logger.info(resourceBundle.getString("displayOfAuthors"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("About-view.fxml"), resourceBundleAuthors);
        Stage stage = new Stage();
        try {
            Scene scene = new Scene(fxmlLoader.load(), 200, 200);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new DisplayAuthorsException(resourceBundle.getString("authorsDisplayException"), e);
        }
    }
}