module ru.nsu.gemuev.net {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.guice;
    requires com.google.common;

    requires java.net.http;
    requires lombok;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires org.apache.logging.log4j;

    //exports ru.nsu.gemuev.net3 to com.fasterxml.jackson.databind;
    opens ru.nsu.gemuev.net3 to javafx.fxml;
    exports ru.nsu.gemuev.net3;
    opens ru.nsu.gemuev.net3.net;
    exports ru.nsu.gemuev.net3.controllers.events;
    exports ru.nsu.gemuev.net3.controllers;
    opens ru.nsu.gemuev.net3.controllers to javafx.fxml;
    opens ru.nsu.gemuev.net3.util;
    exports ru.nsu.gemuev.net3.util;
    exports ru.nsu.gemuev.net3.model.entities;
    opens ru.nsu.gemuev.net3.model.entities;
    exports ru.nsu.gemuev.net3.model.usecases;
    opens ru.nsu.gemuev.net3.model.usecases;
    exports ru.nsu.gemuev.net3.model.ports;
    opens ru.nsu.gemuev.net3.model.ports;
}