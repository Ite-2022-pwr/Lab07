package pl.pwr.ite.customer.service.exception;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class JavaFXException extends RuntimeException {

    public JavaFXException(String message) {
        super(message);
        Platform.runLater(() -> {
            var alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
            alert.showAndWait();
        });
    }

    public JavaFXException(String message, Throwable cause) {
        super(message, cause);
        Platform.runLater(() -> {
            var alert = new Alert(Alert.AlertType.WARNING, message + "\n" + cause.getMessage(), ButtonType.OK);
            alert.showAndWait();
        });
    }
}
