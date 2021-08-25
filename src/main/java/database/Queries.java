package database;

public class Queries {

    public static final String CHECK_USER_EXISTS_USERNAME = "SELECT * FROM user WHERE username = ? && password = ? LIMIT 1";

    public static final String CREATE_USER = "INSERT INTO user (username, password, name, email, budget, user_type)" +
            " VALUES (?, ?, ?, ?, ?, ?)";

    public static final String CHECK_USERNAME = "SELECT * FROM user WHERE username = ?";

    public static final String GET_ALL_USERS = "SELECT user_id, username, name, email, budget, user_type FROM user";
}
