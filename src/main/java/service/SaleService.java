package service;

import controller.ViewController;
import database.DBHandler;
import database.Queries;

import model.Delivery;
import model.Product;
import model.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleService extends ViewController {
    private Connection connection = DBHandler.getConnection();

    public void createDelivery(Delivery delivery) throws SQLException {
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.CREATE_DELIVERY_DETAILS);
        preparedStatement.setString(1, delivery.getFirstName());
        preparedStatement.setString(2, delivery.getLastName());
        preparedStatement.setString(3, delivery.getEmail());
        preparedStatement.setString(4, delivery.getMobileNumber());
        preparedStatement.setString(5, delivery.getHouseFlatNumber());
        preparedStatement.setString(6, delivery.getStreetName());
        preparedStatement.setString(7, delivery.getZipCode());
        preparedStatement.setString(8, delivery.getCity());
        preparedStatement.setString(9, delivery.getNote());
//        preparedStatement.setString(10, delivery.ge());
//        preparedStatement.setString(11, delivery.getNote());
        preparedStatement.executeUpdate();

//        showAlert("User Created", "User created successfully", Alert.AlertType.INFORMATION);
        DBHandler.closeConnections(preparedStatement, connection);
    }

    public List<Sale> getAllCustomerSales() throws SQLException {
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_ALL_CUSTOMER_SALES);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Sale> sales = new ArrayList<>();
        while (resultSet.next()) {
            sales.add(new Sale(
                    resultSet.getString("order_number"),
                    resultSet.getString("product"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("price"),
                    resultSet.getString("image"),
                    resultSet.getString("username"),
                    resultSet.getDate("purchase_date")));
        }
        return sales;
    }

    public List<Sale> getCustomerSales(String username) throws SQLException {
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_CUSTOMER_SALES);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Sale> sales = new ArrayList<>();
        while (resultSet.next()) {
            sales.add(new Sale(
                    resultSet.getString("order_number"),
                    resultSet.getString("product"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("price"),
                    resultSet.getString("image"),
                    resultSet.getString("username"),
                    resultSet.getDate("purchase_date")));
        }
        return sales;
    }

    public void createSale(String orderNumber, String productName, double quantity,
                           String username, String currentDate, double pricePerUnit, String image) throws SQLException {
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.CREATE_ORDER);
        preparedStatement.setString(1, orderNumber);
        preparedStatement.setString(2, productName);
        preparedStatement.setDouble(3, quantity);
        preparedStatement.setString(4, username);
        preparedStatement.setString(5, currentDate);
        preparedStatement.setDouble(6, pricePerUnit);
        preparedStatement.setString(7, image);
        preparedStatement.executeUpdate();

        DBHandler.closeConnections(preparedStatement, connection);

    }
}
