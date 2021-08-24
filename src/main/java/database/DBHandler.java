package database;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.sql.*;

public class DBHandler {

    private static String pass;
    private static String user;
    private static String connectionURl;
    private static Connection connection;

    public DBHandler() {
        getSetDatabaseInfo();
    }

    private static void getSetDatabaseInfo() {
        PropertiesConfiguration databaseProperties = new PropertiesConfiguration();
        try {
            databaseProperties.load("database.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        String host = databaseProperties.getString("database.host");
        String port = databaseProperties.getString("database.port");
        String dbName = databaseProperties.getString("database.name");

        user = databaseProperties.getString("database.user");
        pass = databaseProperties.getString("database.password");
        connectionURl = host + ":" + port + "/" + dbName;
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                getSetDatabaseInfo();
                connection = DriverManager.getConnection(connectionURl, user, pass);
            }
        } catch (SQLException exception) {
            System.out.println("Unable to connect to database");
            exception.printStackTrace();
        }
        return connection;
    }

    public static void closeConnections(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeConnections(PreparedStatement preparedStatement, Connection connection) {
        try {
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
