package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import main.finalproject.Main;
import model.Product;
import service.ShopService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShoppingCartController extends ViewController implements Initializable {
    @FXML
    private JFXButton basketButton;
    @FXML
    private JFXButton homeButton;
    @FXML
    private JFXButton accountButton;
    @FXML
    private JFXButton buttonLogOut;
    @FXML
    private VBox vbox;
    private ShopService shopService = new ShopService();
    private List<Product> shoppingBasket = new ArrayList<>();

    public List<Product> getShoppingCartData() {
        List<Product> products = null;
        try {
            products = shopService.getAllShoppingBasketProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < shoppingBasket.size(); i++) {
            Product product = new Product();
            product.setName(product.getName());
            product.setPricePerUnit(product.getPricePerUnit());
            product.setImage("/images/" + product.getImage());
            products.add(product);
        }
        return products;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        shoppingBasket.addAll(getShoppingCartData());

        for (int i = 0; i < shoppingBasket.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("shoppingCartItem.fxml"));
            try {
                AnchorPane hBox = fxmlLoader.load();
                ShoppingCartItemController shoppingCartItemController = fxmlLoader.getController();
                shoppingCartItemController.setShoppingCartData(shoppingBasket.get(i));
                vbox.getChildren().add(hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            //action happens after click on it
            @Override
            public void handle(ActionEvent event) {
                //if users logged out
                try {
                    changeScene(event, "login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        basketButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    changeSceneForCustomer(event, "shoppingCart", null, 0);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneForCustomer(event, "shop", null, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        accountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }
}
