package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import service.ShopService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PaymentController extends ViewController implements Initializable {
    @FXML
    private TextField searchTextField;
    @FXML
    private JFXButton searchButton;
    @FXML
    private JFXButton basketButton;
    @FXML
    private JFXButton homeButton;
    @FXML
    private JFXButton accountButton;
    @FXML
    private JFXButton buttonLogOut;
    @FXML
    private Label numberInTheBasket;
    @FXML
    private Label shoppingCartItemsLabel;
    @FXML
    private HBox hbox;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailAddressTextField;
    @FXML
    private TextField mobileNumberTextField;
    @FXML
    private TextField houseFlatNumberTextField;
    @FXML
    private TextField StreetNameTextField;
    @FXML
    private TextField zipCodeTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField notesTextField;
    @FXML
    private JFXButton payButton;
    @FXML
    private TextField cardholderTextField;
    @FXML
    private PasswordField cardNUmberTextField;
    @FXML
    private JFXComboBox monthComboBox;
    @FXML
    private JFXComboBox yearComboBox;
    @FXML
    private TextField cvvTextField;
    @FXML
    private Button goBackShoppingButton;
    @FXML
    private Pane thankYouPagePane;
    @FXML
    private VBox deliveryPaymentsWindowVBox;
    private ShopService shopService = new ShopService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setComboBoxValues();

        showBasketSize();

        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            //action happens after click on it
            @Override
            public void handle(ActionEvent event) {
                //if users logged out
                try {
                    shopService.clearBasket();
                    changeScene(event, "login");
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        basketButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneHome(event, "shoppingCart");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneHome(event, "shop");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        goBackShoppingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneHome(event, "shoppingCart");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        accountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        payButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deliveryPaymentsWindowVBox.setVisible(false);
                thankYouPagePane.setVisible(true);
            }
        });
    }

    private void setComboBoxValues() {
        monthComboBox.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", 10, 11, 12);
        yearComboBox.getItems().addAll(2021, 2022, 2023, 2024);
    }

    public void showBasketSize() {
        try {
            int basketSize = shopService.getShoppingCartSize();
            if (basketSize == 0) {
                numberInTheBasket.setVisible(false);
            } else {
                numberInTheBasket.setVisible(true);
                numberInTheBasket.setText(String.valueOf(basketSize));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
