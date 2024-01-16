package pl.pwr.ite.customer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.pwr.ite.customer.view.controller.CustomerController;
import pl.pwr.ite.service.javafx.CommunicationController;

import java.io.IOException;

public class CustomerApplication extends Application {

    private FXMLLoader fxmlLoader;
    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(CustomerApplication.class.getResource("customer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 800);
        stage.setTitle("Customer");
        fxmlLoader.getController();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        var controller = (CustomerController) fxmlLoader.getController();
//        controller.stop();
    }
}