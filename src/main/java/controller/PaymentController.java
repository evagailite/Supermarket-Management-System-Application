package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import service.*;

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
    private TextField cardNumberTextField;
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
    @FXML
    private JFXButton viewOrdersButton;
    private SaleService saleService = new SaleService();
    private ShopService shopService = new ShopService();
    private UserService userService = new UserService();
    private ProductService productService = new ProductService();
    private DeliveryService deliveryService = new DeliveryService();
    private final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getOrderSummaryDetails();

        setComboBoxValues();

        showBasketSize();

        disableGoPayButtonWhileTextFieldsIsEmpty();

        disablePayButtonWhileTextFieldsIsEmpty();

        formatCvvField();

        formatCardNumberField();
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String userSearch = searchTextField.getText();
                    changeSceneSearch(event, "shop", userSearch);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            //action happens after click on it
            @Override
            public void handle(ActionEvent event) {
                shoppingCartItemsLabel.setVisible(true);
                confirmationLabel.setVisible(false);
                //if users logged out
                resetWarehouseQuantity();
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
                showBasketSize();
                numberInTheBasket.setVisible(false);

                try {
                    shopService.clearBasket();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                deliveryPaymentsWindowVBox.setVisible(false);
                thankYouPagePane.setVisible(true);

                viewOrdersButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            changeSceneHome(event, "account");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

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

    private void formatCvvField() {
        cvvTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("[\\d*]")) {
                    cvvTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private void formatCardNumberField() {
        cardNumberTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("[\\d*]")) {
                    cardNumberTextField.setText(newValue.replaceAll("([^\\d])", ""));
                }
            }
        });
    }


    private void disablePayButtonWhileTextFieldsIsEmpty() {
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(cardholderTextField.textProperty(), cardNumberTextField.textProperty(), monthComboBox.valueProperty(),
                        yearComboBox.valueProperty(), cvvTextField.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (cardNumberTextField.getText().isEmpty() || cardNumberTextField.getText().isEmpty() ||
                        cvvTextField.getText().isEmpty() || yearComboBox.getSelectionModel().isEmpty() ||
                        monthComboBox.getSelectionModel().isEmpty());
            }
        };

        payButton.disableProperty().bind(bb);
    }

    private void disableGoPayButtonWhileTextFieldsIsEmpty() {
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(firstNameTextField.textProperty(), lastNameTextField.textProperty(), emailAddressTextField.textProperty(),
                        mobileNumberTextField.textProperty(), houseFlatNumberTextField.textProperty(), streetNameTextField.textProperty(),
                        zipCodeTextField.textProperty(), cityTextField.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty() ||
                        emailAddressTextField.getText().isEmpty() || mobileNumberTextField.getText().isEmpty() ||
                        houseFlatNumberTextField.getText().isEmpty() || streetNameTextField.getText().isEmpty() ||
                        zipCodeTextField.getText().isEmpty() || cityTextField.getText().isEmpty());
            }
        };

        goNextToPayButton.disableProperty().bind(bb);
    }

    private void resetWarehouseQuantity() {
        if (!checkIfBasketIsEmpty()) {
            try {
                List<Product> productsInBasket = shopService.getAllShoppingBasketProducts();
                for (Product product : productsInBasket) {
                    int basketQty = (int) shopService.getQuantity(product.getName());
                    int warehouseQty = productService.productQuantity(product.getName());
                    int totalQuantity = warehouseQty + basketQty;
                    productService.editProductQuantity(product.getName(), totalQuantity);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkIfBasketIsEmpty() {
        try {
            int amountOfProductsInBasket = shopService.getShoppingCartSize();
            if (amountOfProductsInBasket == 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void getOrderSummaryDetails() {
        try {
            double tax = 1.21;
            shippingLabel.setText("FREE");
            double subTotal = shopService.getSubTotal();
            double total = subTotal * tax;
            subtotalLabel.setText("$" + String.format(Locale.ENGLISH, "%.2f", Math.round(subTotal * 100) / 100D));
            double totalPrice = Math.round(total * 100) / 100D;
            totalLabel.setText("$" + String.format(Locale.ENGLISH, "%.2f", totalPrice));
            double taxValue = Math.round((totalPrice - subTotal) * 100) / 100D;
            taxRateLabel.setText("$" + String.format(Locale.ENGLISH, "%.2f", taxValue));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void createDeliveryDetails() {
        try {
            String username = userService.getOnlineUser("TRUE");
            int orderNumber = saleService.getOrderNumber(username);
            Delivery delivery = setDeliveryDetails();
            deliveryService.createDelivery(delivery, getShopUser(), orderNumber);
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


    private String getCurrentDate() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH);
        String date = dateFormat.format(new Date());
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
