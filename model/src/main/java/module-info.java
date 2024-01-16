module model {
    exports pl.pwr.ite.model;
    exports pl.pwr.ite.model.remote;
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires shop;

    opens pl.pwr.ite.model.remote;
    opens pl.pwr.ite.model.enums;
    exports pl.pwr.ite.model.enums;
}