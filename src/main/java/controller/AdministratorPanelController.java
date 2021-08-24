package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdministratorPanelController implements Initializable {
    UserService userService = new UserService();
    @FXML
    private Button buttonLoggedOut;

    @FXML
    private JFXButton buttonCreateProduct;

    @FXML
    private JFXButton buttonEditProduct;

    @FXML
    private JFXButton buttonViewProducts;

    @FXML
    private JFXButton buttonSalesReports;

    @FXML
    private JFXButton buttonUsers;

    @FXML
    private Pane panelCreateProduct;

    @FXML
    private Pane panelEditProduct;

    @FXML
    private Pane panelViewProducts;

    @FXML
    private AnchorPane anchorPaneSalesReports;

    @FXML
    private AnchorPane anchorPaneUsers;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == buttonCreateProduct) {
            panelCreateProduct.toFront();
        } else if (event.getSource() == buttonEditProduct) {
            panelEditProduct.toFront();
        } else if (event.getSource() == buttonViewProducts) {
            panelViewProducts.toFront();
        } else if (event.getSource() == buttonSalesReports) {
            anchorPaneSalesReports.toFront();
        } else if (event.getSource() == buttonUsers) {
            anchorPaneUsers.toFront();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonLoggedOut.setOnAction(new EventHandler<ActionEvent>() {
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

    public void setAdminInformation(String username) {
        //  label_welcome_admin.setText("Welcome " + username + "!");
    }
}
