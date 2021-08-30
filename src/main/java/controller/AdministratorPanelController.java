package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import model.Product;
import model.Users;
import service.ProductService;
import service.UserService;
import types.Category;
import types.ProductUnit;
import types.UserType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdministratorPanelController extends ViewController implements Initializable {
    @FXML
    private Button buttonLoggedOut;
    @FXML
    private JFXButton buttonCreateProduct;
    @FXML
    private JFXButton buttonDashboard;
    @FXML
    private JFXButton buttonUsers;
    @FXML
    private Pane panelCreateProduct;
    @FXML
    private AnchorPane anchorPaneDashboard;
    @FXML
    private AnchorPane anchorPaneUsers;
    @FXML
    private TableView<Users> userTable;
    @FXML
    private TableColumn<Users, Integer> columnId;
    @FXML
    private TableColumn<Users, String> columnUsername;
    @FXML
    private TableColumn<Users, String> columnName;
    @FXML
    private TableColumn<Users, String> columnEmail;
    @FXML
    private TableColumn<Users, Double> columnBudget;
    @FXML
    private TableColumn<Users, UserType> columnUserType;
    @FXML
    private TableColumn columnDeleteUser;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private JFXButton browseImageButton;
    @FXML
    private JFXComboBox<ProductUnit> unitComboBox;
    @FXML
    private JFXComboBox<Category> categoryComboBox;
    @FXML
    private JFXButton handleCreateButtonAction;
    @FXML
    private TextField imagePathTextField;
    @FXML
    private ImageView productImageView;
    @FXML
    private AnchorPane anchorPaneProducts;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idProductColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> productQtyColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, ProductUnit> productUnitColumn;
    @FXML
    private TableColumn<Product, Category> productCategoryColumn;
    @FXML
    private TableColumn<Product, String> productImageColumn;
    @FXML
    private JFXButton handleCreateUserFromAdminButton;
    @FXML
    private JFXComboBox<UserType> userTypeComboBox;
    @FXML
    private TextField createUsernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField passwordConfirmationTextField;
    @FXML
    private TextField createNameTextField;
    @FXML
    private TextField createEmailTextField;
    @FXML
    private TableColumn columnDeleteProduct;
    @FXML
    private TableColumn columnEditUser;
    @FXML
    private TableColumn columnEditProduct;
    @FXML
    private Label dateDashboardLabel;
    private Image image;
    private ObservableList<Users> userList;


    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == buttonCreateProduct) {
            anchorPaneProducts.toFront();
            showAllProducts();
        } else if (event.getSource() == buttonDashboard) {
            anchorPaneDashboard.toFront();
            showCurrentDate();
        } else if (event.getSource() == buttonUsers) {
            anchorPaneUsers.toFront();
            showAllUsers();
        }
    }

    private void showCurrentDate() {
        Date date = new Date();
        dateDashboardLabel.setText(String.valueOf(date));
    }

    UserService userService = new UserService();
    ProductService productService = new ProductService();

    private void showAllUsers() {

        columnId.setCellValueFactory(new PropertyValueFactory<Users, Integer>("id"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<Users, String>("username"));
        columnName.setCellValueFactory(new PropertyValueFactory<Users, String>("name"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<Users, String>("email"));
        columnBudget.setCellValueFactory(new PropertyValueFactory<Users, Double>("budget"));
        columnUserType.setCellValueFactory(new PropertyValueFactory<Users, UserType>("userType"));

        setEditableUsersTableColumns();
        addDeleteButtonInTheUsersTable();
        addEditButtonInTheUsersTable();

        try {
            userList = userService.getAllUsers();
            userTable.setItems(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addEditButtonInTheUsersTable() {
        Callback<TableColumn<Users, String>, TableCell<Users, String>> cellEditFactory = (param) -> {
            final TableCell<Users, String> cell = new TableCell<Users, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button updateButton = new Button("UPDATE");
                        updateButton.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 5;");
                        updateButton.setOnAction(event -> {
                            Users users = getTableView().getItems().get(getIndex());
                            setEditableUsersTableColumns();
                            try {
                                userService.editUser(users.getUsername(), users.getName(), users.getEmail(),
                                        users.getBudget(), users.getUserType(), users.getId());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            showAlert("Update", "User with id " + users.getId() + " updated successfully",
                                    Alert.AlertType.INFORMATION);
                        });
                        setGraphic(updateButton);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        columnEditUser.setCellFactory(cellEditFactory);
    }

    private void addDeleteButtonInTheUsersTable() {
        //cell factory to insert a button in every row
        Callback<TableColumn<Users, String>, TableCell<Users, String>> cellDeleteFactory = (param) -> {
            final TableCell<Users, String> cell = new TableCell<Users, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button deleteButton = new Button("DELETE");
                        deleteButton.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 5;");
                        deleteButton.setOnAction(event -> {
                            Users users = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation dialog");
                            alert.setContentText("Are you sure you want to delete user " + users.getUsername() + "?");
                            alert.setHeaderText(null);
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                try {
                                    userService.deleteUser(users.getId());
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                showAlert("Delete", "You have deleted user with id " + users.getId(), Alert.AlertType.INFORMATION);
                            }
                        });
                        setGraphic(deleteButton);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        columnDeleteUser.setCellFactory(cellDeleteFactory);
    }

    private void setEditableUsersTableColumns() {
        columnUsername.setCellFactory(TextFieldTableCell.forTableColumn());
        columnUsername.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setUsername(e.getNewValue());
        });

        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });

        columnEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        columnEmail.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
        });

        columnBudget.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        columnBudget.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setBudget(e.getNewValue());
        });

        columnUserType.setCellFactory(ComboBoxTableCell.forTableColumn(UserType.values()));
        columnUserType.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setUserType(e.getNewValue());
        });

    }

    private void showAllProducts() {
        ObservableList<Product> productList;

        idProductColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productQtyColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("quantity"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("pricePerUnit"));
        productUnitColumn.setCellValueFactory(new PropertyValueFactory<Product, ProductUnit>("productUnit"));
        productCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Category>("category"));
        productImageColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("image"));

        setEditableProductTableColumns();
        addDeleteButtonInTheProductTable();
        addEditButtonInTheProductTable();

        try {
            productList = productService.getAllProducts();
            productTable.setItems(productList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addEditButtonInTheProductTable() {
        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellEditFactory = (param) -> {
            final TableCell<Product, String> cell = new TableCell<Product, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button updateButton = new Button("UPDATE");
                        updateButton.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 5;");
                        updateButton.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            setEditableProductTableColumns();
                            try {
                                productService.editProduct(product.getName(), product.getQuantity(),
                                        product.getPricePerUnit(), product.getProductUnit(), product.getCategory(),
                                        product.getImage(), product.getId());

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            showAlert("Table updated", "Product with id " + product.getId() + " updated successfully", Alert.AlertType.INFORMATION);
                        });
                        setGraphic(updateButton);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        columnEditProduct.setCellFactory(cellEditFactory);
    }

    private void setEditableProductTableColumns() {

        productNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        productNameColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });

        productQtyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() {
        }));
        productQtyColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setQuantity(e.getNewValue());
        });

        productPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        productPriceColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPricePerUnit(e.getNewValue());
        });

        productUnitColumn.setCellFactory(ComboBoxTableCell.forTableColumn(ProductUnit.values()));
        productUnitColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setProductUnit(e.getNewValue());
        });

        productCategoryColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Category.values()));
        productCategoryColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCategory(e.getNewValue());
        });

        productImageColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        productImageColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setImage(e.getNewValue());
        });

    }

    private void addDeleteButtonInTheProductTable() {
        //cell factory to insert a button in every row
        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellDeleteFactory = (param) -> {
            final TableCell<Product, String> cell = new TableCell<Product, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button deleteButton = new Button("DELETE");
                        deleteButton.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 5;");
                        deleteButton.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation dialog");
                            alert.setContentText("Are you sure you want to delete product " + product.getName() + "?");
                            alert.setHeaderText(null);
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                try {
                                    productService.deleteProduct(product.getId());
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                showAlert("Delete", "You have deleted product with id: " + product.getId(), Alert.AlertType.INFORMATION);
                            }
                        });
                        setGraphic(deleteButton);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        columnDeleteProduct.setCellFactory(cellDeleteFactory);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showFirstPage();
        setComboBoxValues();

        buttonLoggedOut.setOnAction(new EventHandler<ActionEvent>() {
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

        handleCreateButtonAction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Product product = new Product(
                        productNameTextField.getText(),
                        Double.parseDouble(priceTextField.getText()),
                        Double.parseDouble(quantityTextField.getText()),
                        unitComboBox.getSelectionModel().getSelectedItem().toString(),
                        categoryComboBox.getSelectionModel().getSelectedItem().toString(),
                        imagePathTextField.getText()
                );
                try {
                    productService.createProduct(product);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        browseImageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open File Dialog");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                Stage stage = (Stage) panelCreateProduct.getScene().getWindow();
                File file = fileChooser.showOpenDialog(stage);

                if (file != null) {
                    image = new Image(file.getAbsoluteFile().toURI().toString(), productImageView.getFitWidth(),
                            productImageView.getFitHeight(), true, true);
                    productImageView.setImage(image);
                    productImageView.setPreserveRatio(true);
                    String fileName = file.getAbsolutePath();
                    String fileWithoutPath = fileComponent(fileName);
                    imagePathTextField.setText(fileWithoutPath);
                }
            }

        });

        handleCreateUserFromAdminButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SignUpController signUpController = new SignUpController();
                try {

                    if (createUsernameTextField.getText().isEmpty() || createNameTextField.getText().isEmpty() || createEmailTextField.getText().isEmpty() ||
                            passwordTextField.getText().isEmpty() || userTypeComboBox.getSelectionModel().getSelectedItem() == null) {
                        showAlert("Error", "Please fill all fields", Alert.AlertType.ERROR);
                    } else {
                        signUpController.validateUserInfo(passwordTextField.getText(), passwordConfirmationTextField.getText(), createUsernameTextField.getText());

                        String comboBoxUserTypeChoice = userTypeComboBox.getSelectionModel().getSelectedItem().toString();
                        checkUserTypeForBudget(comboBoxUserTypeChoice);
                        Users user = new Users(
                                createUsernameTextField.getText(),
                                createNameTextField.getText(),
                                createEmailTextField.getText(),
                                passwordTextField.getText(),
                                checkUserTypeForBudget(comboBoxUserTypeChoice),
                                UserType.valueOf(userTypeComboBox.getSelectionModel().getSelectedItem().toString())
                        );
                        userService.createUser(user);
                        clearTextFields();
                    }
                } catch (Exception e) {
                    showAlert("Something went wrong", e.getMessage(), Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            }
        });
    }

    private void clearTextFields() {
        createUsernameTextField.clear();
        createNameTextField.clear();
        createEmailTextField.clear();
        passwordTextField.clear();
        passwordConfirmationTextField.clear();
        userTypeComboBox.getSelectionModel().clearSelection();
    }

    private double checkUserTypeForBudget(String comboBoxUserTypeChoice) {
        if (comboBoxUserTypeChoice.equals("CUSTOMER")) {
            return 500;
        } else {
            return 0;
        }
    }

    private String fileComponent(String fileName) {
        int pos = fileName.lastIndexOf(File.separator);
        if (pos > -1)
            return fileName.substring(pos + 1);
        else
            return fileName;
    }

    private void setComboBoxValues() {
        unitComboBox.getItems().setAll(ProductUnit.values());
        categoryComboBox.getItems().setAll(Category.values());
        userTypeComboBox.getItems().setAll(UserType.values());
    }

    private void showFirstPage() {
        anchorPaneDashboard.toFront();
    }

}
