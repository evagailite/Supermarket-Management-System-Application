package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private List<Sale> customerSales = new ArrayList<>();
    SaleService saleService = new SaleService();

    public List<Sale> getCustomerSales() {
        List<Sale> sales = null;
        try {
            sales = saleService.getAllCustomerSales();
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


    }

    private void loadSaleOrders() {
        customerSales.addAll(getCustomerSales());

        for (int i = 0; i < customerSales.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("accountItem.fxml"));
            try {
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
        usernameLabel.setText(username);
    }
}
