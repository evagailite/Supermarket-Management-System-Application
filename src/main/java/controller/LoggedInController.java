package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {
    UserService userService = new UserService();
    @FXML
    private Button buttonLogOut;
    //    @FXML
//    private Label label_welcome;
    @FXML
    private Label labelToTheSupermarket;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            //action happens after click on it
            @Override
            public void handle(ActionEvent event) {
                //if users logged out
                try {
                    userService.changeScene(event, "login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setUserInformationForCustomer(String username, double budget) {
        //   label_welcome.setText("Welcome " + username + "!");
        labelToTheSupermarket.setText("You have " + budget + " in your Digital Wallet");
    }

    public void setUserInformation(String username) {

    }
}
