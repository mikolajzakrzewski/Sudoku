package edu.view;

import edu.view.exceptions.ApplicationLaunchException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SudokuGameApplication extends Application {

    private static final Logger logger = LogManager.getLogger(MenuController.class);

    @Override
    public void start(Stage stage) throws ApplicationLaunchException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("LocaleBundle", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader(
                SudokuGameApplication.class.getResource("Menu-view.fxml"), resourceBundle
        );
        try {
            Scene scene = new Scene(fxmlLoader.load(), 500, 528);
            stage.setScene(scene);
            stage.show();
            logger.debug(resourceBundle.getString("menuOpened"));
            logger.info(resourceBundle.getString("chosenLanguage") + resourceBundle.getString("englishVersion"));
        } catch (IOException e) {
            throw new ApplicationLaunchException(resourceBundle.getString("applicationStartException"), e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}