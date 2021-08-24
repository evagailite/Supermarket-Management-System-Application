package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends ViewController implements Initializable {
    @FXML
    private Button buttonLogIn;
    @FXML
    private Button buttonSignUp;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        buttonLogIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    userService.logInUser(event, usernameTextField.getText(), passwordTextField.getText());
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    userService.changeScene(event, "signUp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}