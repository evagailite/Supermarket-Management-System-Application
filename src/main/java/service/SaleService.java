package service;

import controller.ViewController;
import database.DBHandler;
import database.Queries;

import model.Delivery;
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
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_CUSTOMER_ORDER_SALES);
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

    public String getCustomerOrderSales(String username) throws SQLException {
        connection = DBHandler.getConnection();
        String order = null;
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_CUSTOMER_ORDER_SALES);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            order = resultSet.getString("order_number");
        }
        return order;
    }

    public int getSalesOrderNumber(String username) throws SQLException {
        connection = DBHandler.getConnection();
        int orderNumber = 0;
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_CUSTOMER_ORDER_NUMBER);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            orderNumber = Integer.parseInt(resultSet.getString("count(distinct order_number)"));
        }
        return orderNumber;
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

    public List<Sale> getCustomerSalesProducts(String username) throws SQLException {
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_CUSTOMER_SALES_PRODUCTS);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Sale> sales = new ArrayList<>();
        while (resultSet.next()) {
            sales.add(new Sale(
                    resultSet.getString("product"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("price"),
                    resultSet.getString("image")));
        }
        return sales;
    }

    public List<Sale> getCustomerSalesProductsByOrderNumber(String username, String orderNumber) throws SQLException {
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_CUSTOMER_SALES_PRODUCTS_BY_ORDER_NUMBER);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, orderNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Sale> sales = new ArrayList<>();
        while (resultSet.next()) {
            sales.add(new Sale(
                    resultSet.getString("product"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("price"),
                    resultSet.getString("image")));
        }
        return sales;
    }

    public int getLastOrderNumber() throws SQLException {
        connection = DBHandler.getConnection();
        int orderId = 0;
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_LAST_ORDER_NUMBER);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            orderId = resultSet.getInt(1);
        }
        return orderId;
    }

    public List<Sale> getBestSellingItems() throws SQLException {
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_BEST_SELLING_ITEMS);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Sale> sales = new ArrayList<>();
        while (resultSet.next()) {
            sales.add(new Sale(
                    resultSet.getString(1),
                    resultSet.getInt(2)
            ));
        }
        return sales;
    }

    public int getSalesCount() throws SQLException {
        int total = 0;
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_SALES_COUNT);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            total = resultSet.getInt(1);
            //  DBHandler.closeConnections(resultSet, preparedStatement, connection);
        }
        return total;
    }

}
