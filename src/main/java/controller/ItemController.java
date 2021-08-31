package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Product;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    @FXML
    private ImageView productImage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private JFXButton addToCart;
    public static final String CURRENCY = "$";
    private Product product;

    public void setData(Product product) {
        this.product = product;
        nameLabel.setText(product.getName());
        priceLabel.setText(CURRENCY + product.getPricePerUnit());
        Image image = new Image("file:///C:/Users/Eva/Dropbox/Programming/AccentureBootcamp2021/projects/finalProject/src/main/resources/main/finalproject/images/shop/" + product.getImage());
        productImage.setImage(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
