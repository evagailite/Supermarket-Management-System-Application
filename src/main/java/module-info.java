module main.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.configuration;
    requires com.jfoenix;


    opens main.finalproject to javafx.fxml;
    exports main.finalproject;
    exports controller;
    opens controller to javafx.fxml;
}