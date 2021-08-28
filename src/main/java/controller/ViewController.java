package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import main.finalproject.Main;
import service.UserService;

import java.io.IOException;

public class ViewController {

    public void changeScene(ActionEvent event, String sceneName) throws IOException {
        String scenePath = sceneName + ".fxml";

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root = FXMLLoader.load(Main.class.getResource(scenePath));
        stage.setScene(new Scene(root, 793, 600));
        stage.show();
    }

    public void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }

    public void changeSceneForCustomer(ActionEvent event, String sceneName, String username, double retrievedBudget) throws IOException {
        String scenePath = sceneName + ".fxml";
        Parent root = FXMLLoader.load(Main.class.getResource(scenePath));

//        LoggedInController loggedInController = loader.getController();
//        loggedInController.setUserInformationForCustomer(username, retrievedBudget);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setTitle(title);
        stage.setScene(new Scene(root, 793, 600));
        stage.show();
    }

    public void changeSceneForAdminAndSalesManager(ActionEvent event, String sceneName, String username) throws IOException {
        String scenePath = sceneName + ".fxml";
        Parent root = FXMLLoader.load(Main.class.getResource(scenePath));

//        LoggedInController loggedInController = loader.getController();
//        loggedInController.setUserInformation(username);

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root, 850, 600));
        stage.show();
    }


}
