package controller;

import com.jfoenix.controls.JFXButton;
import database.DBHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Product;
import service.ShopService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.PrimitiveIterator;
import java.util.ResourceBundle;

public class ShoppingCartItemController implements Initializable {
    @FXML
    private ImageView productImageBasketImage;
    @FXML
    private Label productNameBasketLabel;
    @FXML
    private Label quantityNumberLabel;
    @FXML
    private ImageView decreaseButton;
    @FXML
    private ImageView increaseButton;
    @FXML
    private Label priceBasketLabel;
    @FXML
    private Button removeFromBasketButton;
    private ShopService shopService = new ShopService();
    private Connection connection = DBHandler.getConnection();


    public void setShoppingCartData(Product product) {
        productNameBasketLabel.setText(product.getName());
        priceBasketLabel.setText(ItemController.CURRENCY + product.getPricePerUnit());
        Image image = new Image("file:///C:/Users/Eva/Dropbox/Programming/AccentureBootcamp2021/projects/finalProject/src/main/resources/main/finalproject/images/shop/"
                + product.getImage());
        productImageBasketImage.setImage(image);

        removeFromBasketButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    shopService.removeProductFromShoppingBasket(product.getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
