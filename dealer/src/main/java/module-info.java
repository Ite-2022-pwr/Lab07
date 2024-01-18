module pl.pwr.ite.dealer {
    requires javafx.controls;
    requires javafx.fxml;
    requires service;
    requires model;
    requires shop;


    opens pl.pwr.ite.dealer to javafx.fxml;
    exports pl.pwr.ite.dealer;
//    exports pl.pwr.ite.dealer.service;
    exports pl.pwr.ite.dealer.view.controller;
    opens pl.pwr.ite.dealer.view.controller to javafx.fxml;
}