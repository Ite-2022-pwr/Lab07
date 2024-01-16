package pl.pwr.ite.provider;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.pwr.ite.provider.view.controller.ProviderController;

import java.io.IOException;

public class ProviderApplication extends Application {

    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(ProviderApplication.class.getResource("provider-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 800);
        stage.setTitle("Provider");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        var controller = (ProviderController) fxmlLoader.getController();
//        controller.stop();
    }
}