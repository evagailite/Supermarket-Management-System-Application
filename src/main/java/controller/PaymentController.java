package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Delivery;
import service.SaleService;
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
    private TextField streetNameTextField;
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
    @FXML
    private Label confirmationLabel;
    private SaleService saleService = new SaleService();
    private ShopService shopService = new ShopService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setComboBoxValues();

        showBasketSize();

        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            //action happens after click on it
            @Override
            public void handle(ActionEvent event) {

                shoppingCartItemsLabel.setVisible(true);
                confirmationLabel.setVisible(false);
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
                try {
                    changeSceneHome(event, "account");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        payButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                setDeliveryDetails();
                createOrder();

                try {
                    Delivery delivery = setDeliveryDetails();
                    if (delivery != null) {
                        saleService.createDelivery(delivery);
                    } else {
                        setDeliveryDetails();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                deliveryPaymentsWindowVBox.setVisible(false);
                thankYouPagePane.setVisible(true);

                shoppingCartItemsLabel.setVisible(false);
                confirmationLabel.setVisible(true);

                try {
                    shopService.clearBasket();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void createOrder() {

    }

    private Delivery setDeliveryDetails() {
        if (firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty() ||
                emailAddressTextField.getText().isEmpty() || mobileNumberTextField.getText().isEmpty() ||
                houseFlatNumberTextField.getText().isEmpty() || streetNameTextField.getText().isEmpty() ||
                zipCodeTextField.getText().isEmpty() || cityTextField.getText().isEmpty()) {
            showAlert("Error", "Please fill all required fields", Alert.AlertType.ERROR);
        } else {
            Delivery delivery = new Delivery(
                    firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    emailAddressTextField.getText(),
                    mobileNumberTextField.getText(),
                    houseFlatNumberTextField.getText(),
                    streetNameTextField.getText(),
                    zipCodeTextField.getText(),
                    cityTextField.getText(),
                    notesTextField.getText()
            );
            return delivery;
        }
        return null;
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
