package database;

public class Queries {

    public static final String CHECK_USER_EXISTS_USERNAME = "SELECT * FROM user WHERE username = ? && password = ? LIMIT 1";

    public static final String CREATE_USER = "INSERT INTO user (username, password, name, email, budget, user_type, isOnline)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?)";

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

    public static final String ADD_PRODUCT_IN_THE_BASKET = "INSERT INTO basket (name, quantity, price, image, username) VALUES (?, ?, ?, ?, ?)";
    public static final String GET_ALL_SHOPPING_BASKET_PRODUCTS = "SELECT name, quantity, price, image FROM basket;";
    public static final String DELETE_FROM_SHOPPING_BASKET = "DELETE FROM basket WHERE name = ?";
    public static final String UPDATE_SHOPPING_BASKET_QUANTITY = "UPDATE basket SET quantity = ? WHERE name = ?;";
    public static final String UPDATE_SHOPPING_BASKET = "UPDATE basket SET price = ? WHERE name = ?";
    public static final String GET_QTY = "SELECT quantity FROM basket WHERE name = ?";
    public static final String SET_USER_ONLINE_STATUS = "UPDATE user SET isOnline = ? WHERE username = ?";
    public static final String SET_ONLINE_USER = "SELECT username FROM user WHERE isOnline =?";


    public static String CHECK_PRODUCT_EXISTS_IN_THE_SHOPPING_BASKET = "SELECT quantity FROM basket WHERE name = ?";

    public static String GET_SHOPPING_CART_SIZE = "SELECT count(basket_id) FROM basket";

    public static String GET_FOOD_PRODUCTS = "SELECT product_id, product_name, quantity, price, unit, category, image FROM product WHERE category = 'FOOD'";
    public static String GET_NON_FOOD_PRODUCTS = "SELECT product_id, product_name, quantity, price, unit, category, image FROM product WHERE category = 'NON_FOOD'";
    public static String GET_DRINK_PRODUCTS = "SELECT product_id, product_name, quantity, price, unit, category, image FROM product WHERE category = 'DRINK'";

    public static String CLEAR_SHOPPING_BASKET = "DELETE FROM basket";

    public static String GET_TOTAL_PRICE = "SELECT SUM(quantity*price) FROM basket WHERE name = ?";

    public static String GET_SUBTOTAL = "SELECT SUM(quantity*price) FROM basket";

    public static final String CREATE_DELIVERY_DETAILS = "INSERT INTO delivery (first_name, last_name, email, mobile_number, house_number, street, zip_code, city, note)\n" +
            " VALUES (?, ?, ?,?,?,?,?,?,?)";
    public static final String GET_ALL_CUSTOMER_SALES = "SELECT order_number, product, quantity, price, image, user, purchase_date FROM sales";
}
