package service;

import controller.ViewController;
import database.DBHandler;
import database.Queries;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Sale;
import model.Users;
import types.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleService extends ViewController {
    private Connection connection = DBHandler.getConnection();

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
                    resultSet.getString("purchase_date")));
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
                    resultSet.getString("purchase_date")));
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

    public void createSale(int orderNumber, String productName, double quantity,
                           String username, String currentDate, double pricePerUnit, String image) throws SQLException {
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.CREATE_ORDER);
        preparedStatement.setInt(1, orderNumber);
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

    public double getSaleSubTotal(int orderNumber) throws SQLException {
        double subTotal = 0;
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_SALES_SUBTOTAL);
        preparedStatement.setInt(1, orderNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            subTotal = resultSet.getDouble(1);
            //  DBHandler.closeConnections(resultSet, preparedStatement, connection);
        }
        return subTotal;
    }

    public double getAllSalesTotal() throws SQLException {
        double total = 0;
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_SALES_TOTAL);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            total = resultSet.getDouble(1);
            //  DBHandler.closeConnections(resultSet, preparedStatement, connection);
        }
        return total;
    }

    public ObservableList<Sale> getAllSales() throws SQLException {
        connection = DBHandler.getConnection();
        ObservableList<Sale> saleOrders = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_ALL_SALES);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            saleOrders.add(new Sale(resultSet.getInt("order_number"),
                    resultSet.getString("product"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("price"),
                    resultSet.getString("username"),
                    resultSet.getString("purchase_date")));
        }
        //   DBHandler.closeConnections(resultSet, preparedStatement, connection);
        return saleOrders;
    }
}
