package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import main.finalproject.Main;
import model.Product;
import service.ShopService;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShoppingCartController extends ViewController implements Initializable {
    @FXML
    private JFXButton basketButton;
    @FXML
    private JFXButton homeButton;
    @FXML
    private JFXButton accountButton;
    @FXML
    private JFXButton buttonLogOut;
    @FXML
    private VBox vbox;
    @FXML
    private Label numberInTheBasket;
    @FXML
    private HBox tableNamesForBasket;
    @FXML
    private Pane emptyCartPanel;
    @FXML
    private Button goBackShoppingButton;
    @FXML
    private Label shoppingCartItemsLabel;
    @FXML
    private JFXButton checkoutButton;
    @FXML
    private VBox orderSummaryVbox;
    @FXML
    private Label subtotalLabel;
    @FXML
    private Label shippingLabel;
    @FXML
    private Label taxRateLabel;
    @FXML
    private Label totalLabel;
    private ShopService shopService = new ShopService();
    private List<Product> shoppingBasket = new ArrayList<>();
    private final DecimalFormat df = new DecimalFormat("0.00");
    private String shopUser = "test";
    private UserService userService = new UserService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        showBasketSize();

        loadShoppingCartItems();

        getOrderSummaryDetails();

        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            //action happens after click on it
            @Override
            public void handle(ActionEvent event) {
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
                    changeSceneHome(event, "shop");
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

        checkoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneHome(event, "payment");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<Product> getShoppingCartData() {
        List<Product> products = null;
        try {
            products = shopService.getAllShoppingBasketProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < shoppingBasket.size(); i++) {
            Product product = new Product();
            product.setName(product.getName());
            product.setPricePerUnit(product.getPricePerUnit());
            product.setImage("/images/" + product.getImage());
            products.add(product);
        }
        return products;
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

    private void loadShoppingCartItems() {
        shoppingBasket.addAll(getShoppingCartData());

        for (int i = 0; i < shoppingBasket.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("shoppingCartItem.fxml"));
            try {
                AnchorPane hBox = fxmlLoader.load();
                ShoppingCartItemController shoppingCartItemController = fxmlLoader.getController();
                shoppingCartItemController.setShoppingCartData(shoppingBasket.get(i));
                vbox.getChildren().add(hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showBasketSize() {
        try {
            int basketSize = shopService.getShoppingCartSize();
            if (basketSize == 0) {
                numberInTheBasket.setVisible(false);
                shoppingCartItemsLabel.setVisible(false);
                tableNamesForBasket.setVisible(false);
                emptyCartPanel.setVisible(true);
                orderSummaryVbox.setVisible(false);
                emptyCartPanel.toFront();
            } else {
                numberInTheBasket.setVisible(true);
                numberInTheBasket.setText(String.valueOf(basketSize));
                shoppingCartItemsLabel.setVisible(true);
                tableNamesForBasket.setVisible(true);
                emptyCartPanel.toBack();
                orderSummaryVbox.setVisible(true);
                emptyCartPanel.setVisible(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
