package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Sale;
import service.ShopService;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class OrderItemController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView productImageBasketImage;
    @FXML
    private Label productNameBasketLabel;
    @FXML
    private Label priceBasketLabel;
    @FXML
    private Label quantityNumberLabel;
    @FXML
    private Label totalPriceBasketLabel;
    private ShopService shopService = new ShopService();
    private final DecimalFormat df = new DecimalFormat("0.00");

    public void setOrderDetails(Sale sale) {

        productNameBasketLabel.setText(sale.getProductName());
        priceBasketLabel.setText(String.valueOf(sale.getPrice()));
        quantityNumberLabel.setText(String.valueOf(sale.getQuantity()));
        totalPriceBasketLabel.setText(String.valueOf(sale.getPrice()));
        Image image = new Image("file:///C:/Users/Eva/Dropbox/Programming/AccentureBootcamp2021/projects/finalProject/src/main/resources/main/finalproject/images/shop/"
                + sale.getImage());
        productImageBasketImage.setImage(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
