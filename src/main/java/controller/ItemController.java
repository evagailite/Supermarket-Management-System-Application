package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Product;
import service.ShopService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    @FXML
    private ImageView productImage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private JFXButton addToCart;
    @FXML
    private Button decrementButton;
    @FXML
    private Label quantityNumberLabel;
    @FXML
    private Button incrementButton;
    public static final String CURRENCY = "$";
    public ShopService shopService = new ShopService();

    public void setData(Product product) {
        nameLabel.setText(product.getName());
        priceLabel.setText(CURRENCY + product.getPricePerUnit());
        Image image = new Image("file:///C:/Users/Eva/Dropbox/Programming/AccentureBootcamp2021/projects/finalProject/src/main/resources/main/finalproject/images/shop/" + product.getImage());
        productImage.setImage(image);
        addToCart.setOnAction(event -> {
            try {
                shopService.addProductInTheBasket(product.getName(), 1,
                        product.getPricePerUnit(), product.getImage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            addToCart.setVisible(false);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
