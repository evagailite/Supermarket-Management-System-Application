package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.util.*;

public class ShopController extends ViewController implements Initializable {
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
    private GridPane gridpane;
    private List<Product> productList = new ArrayList<>();
    private ProductService productService = new ProductService();
    private ShopService shopService = new ShopService();
    private UserService userService = new UserService();
    private String user;

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

        Platform.runLater(() -> {
            try {
                userService.setUserIsOnlineStatus("TRUE", this.user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        showBasketSize();

        try {
            productList.addAll(getData(productService.getAllProductsForShop()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        placeProductsInTheShop(productList);

        foodButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    gridpane.getChildren().retainAll(gridpane.getChildren().get(0));
                    productList.clear();
                    productList.addAll(getData(productService.getFoodProductsForShop()));
                    placeProductsInTheShop(productList);
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
                    placeProductsInTheShop(productList);
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
                    placeProductsInTheShop(productList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    gridpane.getChildren().retainAll(gridpane.getChildren().get(0));
                    productList.clear();


                    //*********************
                    productList.addAll(getData(productService.getNonFoodProductsForShop()));
                    placeProductsInTheShop(productList);
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
                resetWarehouseQuantity();
                try {
                    String username = userService.getOnlineUser("TRUE");
                    shopService.clearBasket();
                    userService.setUserIsOnlineStatus("FALSE", username);
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

        accountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneForShop(event, "account");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void sortByHighestPrice(ActionEvent event) {
        gridpane.getChildren().retainAll(gridpane.getChildren().get(0));
        productList.clear();
        try {
            productList.addAll(getData(productService.getProductsSortedByHighestPrice()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        placeProductsInTheShop(productList);
    }

    @FXML
    void sortByLowestPrice(ActionEvent event) {
        gridpane.getChildren().retainAll(gridpane.getChildren().get(0));
        productList.clear();
        try {
            productList.addAll(getData(productService.getProductsSortedByLowestPrice()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        placeProductsInTheShop(productList);
    }

    private void resetWarehouseQuantity() {
        if (!checkIfBasketIsEmpty()) {
            try {
                List<Product> productsInBasket = shopService.getAllShoppingBasketProducts();
                for (Product product : productsInBasket) {
                    int basketQty = (int) shopService.getQuantity(product.getName());
                    int warehouseQty = productService.productQuantity(product.getName());
                    int totalQuantity = warehouseQty + basketQty;
                    productService.editProductQuantity(product.getName(), totalQuantity);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkIfBasketIsEmpty() {
        try {
            int amountOfProductsInBasket = shopService.getShoppingCartSize();
            if (amountOfProductsInBasket == 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void placeProductsInTheShop(List<Product> productList) {

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

    public void setUsername(String username) {
        this.user = username;
    }

//    public void findProduct(){
//        ArrayList<Product> foundProduct = new ArrayList<>();
//        for (Product product : productList) {
//            if (searchList(searchTextField.getText(), ))
//                foundProduct.add(product);
//        }
//    }

//    private List<String> searchList(String searchWords, List<String> listOfProducts) {
//        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
//
//        return listOfProducts.stream().filter(input -> {
//            return searchWordsArray.stream().allMatch(word -> input.toLowerCase().contains(word.toLowerCase()));
//        }).collect(Collectors.toList());
//    }

//    private boolean searchList(String searchWords, List<Product> listOfProducts) {
//        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
//
//        return listOfProducts.stream().filter(input -> {
//            return searchWordsArray.stream().allMatch(word -> input.toLowerCase().contains(word.toLowerCase()));
//        }).collect(Collectors.toList());
//    }


}
