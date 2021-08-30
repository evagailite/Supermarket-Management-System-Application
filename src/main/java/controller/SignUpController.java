package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Users;
import service.UserService;
import types.UserType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController extends ViewController implements Initializable {
    @FXML
    public PasswordField passwordConfirmationTextField;
    @FXML
    private Button buttonSignUp;
    @FXML
    private Button buttonLogIn;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;

    UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        buttonLogIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    userService.changeScene(event, "login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (usernameTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || emailTextField.getText().isEmpty() ||
                            passwordTextField.getText().isEmpty() || passwordConfirmationTextField.getText().isEmpty()) {
                        showAlert("Error", "Please fill all fields", Alert.AlertType.ERROR);
                    } else {
                        validateUserInfo(passwordTextField.getText(), passwordConfirmationTextField.getText(), usernameTextField.getText());
                        Users user = new Users(
                                usernameTextField.getText(),
                                nameTextField.getText(),
                                emailTextField.getText(),
                                passwordTextField.getText(),
                                500,
                                UserType.CUSTOMER
                        );
                        userService.signUpUser(user, event);
                    }
                } catch (Exception e) {
                    showAlert("Registration Failed", e.getMessage(), Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            }
        });
    }

    public void validateUserInfo(String password, String passwordConfirmation, String username) throws Exception {
        if (!password.equals(passwordConfirmation))
            throw new Exception("Password does not match, confirm password");
        if (password.length() < 4)
            throw new Exception("Password needs to be minimum 4 characters");
        if (username.length() < 4)
            throw new Exception("Username needs to be minimum 4 characters");
    }

}
