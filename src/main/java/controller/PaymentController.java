package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Delivery;
import model.Product;
import service.SaleService;
import service.ShopService;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
    @FXML
    private Label orderNumberLabel;
    @FXML
    private Label subtotalLabel;
    @FXML
    private Label shippingLabel;
    @FXML
    private Label taxRateLabel;
    @FXML
    private AnchorPane paymentAnchorPane;
    @FXML
    private Label totalLabel;
    @FXML
    private AnchorPane deliveryAnchorPane;
    @FXML
    private AnchorPane goBackAnchorPane;
    @FXML
    private Button returnToDeliveryButton;
    @FXML
    private Button goNextToPayButton;
    private SaleService saleService = new SaleService();
    private ShopService shopService = new ShopService();
    private UserService userService = new UserService();
    private final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getOrderSummaryDetails();

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
                    String username = userService.getOnlineUser("TRUE");
                    userService.setUserIsOnlineStatus("FALSE", username);
                    shopService.clearBasket();
                    changeScene(event, "login");
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        goNextToPayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deliveryAnchorPane.setVisible(false);
                goBackAnchorPane.setVisible(false);
                paymentAnchorPane.setVisible(true);
            }
        });

        returnToDeliveryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goBackAnchorPane.setVisible(true);
                paymentAnchorPane.setVisible(false);
                deliveryAnchorPane.setVisible(true);
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
                createDeliveryDetails();

                try {
                    shopService.clearBasket();
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

    private void getOrderSummaryDetails() {
        try {
            double tax = 1.21;
            shippingLabel.setText("FREE");
            double subTotal = shopService.getSubTotal();
            double total = subTotal * tax;
            subtotalLabel.setText("$" + df.format(Math.round(subTotal * 100) / 100D));
            double totalPrice = Math.round(total * 100) / 100D;
            totalLabel.setText("$" + df.format(totalPrice));
            double taxValue = Math.round((totalPrice - subTotal) * 100) / 100D;
            taxRateLabel.setText("$" + taxValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDeliveryDetails() {
        try {
            Delivery delivery = setDeliveryDetails();
            saleService.createDelivery(delivery, getShopUser(), getOrderNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getOrderNumber() throws SQLException {
        return saleService.getLastOrderNumber() + 1;
    }

    private String getShopUser() throws SQLException {
        return userService.getOnlineUser("TRUE");
    }

    private void createOrder() {
        try {
            int orderNumber = getOrderNumber();
            List<Product> basket = shopService.getAllShoppingBasketProducts();
            for (Product product : basket) {
                saleService.createSale(orderNumber, product.getName(), product.getQuantity(), getShopUser(),
                        getCurrentDate(), product.getPricePerUnit(), product.getImage());
                orderNumberLabel.setText("#" + orderNumber);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentDate() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH);
        String date = dateFormat.format(new Date());
//        Date date = new Date();
        return date;
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
