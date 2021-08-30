package service;

import controller.ViewController;
import database.DBHandler;
import database.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Users;
import types.UserType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService extends ViewController {
    private Connection connection = DBHandler.getConnection();

    public void signUpUser(Users users, ActionEvent event) throws SQLException, IOException {

        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.CHECK_USERNAME);
        preparedStatement.setString(1, users.getUsername());
        ResultSet resultSet = preparedStatement.executeQuery();

        //isBeforeFirst - checks if resultSet is empty
        //if true - username is already taken
        if (resultSet.isBeforeFirst()) {
            showAlert("User already exists!", "You cannot use this username", Alert.AlertType.ERROR);
        } else {
            //if username isn't taken - insert into the database
            preparedStatement = connection.prepareStatement(Queries.CREATE_USER);
            preparedStatement.setString(1, users.getUsername());
            preparedStatement.setString(2, users.getPassword());
            preparedStatement.setString(3, users.getName());
            preparedStatement.setString(4, users.getEmail());
            preparedStatement.setDouble(5, users.getBudget());
            preparedStatement.setString(6, String.valueOf(users.getUserType()));
            preparedStatement.executeUpdate();

            showAlert("Registration successful", "Registration successful, login to continue", Alert.AlertType.CONFIRMATION);
            changeScene(event, "login");
            DBHandler.closeConnections(resultSet, preparedStatement, connection);

        }
    }

    public void logInUser(ActionEvent event, String username, String password) throws SQLException, IOException {

        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.CHECK_USER_EXISTS_USERNAME);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();

        //if user is not in the database
        if (!resultSet.isBeforeFirst()) {
            showAlert("User not found in the database!", "Provided credentials are incorrect!", Alert.AlertType.ERROR);
        } else {
            while (resultSet.next()) {
                double retrievedBudget = resultSet.getDouble("budget");
                String retrieveUserType = resultSet.getString("user_type");

                if (retrieveUserType.equals("CUSTOMER")) {
                    changeSceneForCustomer(event, "market", username, retrievedBudget);
                } else if (retrieveUserType.equals("SALES_MANAGER")) {
                    changeSceneForAdminAndSalesManager(event, "salesManagerPanel", username);
                } else if (retrieveUserType.equals("ADMINISTRATOR")) {
                    changeSceneForAdminAndSalesManager(event, "administratorPanel", username);
                } else {
                    showAlert("Incorrect username or password", "The provided credentials are incorrect!", Alert.AlertType.ERROR);
                }
            }
        }
        DBHandler.closeConnections(resultSet, preparedStatement, connection);
    }

    public ObservableList<Users> getAllUsers() throws SQLException {
        connection = DBHandler.getConnection();
        ObservableList<Users> users = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_ALL_USERS);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            users.add(new Users(resultSet.getInt("user_id"),
                    resultSet.getString("username"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getDouble("budget"),
                    UserType.valueOf(resultSet.getString("user_type"))));
        }
        //   DBHandler.closeConnections(resultSet, preparedStatement, connection);
        return users;
    }

    public void createUser(Users users) throws SQLException {
        connection = DBHandler.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(Queries.CREATE_USER);
        preparedStatement.setString(1, users.getUsername());
        preparedStatement.setString(2, users.getPassword());
        preparedStatement.setString(3, users.getName());
        preparedStatement.setString(4, users.getEmail());
        preparedStatement.setDouble(5, users.getBudget());
        preparedStatement.setString(6, String.valueOf(users.getUserType()));
        preparedStatement.executeUpdate();

        showAlert("User Created", "User created successfully", Alert.AlertType.CONFIRMATION);
        DBHandler.closeConnections(preparedStatement, connection);
    }


    public void deleteUser(Integer id) throws SQLException {
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.DELETE_USER);
        preparedStatement.setString(1, String.valueOf(id));
        preparedStatement.executeUpdate();
        //   showAlert("User Deleted", "User deleted successfully", Alert.AlertType.CONFIRMATION);
        DBHandler.closeConnections(preparedStatement, connection);
    }

    public void editUser(String username, String name, String email, double budget, UserType userType, Integer id) throws SQLException {
        connection = DBHandler.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.UPDATE_USER);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, email);
        preparedStatement.setDouble(4, budget);
        preparedStatement.setString(5, String.valueOf(userType));
        preparedStatement.setInt(6, id);

        preparedStatement.executeUpdate();
        DBHandler.closeConnections(preparedStatement, connection);

    }
}
