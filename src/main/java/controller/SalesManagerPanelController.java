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
import javafx.scene.layout.BorderPane;
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

public class SalesManagerPanelController extends ViewController implements Initializable {
    //@FXML
    //private BorderPane bp;

    @FXML
    private Label label_welcome_salesman;

    @FXML
    private Button buttonLoggedOut;

    @FXML
    private JFXButton buttonSales;

    @FXML
    private JFXButton buttonCreateProduct;

    @FXML
    private AnchorPane ap;

    @FXML
    private AnchorPane anchorPaneProducts;

    @FXML
    private Pane panelCreateProduct;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private JFXButton browseImageButton;

    @FXML
    private JFXButton handleCreateButtonAction;

    @FXML
    private JFXComboBox<ProductUnit> unitComboBox;

    @FXML
    private JFXComboBox<Category> categoryComboBox;

    @FXML
    private ImageView productImageView;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField imagePathTextField;

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
    private TableColumn columnDeleteProduct;

    @FXML
    private TableColumn columnEditProduct;

    @FXML
    private AnchorPane anchorPaneDashboard;

    @FXML
    private Pane dashboardPanel;

    @FXML
    private Label dateDashboardLabel;
    private Image image;
    private ProductService productService = new ProductService();

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == buttonCreateProduct) {
            anchorPaneProducts.toFront();
            showAllProducts();
        } else if (event.getSource() == buttonSales) {
            anchorPaneDashboard.toFront();
            showSales();
        }
    }
    private void showSales() {
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
                    productService.changeScene(event, "login");
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
                    //clearProductTextFields();
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

    }
    private void showFirstPage() {
        anchorPaneDashboard.toFront();
    }

}