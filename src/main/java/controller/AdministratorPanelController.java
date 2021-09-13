package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import model.Product;
import model.Sale;
import model.Users;
import service.ProductService;
import service.SaleService;
import service.UserService;
import types.Category;
import types.ProductUnit;
import types.UserType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.*;

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
    @FXML
    private Button productsButton;
    @FXML
    private Button orderButton;
    @FXML
    private Button userButton;
    @FXML
    private Label productCountLabel;
    @FXML
    private Label orderCountLabel;
    @FXML
    private Label userCountLabel;
    @FXML
    private BarChart<?, ?> productBarChart;
    @FXML
    private Label salesCountLabel;
    @FXML
    private TableView<Sale> salesTable;
    @FXML
    private TableColumn<Sale, Integer> salesOrderColumn;
    @FXML
    private TableColumn<Sale, Integer> quantityColumn;
    @FXML
    private TableColumn<Sale, Double> priceColumn;
    @FXML
    private TableColumn<Sale, String> dateColumn;
    @FXML
    private TableColumn<Sale, String> userColumn;
    @FXML
    private TableColumn<Sale, String> productSalesNameColumn;
    @FXML
    private CategoryAxis productNameXAxis;
    private Image image;
    private UserService userService = new UserService();
    private ProductService productService = new ProductService();
    private SaleService saleService = new SaleService();
    private List<Sale> topThreeProducts = new ArrayList<>();
    private final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == buttonCreateProduct) {
            anchorPaneProducts.toFront();
            showAllProducts();
        } else if (event.getSource() == buttonDashboard) {
            anchorPaneDashboard.toFront();
            showAllSales();
            getCurrentDate();
        } else if (event.getSource() == buttonUsers) {
            anchorPaneUsers.toFront();
            showAllUsers();
        }
    }

    private void showProductChart() {
        try {
            topThreeProducts.addAll(getBestSellingData(saleService.getBestSellingItems()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        XYChart.Series set = new XYChart.Series();


        for (int i = 0; i < topThreeProducts.size(); i++) {
            String x = topThreeProducts.get(i).getProductName().trim();
            int ind = x.lastIndexOf(" ");
            if (ind > 0) {
                x = x.substring(0, ind).trim();
            }
            int y = topThreeProducts.get(i).getQuantity();
            set.getData().add(new XYChart.Data<>(x, y));
        }
        productBarChart.getData().addAll(set);
        productBarChart.setBarGap(1);
        productBarChart.setCategoryGap(10);
        productNameXAxis.tickLabelFontProperty().set(Font.font(9));
    }

    private List<Sale> getBestSellingData(List<Sale> bestSellingProducts) {
        List<Sale> products = bestSellingProducts;
        for (int i = 0; i < topThreeProducts.size(); i++) {
            Sale sale = new Sale();
            sale.setProductName(sale.getProductName());
            sale.setQuantity(sale.getQuantity());
            products.add(sale);
        }
        return products;
    }

    private void showCurrentDate() {
        Date date = new Date();
        dateDashboardLabel.setText(String.valueOf(date));
    }

    private String getCurrentDate() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH);
        String date = dateFormat.format(new Date());
        return date;
    }

    private void showAllUsers() {
        ObservableList<Users> userList;

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

    private void showAllSales() {
        ObservableList<Sale> saleList;

        salesOrderColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>("orderNumber"));
        productSalesNameColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Sale, Double>("price"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("orderDate"));
        userColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("username"));

        try {
            saleList = saleService.getAllSales();
            salesTable.setItems(saleList);
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

    public void styleRowColor() {
        Callback<TableColumn<Product, Double>, TableCell<Product, Double>> cellFactory
                = //
                new Callback<TableColumn<Product, Double>, TableCell<Product, Double>>() {
                    @Override
                    public TableCell<Product, Double> call(final TableColumn<Product, Double> param) {
                        final TableCell<Product, Double> cell = new TableCell<Product, Double>() {

                            @Override
                            public void updateItem(Double item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    setText(String.valueOf(item));
                                    TableRow<Product> row = getTableRow();
                                    if (row.getItem().getQuantity() == 0.0) {
                                        row.getStyleClass().clear();
                                        row.setStyle("-fx-background-color: red");
                                        row.getStyleClass().add("-fx-background-color: red");
                                        setText(String.valueOf(item));
                                    }
                                    if (row.getItem().getQuantity() == 1) {
                                        row.getStyleClass().clear();
                                        row.setStyle("-fx-background-color: orange");
                                        setText(String.valueOf(item));
//                                        row.getStyleClass().add("orange-row");
                                    }
//                                    if (row.getItem().getColor().equals("green")) {
//                                        row.getStyleClass().clear();
//                                        row.getStyleClass().add("green-row");
//                                    }
//                                    if (row.getItem().getColor().equals("yellow")) {
//                                        row.getStyleClass().clear();
//                                        row.getStyleClass().add("yellow-row");
//                                    }
                                }
                            }
                        };
                        return cell;
                    }
                };
        productQtyColumn.setCellFactory(cellFactory);
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


        productsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                anchorPaneProducts.toFront();
                showAllProducts();
                styleRowColor();
            }
        });

        orderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        userButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                anchorPaneUsers.toFront();
                showAllUsers();
            }
        });

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
                    clearProductTextFields();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                showAllProducts();
                styleRowColor();
                getStatistic();
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
                        clearUserTextFields();
                    }
                } catch (Exception e) {
                    showAlert("Something went wrong", e.getMessage(), Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
                showAllUsers();
                getStatistic();
            }
        });
    }

    private void clearUserTextFields() {
        createUsernameTextField.clear();
        createNameTextField.clear();
        createEmailTextField.clear();
        passwordTextField.clear();
        passwordConfirmationTextField.clear();
        userTypeComboBox.getSelectionModel().clearSelection();
    }

    private void clearProductTextFields() {
        productNameTextField.clear();
        priceTextField.clear();
        quantityTextField.clear();
        unitComboBox.getSelectionModel().clearSelection();
        categoryComboBox.getSelectionModel().clearSelection();
        imagePathTextField.clear();
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
        showAllSales();
        dateDashboardLabel.setText(getCurrentDate());
        getStatistic();
        showProductChart();
    }

    private void getStatistic() {
        try {
            userCountLabel.setText(String.valueOf(userService.getUserCount()));
            productCountLabel.setText(String.valueOf(productService.getProductCount()));
            orderCountLabel.setText(String.valueOf(saleService.getSalesCount()));
            salesCountLabel.setText(String.valueOf(df.format(saleService.getAllSalesTotal())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
