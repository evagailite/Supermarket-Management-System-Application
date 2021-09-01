package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import main.finalproject.Main;
import model.Product;
import service.ProductService;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShopController extends ViewController implements Initializable {
    UserService userService = new UserService();
    @FXML
    private JFXButton basketButton;
    @FXML
    private JFXButton homeButton;
    @FXML
    private JFXButton accountButton;
    @FXML
    private JFXButton buttonLogOut;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane gridpane;
    //    @FXML
//    private Label label_welcome;
    @FXML
    private Label labelToTheSupermarket;
    private List<Product> productList = new ArrayList<>();
    private ProductService productService = new ProductService();

    public List<Product> getData() {
        List<Product> products = null;
        try {
            products = productService.getAllProductsForShop();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < productList.size(); i++) {
            Product product = new Product();
            product.setName(product.getName());
            product.setPricePerUnit(product.getPricePerUnit());
            product.setImage("/images/" + product.getImage());
            products.add(product);
        }
        return products;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productList.addAll(getData());
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < productList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(productList.get(i));

                if (column == 4) {
                    column = 0;
                    row++;
                }

                gridpane.add(anchorPane, column++, row);

                gridpane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridpane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridpane.setMaxWidth(Region.USE_PREF_SIZE);

                gridpane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridpane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridpane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(5));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            //action happens after click on it
            @Override
            public void handle(ActionEvent event) {
                //if users logged out
                try {
                    userService.changeScene(event, "login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        basketButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneForShop(event, "shoppingCart");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneForShop(event, "shop");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setUserInformationForCustomer(String username, double budget) {
        //   label_welcome.setText("Welcome " + username + "!");
        labelToTheSupermarket.setText("You have " + budget + " in your Digital Wallet");
    }

    public void setUserInformation(String username) {

    }
}
