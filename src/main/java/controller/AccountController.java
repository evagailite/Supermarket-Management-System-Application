package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.finalproject.Main;
import model.Product;
import model.Sale;
import service.ProductService;
import service.SaleService;
import service.ShopService;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountController extends ViewController implements Initializable {
    @FXML
    private VBox orderHistoryVBox;
    @FXML
    private TextField searchTextField;
    @FXML
    private JFXButton searchButton;
    @FXML
    private JFXButton basketButton;
    @FXML
    private JFXButton homeButton;
    @FXML
    private JFXButton buttonLogOut;
    @FXML
    private Label numberInTheBasket;
    @FXML
    private JFXButton accountButton;
    @FXML
    private Button goBackShoppingButton;
    @FXML
    private Label usernameTextField;
    @FXML
    private Label emailTextField;
    @FXML
    private PasswordField currentPasswordTextField;
    @FXML
    private PasswordField newPasswordTextField;
    @FXML
    private PasswordField confirmNewPasswordField;
    @FXML
    private Button backToTheEditProfileButton;
    @FXML
    private JFXButton savePasswordButton;
    @FXML
    private JFXButton changePasswordButton;
    @FXML
    private TextField changeUsernameField;
    @FXML
    private TextField changeEmailField;
    @FXML
    private JFXButton deleteAnAccountButton;
    @FXML
    private Button backToTheProfileAfterEditButton;
    @FXML
    private JFXButton editProfileInfoButton;
    @FXML
    private AnchorPane userInfoAnchorPane;
    @FXML
    private AnchorPane editProfileAnchorPane;
    @FXML
    private AnchorPane changePasswordAnchorPane;
    @FXML
    private JFXButton changeUsernameButton;
    @FXML
    private JFXButton changeEmailButton;
    @FXML
    private Pane noHistoryPane;
    private List<Sale> customerSales = new ArrayList<>();
    private SaleService saleService = new SaleService();
    private ShopService shopService = new ShopService();
    private UserService userService = new UserService();
    private ProductService productService = new ProductService();

    public List<Sale> getCustomerSales() {
        List<Sale> sales = null;
        try {
            String username = userService.getOnlineUser("TRUE");
            sales = saleService.getCustomerSales(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < customerSales.size(); i++) {
            Sale sale = new Sale();
            sale.setOrderNumber(sale.getOrderNumber());
            sale.setOrderDate(sale.getOrderDate());
            sale.setPrice(sale.getPrice());
            sales.add(sale);
        }
        return sales;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadSaleOrders();

        showBasketSize();

        loadUserProfileInformation();

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String userSearch = searchTextField.getText();
                    changeSceneSearch(event, "shop", userSearch);
                } catch (IOException e) {
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

        goBackShoppingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    changeSceneHome(event, "shop");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        deleteAnAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation dialog");
                alert.setContentText("Are you sure you want to delete your account? \nYour account will be deleted permanently!");
                alert.setHeaderText(null);
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    String shopUser = getShopUser();
                    try {
                        userService.deleteUserByUsername(shopUser);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    showAlert("Delete", "You have deleted your account", Alert.AlertType.INFORMATION);
                }
                try {
                    userService.changeScene(event, "login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        savePasswordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showChangePassword();
                if (currentPasswordTextField.getText().isEmpty() || newPasswordTextField.getText().isEmpty() ||
                        confirmNewPasswordField.getText().isEmpty()) {
                    showAlert("Error", "Please fill fields to make changes", Alert.AlertType.ERROR);
                } else {
                    try {
                        String username = setUsername();
                        validateUserCurrentPassword(currentPasswordTextField.getText());
                        System.out.println(username);
                        validateUserPassword(newPasswordTextField.getText(), confirmNewPasswordField.getText());
                        userService.changeUserPassword(newPasswordTextField.getText(), username);
                        showAlert("Password Change", "You have changed password successfully", Alert.AlertType.INFORMATION);

                        try {
                            changeSceneForShop(event, "account");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        currentPasswordTextField.clear();
                        newPasswordTextField.clear();
                        confirmNewPasswordField.clear();
                        showChangePassword();
                    } catch (Exception e) {
                        showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                    }
                }

            }

        });

        backToTheProfileAfterEditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                showUserProfile();
                try {
                    changeSceneForShop(event, "account");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        backToTheEditProfileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showEditUserProfile();
            }
        });

        editProfileInfoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showEditUserProfile();
            }
        });

        changePasswordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showChangePassword();
            }
        });

        changeEmailButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showEditUserProfile();
                if (!changeEmailField.getText().isEmpty()) {
                    try {
                        String username = getShopUser();
                        int userId = userService.getUserId(username);
                        userService.changeUserEmail(changeEmailField.getText(), userId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    showAlert("Edit Profile", "Email changed successfully", Alert.AlertType.INFORMATION);
                    changeUsernameField.clear();
                    changeEmailField.clear();
                    try {
                        changeSceneForShop(event, "account");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showAlert("Error", "Please fill fields to make changes", Alert.AlertType.ERROR);
                }
            }
        });

        changeUsernameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!changeUsernameField.getText().isEmpty()) {
                    try {
                        String username = getShopUser();
                        int userId = userService.getUserId(username);
                        validateUserUsername(changeUsernameField.getText());
                        userService.changeUsername(changeUsernameField.getText(), userId);
                    } catch (Exception e) {
                        showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                        e.printStackTrace();
                    }
                    showAlert("Edit Profile", "Username changed successfully", Alert.AlertType.INFORMATION);
                    changeUsernameField.clear();
                    changeEmailField.clear();
                    try {
                        changeSceneForShop(event, "account");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showAlert("Error", "Please fill fields to make changes", Alert.AlertType.ERROR);
                }
            }
        });


    }

    private void validateUserCurrentPassword(String currentPassword) throws Exception {
        String shopUser = getShopUser();
        String userPasswordInDB = userService.getUserPassword(shopUser);
        System.out.println(currentPassword);
        System.out.println(userPasswordInDB);
        if (!currentPassword.equals(userPasswordInDB))
            throw new Exception("Incorrect Password!");
    }

    private void showEditUserProfile() {
        changePasswordAnchorPane.toBack();
        userInfoAnchorPane.toBack();
        editProfileAnchorPane.toFront();
    }

    private void showChangePassword() {
        editProfileAnchorPane.toBack();
        userInfoAnchorPane.toBack();
        changePasswordAnchorPane.toFront();
    }

    private String getShopUser() {
        try {
            String username = userService.getOnlineUser("TRUE");
            return username;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void validateUserPassword(String password, String passwordConfirmation) throws Exception {
        if (!password.equals(passwordConfirmation))
            throw new Exception("Password does not match, confirm password");
        if (password.length() < 4)
            throw new Exception("Password needs to be minimum 4 characters");
    }

    public void validateUserUsername(String username) throws Exception {
        if (username.length() < 4)
            throw new Exception("Username needs to be minimum 4 characters");
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

    private void loadSaleOrders() {

        customerSales.addAll(getCustomerSales());

        if(customerSales.size()==0){
            orderHistoryVBox.setVisible(false);
            orderHistoryVBox.toBack();
            noHistoryPane.toFront();
            noHistoryPane.setVisible(true);
        }else {
            for (int i = 0; i < customerSales.size(); i++) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("accountItem.fxml"));
                    AnchorPane hBox = fxmlLoader.load();
                    AccountItemController accountItemController = fxmlLoader.getController();
                    accountItemController.setSalesDates(customerSales.get(i));
                    orderHistoryVBox.getChildren().add(hBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            noHistoryPane.toBack();
            noHistoryPane.setVisible(false);
            orderHistoryVBox.setVisible(true);
            orderHistoryVBox.toFront();
        }

    }

    private void loadUserProfileInformation() {
        usernameTextField.setText(setUsername());
        try {
            emailTextField.setText(userService.getUserEmail(setUsername()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String setUsername() {
        try {
            String username = userService.getOnlineUser("TRUE");
            return username;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
