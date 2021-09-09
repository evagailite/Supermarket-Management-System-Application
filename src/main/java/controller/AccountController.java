package controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.finalproject.Main;
import model.Sale;
import model.Users;
import service.SaleService;
import service.ShopService;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AccountController extends ViewController implements Initializable {
    @FXML
    private Label usernameLabel;
    @FXML
    private VBox vbox;
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
    private HBox tableNamesForBasket;
    @FXML
    private VBox orderSummaryVbox;
    @FXML
    private AnchorPane profileAnchorPane;
    @FXML
    private Pane editProfilePane;
    @FXML
    private VBox orderProductsVBox;
    @FXML
    private HBox tableNamesForBasket1;
    @FXML
    private Button goBackShoppingButton;
    private List<Sale> customerSales = new ArrayList<>();
    private SaleService saleService = new SaleService();
    private ShopService shopService = new ShopService();
    private UserService userService = new UserService();

    public List<Sale> getCustomerSales() {
        List<Sale> sales = null;
        try {
            String username = userService.getOnlineUser("TRUE");
            sales = saleService.getCustomerSales(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < customerSales.size(); i++) {
            Sale sale = new Sale();
            sale.setOrderNumber(sale.getOrderNumber());
            sale.setOrderDate(sale.getOrderDate());
            sale.setPrice(sale.getPrice());
            sales.add(sale);
        }
        return sales;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadSaleOrders();

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
                    changeSceneHome(event, "shop");
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

    private void loadSaleOrders() {

        customerSales.addAll(getCustomerSales());

        for (int i = 0; i < customerSales.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("accountItem.fxml"));
                AnchorPane hBox = fxmlLoader.load();
                AccountItemController accountItemController = fxmlLoader.getController();
                accountItemController.setSalesDates(customerSales.get(i));
                vbox.getChildren().add(hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUsername(String username) {

    }
}
