module pl.pwr.ite.customer {
    requires javafx.controls;
    requires javafx.fxml;
    requires service;
    requires lombok;
    requires model;
    requires java.rmi;
    requires shop;


    opens pl.pwr.ite.customer to javafx.fxml, javafx.graphics;
    exports pl.pwr.ite.customer.view.controller;
    opens pl.pwr.ite.customer.view.controller to javafx.fxml;
}