package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import main.finalproject.Main;

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

    public void changeSceneHome(ActionEvent event, String sceneName) throws IOException {
        String scenePath = sceneName + ".fxml";

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root = FXMLLoader.load(Main.class.getResource(scenePath));
        stage.setScene(new Scene(root, 1000, 830));
        stage.show();
    }

    public void changeSceneForCustomer(ActionEvent event, String sceneName, String username) throws IOException {
        String scenePath = sceneName + ".fxml";

        FXMLLoader loader = new FXMLLoader(Main.class.getResource(scenePath));
        Parent root = loader.load();

        ShopController shopController = loader.getController();
        shopController.setUsername(username);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 830));
        stage.show();
    }

    public void changeSceneToAccount(ActionEvent event, String sceneName, String username) throws IOException {
        String scenePath = sceneName + ".fxml";

        FXMLLoader loader = new FXMLLoader(Main.class.getResource(scenePath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 830));
        stage.show();
    }

    public void changeSceneForAdminAndSalesManager(ActionEvent event, String sceneName, String username) throws IOException {

        String scenePath = sceneName + ".fxml";
        Parent root = FXMLLoader.load(Main.class.getResource(scenePath));

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root, 850, 600));
        stage.show();
    }

    public void changeSceneForShop(ActionEvent event, String sceneName) throws IOException {
        String scenePath = sceneName + ".fxml";
        Parent root = FXMLLoader.load(Main.class.getResource(scenePath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 830));
        stage.show();
    }

    public void changeSceneViewOrder(ActionEvent event, String sceneName, String orderNumber) throws IOException {
        String scenePath = sceneName + ".fxml";

        FXMLLoader loader = new FXMLLoader(Main.class.getResource(scenePath));
        Parent root = loader.load();

        OrderController orderController = loader.getController();
        orderController.setOrderNumber(orderNumber);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 830));
        stage.show();
    }

    public void changeSceneSearch(ActionEvent event, String sceneName, String searchName) throws IOException {
        String scenePath = sceneName + ".fxml";

        FXMLLoader loader = new FXMLLoader(Main.class.getResource(scenePath));
        Parent root = loader.load();

        ShopController shopController = loader.getController();
        shopController.search(searchName);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 830));
        stage.show();
    }


}
