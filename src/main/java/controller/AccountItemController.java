package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Sale;
import service.ShopService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class AccountItemController extends ViewController implements Initializable {
    @FXML
    private Label orderNumberLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button viewOrderButton;
    private ShopService shopService = new ShopService();
    private final DecimalFormat df = new DecimalFormat("0.00");

    public void setSalesDates(Sale sale) {
        try {
            double totalPrice = shopService.getOrderSubTotal(sale.getOrderNumber()) * 1.21;
            orderNumberLabel.setText(sale.getOrderNumber());
            dateLabel.setText(String.valueOf(sale.getOrderDate()));
            totalPriceLabel.setText("$" + String.format(Locale.ENGLISH, "%.2f", totalPrice));
            viewOrderButton.setOnAction(event -> {
                try {
                    changeSceneViewOrder(event, "order", sale.getOrderNumber());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
