package controller;

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShoppingCartItemController extends ViewController implements Initializable {
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
        try {
            productNameBasketLabel.setText(product.getName());
            priceBasketLabel.setText(String.valueOf(product.getPricePerUnit()));
            Image image = new Image("file:///C:/Users/Eva/Dropbox/Programming/AccentureBootcamp2021/projects/finalProject/src/main/resources/main/finalproject/images/shop/"
                    + product.getImage());
            productImageBasketImage.setImage(image);
            quantityNumberLabel.setText(String.valueOf(1));

            ArrayList<Product> chosenProducts = shopService.getAllShoppingBasketProducts();
            for (Product product1 : chosenProducts) {
                if (product1.getName().equals(product.getName())) {
                    quantityNumberLabel.setText(String.valueOf((int) product1.getQuantity()));

                    increaseButton.setOnAction(event1 -> {
                        increaseValue(product);
                    });

                    decreaseButton.setOnAction(event1 -> {
                        decreaseValue(product);
                        try {
                            if (checkIfBasketIsEmpty(shopService.getShoppingCartSize())) {
                                changeSceneForShop(event1, "shoppingCart");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }

            removeFromBasketButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        shopService.removeProductFromShoppingBasket(product.getName());
                        anchorPane.setVisible(false);

                        if (checkIfBasketIsEmpty(shopService.getShoppingCartSize())) {
                            changeSceneForShop(event, "shoppingCart");
                        }
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfBasketIsEmpty(int shoppingCartSize) {
        if (shoppingCartSize == 0) {
            return true;
        } else {
            return false;
        }
    }

    private void decreaseValue(Product product) {
        int value = Integer.parseInt(quantityNumberLabel.getText());
        double startPrice = product.getPricePerUnit() / product.getQuantity();
        double pricePerQuantity = (startPrice * value) - startPrice;
        double totalPrice = Math.round(pricePerQuantity * 100) / 100D;
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
                priceBasketLabel.setText("$" + (totalPrice));

                shopService.addProductInTheBasket(product.getName(), (-1),
                        totalPrice, product.getImage());
                shopService.updatePrice(product.getName(), totalPrice);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void increaseValue(Product product) {
        try {
            int value = Integer.parseInt(quantityNumberLabel.getText());
            value++;
            quantityNumberLabel.setText(String.valueOf(value));

            double startPrice = product.getPricePerUnit() / product.getQuantity();
            double pricePerQuantity = startPrice * (value);
            double totalPrice = Math.round(pricePerQuantity * 100) / 100D;
            priceBasketLabel.setText("$" + (totalPrice));

            shopService.addProductInTheBasket(product.getName(), 1,
                    totalPrice, product.getImage());
            shopService.updatePrice(product.getName(), totalPrice);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
