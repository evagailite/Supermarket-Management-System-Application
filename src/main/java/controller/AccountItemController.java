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
import java.util.ArrayList;
import java.util.List;
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
            double totalPrice = shopService.getSubTotal() * 1.21;
            orderNumberLabel.setText(sale.getOrderNumber());
            dateLabel.setText(String.valueOf(sale.getOrderDate()));
            totalPriceLabel.setText(df.format(totalPrice));

            viewOrderButton.setOnAction(event -> {
                try {
                    changeSceneForShop(event, "order");
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
