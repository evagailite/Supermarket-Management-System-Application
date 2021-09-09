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
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class ItemController extends ViewController implements Initializable {
    @FXML
    private ImageView productImage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    public JFXButton addToCart;
    @FXML
    private Button decrementButton;
    @FXML
    private Label quantityNumberLabel;
    @FXML
    private Button incrementButton;
    public ShopService shopService = new ShopService();
    private final DecimalFormat df = new DecimalFormat("0.00");
    private UserService userService = new UserService();

    public void setData(Product product) {
        try {

            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));

            nameLabel.setText(product.getName());
            priceLabel.setText("$" + df.format(product.getPricePerUnit()));
            Image image = new Image("file:///C:/Users/Eva/Dropbox/Programming/AccentureBootcamp2021/projects/finalProject/src/main/resources/main/finalproject/images/shop/" + product.getImage());
            productImage.setImage(image);
            quantityNumberLabel.setText(String.valueOf(1));

            ArrayList<Product> chosenProducts = shopService.getAllShoppingBasketProducts();
            for (Product product1 : chosenProducts) {
                if (product1.getName().equals(product.getName())) {
                    addToCart.setVisible(false);
                    quantityNumberLabel.setText(String.valueOf((int) product1.getQuantity()));
                    incrementButton.setOnAction(event1 -> {
                        increaseValue(product);
                    });

                    decrementButton.setOnAction(event1 -> {
                        decreaseValue(product);
                    });
                }
            }

            addToCart.setOnAction(event -> {
                try {
                    String shopUser = userService.getOnlineUser("TRUE");
                    shopService.addProductInTheBasket(product.getName(), Double.parseDouble(quantityNumberLabel.getText()),
                            product.getPricePerUnit(), product.getImage(), shopUser);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                incrementButton.setOnAction(event1 -> {
                    increaseValue(product);
                });

                decrementButton.setOnAction(event1 -> {
                    decreaseValue(product);

                });
                addToCart.setVisible(false);

//                try {
//                    changeSceneForShop(event, "shop");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void decreaseValue(Product product) {
        int value = Integer.parseInt(quantityNumberLabel.getText());
        value--;
        if (value == 0) {
            try {
                shopService.removeProductFromShoppingBasket(product.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            addToCart.setVisible(true);
        } else {
            try {
                String shopUser = userService.getOnlineUser("TRUE");
                quantityNumberLabel.setText(String.valueOf(value));
                shopService.addProductInTheBasket(product.getName(), (-1),
                        product.getPricePerUnit(), product.getImage(), shopUser);

//                shopService.getTotalPrice(product.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void increaseValue(Product product) {
        try {
            String shopUser = userService.getOnlineUser("TRUE");
            int value = Integer.parseInt(quantityNumberLabel.getText());
            value = value + 1;
            quantityNumberLabel.setText(String.valueOf(value));
            shopService.addProductInTheBasket(product.getName(), 1,
                    product.getPricePerUnit(), product.getImage(), shopUser);

            //  shopService.updatePrice(product.getName(), product.getPricePerUnit() * value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
