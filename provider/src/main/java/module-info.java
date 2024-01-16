module pl.pwr.ite.provider {
    requires javafx.controls;
    requires javafx.fxml;
    requires service;
    requires model;
    requires shop;


    opens pl.pwr.ite.provider to javafx.fxml;
    exports pl.pwr.ite.provider;
    exports pl.pwr.ite.provider.service;
    exports pl.pwr.ite.provider.view.controller;
    opens pl.pwr.ite.provider.view.controller to javafx.fxml;
}