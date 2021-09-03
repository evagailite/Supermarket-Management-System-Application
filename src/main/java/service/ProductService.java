package service;

import controller.ViewController;
import database.DBHandler;
import database.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Product;
import types.Category;
import types.ProductUnit;

import java.sql.*;
import java.util.ArrayList;

public class ProductService extends ViewController {
    private Connection connection = DBHandler.getConnection();

    public void createProduct(Product product) throws SQLException {
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.CREATE_PRODUCT);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setDouble(2, product.getPricePerUnit());
        preparedStatement.setDouble(3, product.getQuantity());
        preparedStatement.setString(4, String.valueOf(product.getProductUnit()));
        preparedStatement.setString(5, String.valueOf(product.getCategory()));
        preparedStatement.setString(6, product.getImage());

        preparedStatement.executeUpdate();

        showAlert("Product Created", "Product created successfully", Alert.AlertType.CONFIRMATION);
        DBHandler.closeConnections(preparedStatement, connection);

    }

    public ObservableList<Product> getAllProducts() throws SQLException {
        connection = DBHandler.getConnection();
        ObservableList<Product> products = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_ALL_PRODUCTS);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getInt("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getDouble("quantity"),
                    resultSet.getDouble("price"),
                    ProductUnit.valueOf(resultSet.getString("unit")),
                    Category.valueOf(resultSet.getString("category")),
                    resultSet.getString("image")));
        }
        //   DBHandler.closeConnections(resultSet, preparedStatement, connection);
        return products;
    }

    public void deleteProduct(Integer id) throws SQLException {
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.DELETE_PRODUCT);
        preparedStatement.setString(1, String.valueOf(id));
        preparedStatement.executeUpdate();
        // showAlert("Product Deleted", "Product deleted successfully", Alert.AlertType.CONFIRMATION);
        DBHandler.closeConnections(preparedStatement, connection);
    }

    public void editProduct(String name, double quantity, double price,
                            ProductUnit productUnit, Category category, String image,
                            Integer id) throws SQLException {
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.UPDATE_PRODUCT);

        preparedStatement.setString(1, name);
        preparedStatement.setDouble(2, quantity);
        preparedStatement.setDouble(3, price);
        preparedStatement.setString(4, String.valueOf(productUnit));
        preparedStatement.setString(5, String.valueOf(category));
        preparedStatement.setString(6, image);
        preparedStatement.setInt(7, id);

        preparedStatement.executeUpdate();
        DBHandler.closeConnections(preparedStatement, connection);
    }

    public ArrayList<Product> getAllProductsForShop() throws SQLException {
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_ALL_PRODUCTS);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getInt("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getDouble("quantity"),
                    resultSet.getDouble("price"),
                    ProductUnit.valueOf(resultSet.getString("unit")),
                    Category.valueOf(resultSet.getString("category")),
                    resultSet.getString("image")));
        }
        // DBHandler.closeConnections(resultSet, preparedStatement, connection);
        return products;
    }

    public ArrayList<Product> getFoodProductsForShop() throws SQLException {
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_FOOD_PRODUCTS);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getInt("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getDouble("quantity"),
                    resultSet.getDouble("price"),
                    ProductUnit.valueOf(resultSet.getString("unit")),
                    Category.valueOf(resultSet.getString("category")),
                    resultSet.getString("image")));
        }
        // DBHandler.closeConnections(resultSet, preparedStatement, connection);
        return products;
    }

    public ArrayList<Product> getNonFoodProductsForShop() throws SQLException {
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_NON_FOOD_PRODUCTS);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getInt("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getDouble("quantity"),
                    resultSet.getDouble("price"),
                    ProductUnit.valueOf(resultSet.getString("unit")),
                    Category.valueOf(resultSet.getString("category")),
                    resultSet.getString("image")));
        }
        // DBHandler.closeConnections(resultSet, preparedStatement, connection);
        return products;
    }

    public ArrayList<Product> getDrinksProductsForShop() throws SQLException {
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_DRINK_PRODUCTS);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getInt("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getDouble("quantity"),
                    resultSet.getDouble("price"),
                    ProductUnit.valueOf(resultSet.getString("unit")),
                    Category.valueOf(resultSet.getString("category")),
                    resultSet.getString("image")));
        }
        // DBHandler.closeConnections(resultSet, preparedStatement, connection);
        return products;
    }

}
