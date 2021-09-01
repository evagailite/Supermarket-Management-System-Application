package database;

public class Queries {

    public static final String CHECK_USER_EXISTS_USERNAME = "SELECT * FROM user WHERE username = ? && password = ? LIMIT 1";

    public static final String CREATE_USER = "INSERT INTO user (username, password, name, email, budget, user_type)" +
            " VALUES (?, ?, ?, ?, ?, ?)";

    public static final String CHECK_USERNAME = "SELECT * FROM user WHERE username = ?";

    public static final String GET_ALL_USERS = "SELECT user_id, username, name, email, budget, user_type FROM user";

    public static final String CREATE_PRODUCT = "INSERT INTO product (product_name, quantity, price, unit, category, image) " +
            "VALUES (?, ?, ?, ?, ?, ?);";

    public static final String GET_ALL_PRODUCTS = "SELECT product_id, product_name, quantity, price, unit, category, image FROM product";

    public static final String DELETE_PRODUCT = "DELETE FROM product WHERE product_id = ?";

    public static final String DELETE_USER = "DELETE FROM user WHERE user_id = ?;";

    public static final String UPDATE_USER = "UPDATE user SET username = ?, name = ?, email = ?, budget = ?, user_type =? WHERE user_id = ?";

    public static final String UPDATE_PRODUCT = "UPDATE product SET product_name = ?, quantity = ?, price = ?, " +
            "unit = ?, category = ?, image = ? WHERE product_id = ?";


    public static final String ADD_PRODUCT_IN_THE_BASKET = "INSERT INTO basket (name, quantity, price, image) VALUES (?, ?, ?, ?)";
    public static final String GET_ALL_SHOPPING_BASKET_PRODUCTS = "SELECT name, quantity, price, image FROM basket;";
    public static final String DELETE_FROM_SHOPPING_BASKET = "DELETE FROM basket WHERE name = ?";
}
