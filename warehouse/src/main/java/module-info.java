module pl.pwr.ite.warehouse {
    requires javafx.controls;
    requires javafx.fxml;
    requires service;
    requires lombok;
    requires model;
    requires shop;

//    exports pl.pwr.ite.warehouse.service;
    opens pl.pwr.ite.warehouse to javafx.fxml;
    exports pl.pwr.ite.warehouse;
    exports pl.pwr.ite.warehouse.view.controller;
    opens pl.pwr.ite.warehouse.view.controller to javafx.fxml;
}