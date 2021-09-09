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

public class DeliveryService extends ViewController {
    private Connection connection = DBHandler.getConnection();

    public void createDelivery(Delivery delivery, String username, int orderNumber) throws SQLException {
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
        preparedStatement.setString(10, username);
        preparedStatement.setInt(11, orderNumber);
        preparedStatement.executeUpdate();

//        showAlert("User Created", "User created successfully", Alert.AlertType.INFORMATION);
        DBHandler.closeConnections(preparedStatement, connection);
    }

    public List<Delivery> getAllDeliveryDetails(int orderNumber) throws SQLException {
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_ALL_DELIVERY_DETAILS);
        preparedStatement.setInt(1, orderNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Delivery> deliveries = new ArrayList<>();
        while (resultSet.next()) {
            deliveries.add(new Delivery(
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("mobile_number"),
                    resultSet.getString("house_number"),
                    resultSet.getString("street"),
                    resultSet.getString("zip_code"),
                    resultSet.getString("city"),
                    resultSet.getString("note"),
                    resultSet.getString("username"),
                    resultSet.getInt("order_number")
            ));
        }
        return deliveries;
    }
}
