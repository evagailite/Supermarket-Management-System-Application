package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.finalproject.Main;
import model.Delivery;
import model.Sale;
import service.DeliveryService;
import service.SaleService;
import service.ShopService;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderController extends ViewController implements Initializable {
    @FXML
    private TextField searchTextField;
    @FXML
    private JFXButton searchButton;
    @FXML
    private JFXButton basketButton;
    @FXML
    private JFXButton homeButton;
    @FXML
    private JFXButton buttonLogOut;
    @FXML
    private Label numberInTheBasket;
    @FXML
    private JFXButton accountButton;
    @FXML
    private Label shoppingCartItemsLabel;
    @FXML
    private HBox hbox;
    @FXML
    private VBox vbox;
    @FXML
    private HBox tableNamesForBasket1;
    @FXML
    private VBox orderSummaryVbox;
    @FXML
    private AnchorPane profileAnchorPane;
    @FXML
    private Label totalLabel;
    @FXML
    private Label taxRateLabel;
    @FXML
    private Label shippingLabel;
    @FXML
    private Label subtotalLabel;
    @FXML
    private Label orderNumberDetails;
    @FXML
    private Label deliveryDetailsLabel;
    @FXML
    private Label orderDateLabel;
    @FXML
    private Button goBackShoppingButton;
    private ShopService shopService = new ShopService();
    private UserService userService = new UserService();
    private SaleService saleService = new SaleService();
    private List<Sale> productList = new ArrayList<>();
    private DeliveryService deliveryService = new DeliveryService();
    private final DecimalFormat df = new DecimalFormat("0.00");
    private String orderNumber;

    public List<Sale> getOrderProducts(String orderNumber) {
        List<Sale> saleProducts = null;
        try {
            String username = userService.getOnlineUser("TRUE");
            saleProducts = saleService.getCustomerSalesProductsByOrderNumber(username, orderNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < productList.size(); i++) {
            Sale sales = new Sale();
            sales.setProductName(sales.getProductName());
            sales.setPrice(sales.getPrice());
            sales.setQuantity(sales.getQuantity());
            sales.setImage(sales.getImage());
            saleProducts.add(sales);
        }
        return saleProducts;
    }

    public void loadOrderViewItems(String orderNumber) {
        productList.addAll(getOrderProducts(orderNumber));

        for (int i = 0; i < productList.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("orderItem.fxml"));
                AnchorPane hBox = fxmlLoader.load();
                OrderItemController orderItemController = fxmlLoader.getController();
                orderItemController.setOrderDetails(productList.get(i));
                vbox.getChildren().add(hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            orderNumberDetails.setText(this.orderNumber);
            loadOrderViewItems(this.orderNumber);
            getOrderSummaryDetails(Integer.parseInt(this.orderNumber));

            try {
                StringBuilder stringBuilder = new StringBuilder();
                List<Delivery> delivery = deliveryService.getAllDeliveryDetails(Integer.parseInt(this.orderNumber));
                for (Delivery delivery1 : delivery) {
                    deliveryDetailsLabel.setText(String.valueOf(stringBuilder.append(delivery1.getStreetName()).append(" ").append(delivery1.getHouseFlatNumber()).append("\n").
                            append(delivery1.getCity()).append(" ").append(delivery1.getZipCode())));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
        showBasketSize();

        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            //action happens after click on it
            @Override
            public void handle(ActionEvent event) {
                //if users logged out
                try {
                    String username = userService.getOnlineUser("TRUE");
                    shopService.clearBasket();
                    userService.setUserIsOnlineStatus("FALSE", username);
                    userService.changeScene(event, "login");
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        basketButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneForShop(event, "shoppingCart");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneForShop(event, "shop");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        accountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneForShop(event, "account");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        goBackShoppingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneHome(event, "account");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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


    public void getOrderSummaryDetails(int orderNumber) {
        try {
            double tax = 1.21;
            shippingLabel.setText("FREE");
            double subTotal = saleService.getSaleSubTotal(orderNumber);
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

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
