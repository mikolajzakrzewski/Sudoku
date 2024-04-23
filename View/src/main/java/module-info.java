module pl.comp.viewproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;
    requires org.apache.logging.log4j;


    opens edu.view to javafx.fxml;
    exports edu.view;
    exports edu.view.exceptions;
}