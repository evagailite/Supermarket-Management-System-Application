package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import main.finalproject.Main;
import model.Product;
import service.ProductService;
import service.ShopService;
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
    private TextField searchTextField;
    @FXML
    private JFXButton searchButton;
    @FXML
    private JFXButton basketButton;
    @FXML
    private JFXButton homeButton;
    @FXML
    private JFXButton accountButton;
    @FXML
    private JFXButton buttonLogOut;
    @FXML
    private Label numberInTheBasket;
    @FXML
    private Button foodButton;
    @FXML
    private Button drinksButton;
    @FXML
    private Button nonFoodButton;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane gridpane;
    private List<Product> productList = new ArrayList<>();
    private ProductService productService = new ProductService();

    private ShopService shopService = new ShopService();

    public List<Product> getData(ArrayList<Product> allProductsForShop) {
        List<Product> products = allProductsForShop;
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

        showBasketSize();
        try {
            productList.addAll(getData(productService.getAllProductsForShop()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        placeProductsInTheShop();

        foodButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    gridpane.getChildren().retainAll(gridpane.getChildren().get(0));
                    productList.clear();
                    productList.addAll(getData(productService.getFoodProductsForShop()));
                    placeProductsInTheShop();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        drinksButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    gridpane.getChildren().retainAll(gridpane.getChildren().get(0));
                    productList.clear();
                    productList.addAll(getData(productService.getDrinksProductsForShop()));
                    placeProductsInTheShop();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

        nonFoodButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    gridpane.getChildren().retainAll(gridpane.getChildren().get(0));
                    productList.clear();
                    productList.addAll(getData(productService.getNonFoodProductsForShop()));
                    placeProductsInTheShop();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            //action happens after click on it
            @Override
            public void handle(ActionEvent event) {
                //if users logged out
                try {
                    shopService.clearBasket();
                    userService.changeScene(event, "login");
                } catch (IOException | SQLException e) {
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

    private void placeProductsInTheShop() {

        int column = 0;
        int row = 1;
        for (int i = 0; i < productList.size(); i++) {
            try {
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

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showBasketSize() {
        try {
            int basketSize = shopService.getShoppingCartSize();
            if (basketSize == 0) {
                numberInTheBasket.setVisible(false);
            } else {
                numberInTheBasket.setVisible(true);
                numberInTheBasket.setText(String.valueOf(basketSize));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
