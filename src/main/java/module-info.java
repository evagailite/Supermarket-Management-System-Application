module main.finalproject {
    requires javafx.controls;
    requires java.base;
    requires javafx.fxml;
    requires java.sql;
    requires commons.configuration;
    requires com.jfoenix;
    requires lombok;
    requires java.desktop;


    opens main.finalproject to javafx.fxml, javafx.base;
    opens controller to javafx.fxml;
    opens model to javafx.base;
    exports main.finalproject;
    exports controller;

}