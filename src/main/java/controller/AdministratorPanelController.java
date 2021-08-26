package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Users;
import service.UserService;
import types.UserType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdministratorPanelController implements Initializable {

    UserService userService = new UserService();
    @FXML
    public Pane startPanel;
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
    private TableView<Users> userTable;

    @FXML
    private TableColumn<Users, Integer> columnId;

    @FXML
    private TableColumn<Users, String> columnUsername;

    @FXML
    private TableColumn<Users, String> columnName;

    @FXML
    private TableColumn<Users, String> columnEmail;

    @FXML
    private TableColumn<Users, Double> columnBudget;

    @FXML
    private TableColumn<Users, UserType> columnUserType;

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
            showAllUsers();
        }
    }

    private void showAllUsers() {
        ObservableList<Users> userList;

        columnId.setCellValueFactory(new PropertyValueFactory<Users, Integer>("id"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<Users, String>("username"));
        columnName.setCellValueFactory(new PropertyValueFactory<Users, String>("name"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<Users, String>("email"));
        columnBudget.setCellValueFactory(new PropertyValueFactory<Users, Double>("budget"));
        columnUserType.setCellValueFactory(new PropertyValueFactory<Users, UserType>("userType"));

        try {
            userList = userService.getAllUsers();
            userTable.setItems(userList);
        } catch (SQLException e) {
            e.printStackTrace();
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

        showFirstPage();
    }

    private void showFirstPage() {
        startPanel.toFront();
    }

}
