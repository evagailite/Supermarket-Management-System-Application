package service;

import controller.ViewController;
import database.DBHandler;
import database.Queries;
import javafx.scene.control.Alert;
import model.Product;
import types.Category;
import types.ProductUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShopService extends ViewController {
    private Connection connection = DBHandler.getConnection();

    public void addProductInTheBasket(String name, double quantity, double price, String image) throws SQLException {
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.ADD_PRODUCT_IN_THE_BASKET);
        preparedStatement.setString(1, name);
        preparedStatement.setDouble(2, quantity);
        preparedStatement.setDouble(3, price);
        preparedStatement.setString(4, image);

        preparedStatement.executeUpdate();

//        showAlert("Product Created", "Product created successfully", Alert.AlertType.CONFIRMATION);
        DBHandler.closeConnections(preparedStatement, connection);
    }

    public ArrayList<Product> getAllShoppingBasketProducts() throws SQLException {
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_ALL_SHOPPING_BASKET_PRODUCTS);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getString("name"),
                    resultSet.getDouble("quantity"),
                    resultSet.getDouble("price"),
                    resultSet.getString("image")));
        }
        // DBHandler.closeConnections(resultSet, preparedStatement, connection);
        return products;
    }

    public void removeProductFromShoppingBasket(String name) throws SQLException {
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.DELETE_FROM_SHOPPING_BASKET);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();

         DBHandler.closeConnections(preparedStatement, connection);
    }


}
