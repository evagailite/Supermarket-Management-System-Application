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
import service.ProductService;
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
    private Label totalPriceBasketLabel;
    @FXML
    private Button removeFromBasketButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label priceBasketLabel;
    private ShopService shopService = new ShopService();
    private final DecimalFormat df = new DecimalFormat("0.00");
    private UserService userService = new UserService();
    private ProductService productService = new ProductService();

    public void setShoppingCartData(Product product) {
        try {
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));

            productNameBasketLabel.setText(product.getName());
            priceBasketLabel.setText("$" + df.format(product.getPricePerUnit()));
            totalPriceBasketLabel.setText("$" + df.format(product.getPricePerUnit()));
            Image image = new Image("file:///C:/Users/Eva/Dropbox/Programming/AccentureBootcamp2021/projects/finalProject/src/main/resources/main/finalproject/images/shop/"
                    + product.getImage());
            productImageBasketImage.setImage(image);
            quantityNumberLabel.setText(String.valueOf(1));

            ArrayList<Product> chosenProducts = shopService.getAllShoppingBasketProducts();
            for (Product product1 : chosenProducts) {
                if (product1.getName().equals(product.getName())) {
                    quantityNumberLabel.setText(String.valueOf((int) product1.getQuantity()));
                    int quantity = (int) shopService.getQuantity(product1.getName());
                    if (quantity > 1) {
                        totalPriceBasketLabel.setText("$" + df.format(product1.getQuantity() * product1.getPricePerUnit()));
                    }

                    increaseButton.setOnAction(event1 -> {
                        if (checkIfSufficientQuantity(product)) {
                            increaseButton.setDisable(false);
                            increaseValue(product);
                        } else {
                            increaseButton.setDisable(true);
                        }
                        try {
                            changeSceneForShop(event1, "shoppingCart");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    decreaseButton.setOnAction(event1 -> {
                        if (!checkIfSufficientQuantity(product)) {
                            decreaseButton.setDisable(false);
                            decreaseValue(product);
                        } else {
                            decreaseValue(product);
                        }
                        try {
                            changeSceneForShop(event1, "shoppingCart");

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
                        resetWarehouseQuantity(product);
                        shopService.removeProductFromShoppingBasket(product.getName());
                        anchorPane.setVisible(false);

                        changeSceneForShop(event, "shoppingCart");

                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfSufficientQuantity(Product product) {
        try {
            int quantity = productService.productQuantity(product.getName());
            if (quantity == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void resetWarehouseQuantity(Product product) {
        try {
            int basketQty = (int) shopService.getQuantity(product.getName());
            int warehouseQty = productService.productQuantity(product.getName());
            int totalQuantity = warehouseQty + basketQty;
            productService.editProductQuantity(product.getName(), totalQuantity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void decreaseValue(Product product) {
        try {
            String shopUser = userService.getOnlineUser("TRUE");

            int value = Integer.parseInt(quantityNumberLabel.getText());
            double startPrice = product.getPricePerUnit();
            double pricePerQuantity = (startPrice * value) - startPrice;
            double totalPrice = Math.round(pricePerQuantity * 100) / 100D;
            value--;
            if (value == 0) {
                try {
                    resetWarehouseQuantity(product);
                    shopService.removeProductFromShoppingBasket(product.getName());
                    anchorPane.setVisible(false);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    quantityNumberLabel.setText(String.valueOf(value));
                    totalPriceBasketLabel.setText("$" + df.format((totalPrice)));

                    shopService.addProductInTheBasket(product.getName(), (-1),
                            totalPrice, product.getImage(), shopUser);

                    int stockQuantity = productService.productQuantity(product.getName());
                    int totalQuantity = stockQuantity + 1;
                    productService.editProductQuantity(product.getName(), totalQuantity);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void increaseValue(Product product) {
        try {
            String shopUser = userService.getOnlineUser("TRUE");

            int value = Integer.parseInt(quantityNumberLabel.getText());
            value++;
            quantityNumberLabel.setText(String.valueOf(value));

            double startPrice = product.getPricePerUnit();
            double pricePerQuantity = startPrice * (value);
            double totalPrice = Math.round(pricePerQuantity * 100) / 100D;
            totalPriceBasketLabel.setText("$" + (df.format((totalPrice))));

            shopService.addProductInTheBasket(product.getName(), 1,
                    totalPrice, product.getImage(), shopUser);

            int stockQuantity = productService.productQuantity(product.getName());
            int totalQuantity = stockQuantity - 1;
            productService.editProductQuantity(product.getName(), totalQuantity);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
