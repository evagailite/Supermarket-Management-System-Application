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

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShoppingCartItemController implements Initializable {
    @FXML
    private ImageView productImageBasketImage;
    @FXML
    private Label productNameBasketLabel;
    @FXML
    private Label quantityNumberLabel;
    @FXML
    private Button decreaseButton;
    @FXML
    private Button increaseButton;
    @FXML
    private Label priceBasketLabel;
    @FXML
    private Button removeFromBasketButton;
    @FXML
    private AnchorPane anchorPane;
    private ShopService shopService = new ShopService();


    public void setShoppingCartData(Product product) {
        productNameBasketLabel.setText(product.getName());
        priceBasketLabel.setText(ItemController.CURRENCY + product.getPricePerUnit());
        Image image = new Image("file:///C:/Users/Eva/Dropbox/Programming/AccentureBootcamp2021/projects/finalProject/src/main/resources/main/finalproject/images/shop/"
                + product.getImage());
        productImageBasketImage.setImage(image);
        quantityNumberLabel.setText(String.valueOf(1));

        removeFromBasketButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    shopService.removeProductFromShoppingBasket(product.getName());
                    anchorPane.setVisible(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        increaseButton.setOnAction(event1 -> {
            try {
                int value = Integer.parseInt(quantityNumberLabel.getText());
                value = value + 1;
                quantityNumberLabel.setText(String.valueOf(value));
                shopService.addProductInTheBasket(product.getName(), 1,
                        product.getPricePerUnit(), product.getImage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        decreaseButton.setOnAction(event1 -> {
            int value = Integer.parseInt(quantityNumberLabel.getText());
            value--;
            if (value == 0) {
                try {
                    shopService.removeProductFromShoppingBasket(product.getName());
                    anchorPane.setVisible(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    quantityNumberLabel.setText(String.valueOf(value));
                    shopService.addProductInTheBasket(product.getName(), (-1),
                            product.getPricePerUnit(), product.getImage());
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
