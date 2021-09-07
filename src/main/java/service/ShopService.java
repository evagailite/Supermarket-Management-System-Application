package service;

import controller.ViewController;
import database.DBHandler;
import database.Queries;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShopService extends ViewController {
    private Connection connection = DBHandler.getConnection();

    public void addProductInTheBasket(String name, double quantity, double price, String image) throws SQLException {
        connection = DBHandler.getConnection();

        if (checkIfProductExistsInShoppingBasket(name) == 0) {
            PreparedStatement preparedStatement = connection.prepareStatement(Queries.ADD_PRODUCT_IN_THE_BASKET);
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, quantity);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, image);

            preparedStatement.executeUpdate();
            // DBHandler.closeConnections(preparedStatement, connection);
        } else {
            double quantityInStock = checkIfProductExistsInShoppingBasket(name);
            double totalQuantity = quantityInStock + quantity;
            PreparedStatement preparedStatement = connection.prepareStatement(Queries.UPDATE_SHOPPING_BASKET_QUANTITY);
            preparedStatement.setDouble(1, totalQuantity);
            preparedStatement.setString(2, name);

            preparedStatement.executeUpdate();
            // DBHandler.closeConnections(preparedStatement, connection);
        }
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
        return products;
    }

    public void removeProductFromShoppingBasket(String name) throws SQLException {
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.DELETE_FROM_SHOPPING_BASKET);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();

        DBHandler.closeConnections(preparedStatement, connection);
    }

    public int checkIfProductExistsInShoppingBasket(String name) throws SQLException {
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.CHECK_PRODUCT_EXISTS_IN_THE_SHOPPING_BASKET);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int quantity = resultSet.getInt("quantity");
            return quantity;
        } else {
            return 0;
        }
    }

    public int getShoppingCartSize() throws SQLException {
        int basketSize = 0;
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_SHOPPING_CART_SIZE);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            basketSize = resultSet.getInt(1);
            //  DBHandler.closeConnections(resultSet, preparedStatement, connection);
        }
        return basketSize;
    }

    public double getSubTotal() throws SQLException {
        double subTotal = 0;
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_SUBTOTAL);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            subTotal = resultSet.getDouble(1);
            //  DBHandler.closeConnections(resultSet, preparedStatement, connection);
        }
        return subTotal;
    }


//    public void updatePrice(String name, double totalPrice) throws SQLException {
//        connection = DBHandler.getConnection();
//
//        PreparedStatement preparedStatement = connection.prepareStatement(Queries.UPDATE_SHOPPING_BASKET);
//        preparedStatement.setDouble(1, totalPrice);
//        preparedStatement.setString(2, name);
//
//        preparedStatement.executeUpdate();
//    }
//
//    public void getTotalPrice(String name) throws SQLException {
//        connection = DBHandler.getConnection();
//
//        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_TOTAL_PRICE);
//        preparedStatement.setString(1, name);
//
//        preparedStatement.executeUpdate();
//    }

    public double getQuantity(String name) throws SQLException {
        double quantity = 0;
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_QTY);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            quantity = resultSet.getInt("quantity");
        }
        return quantity;
    }

    public void clearBasket() throws SQLException {
        try {
            connection = DBHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Queries.CLEAR_SHOPPING_BASKET);

            preparedStatement.executeUpdate();
            DBHandler.closeConnections(preparedStatement, connection);
        } catch (SQLException e) {
            System.out.println("Something went wrong! PLease wait");
            clearBasket();
        }

    }
}
