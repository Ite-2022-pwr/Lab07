module service {
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires lombok;
    requires model;
    requires java.rmi;
    requires shop;
    exports pl.pwr.ite.service;
    exports pl.pwr.ite.service.rmi;
}
