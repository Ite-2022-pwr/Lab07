package pl.pwr.ite.dealer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.pwr.ite.dealer.view.controller.DealerController;

import java.io.IOException;

public class DealerApplication extends Application {

    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(DealerApplication.class.getResource("dealer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        var controller = (DealerController) fxmlLoader.getController();
//        controller.stop();
    }
}