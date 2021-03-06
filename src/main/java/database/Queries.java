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
    public static final String DELETE_USER_BY_USERNAME = "DELETE FROM user WHERE username = ?;";
    public static final String UPDATE_USER = "UPDATE user SET username = ?, name = ?, email = ?, budget = ?, user_type =? WHERE user_id = ?";
    public static final String UPDATE_PRODUCT = "UPDATE product SET product_name = ?, quantity = ?, price = ?, " +
            "unit = ?, category = ?, image = ? WHERE product_id = ?";
    public static final String ADD_PRODUCT_IN_THE_BASKET = "INSERT INTO basket (name, quantity, price, image, username) VALUES (?, ?, ?, ?, ?)";
    public static final String GET_ALL_SHOPPING_BASKET_PRODUCTS = "SELECT name, quantity, price, image FROM basket;";
    public static final String DELETE_FROM_SHOPPING_BASKET = "DELETE FROM basket WHERE name = ?";
    public static final String UPDATE_SHOPPING_BASKET_QUANTITY = "UPDATE basket SET quantity = ? WHERE name = ?;";
    public static final String UPDATE_SHOPPING_BASKET = "UPDATE basket SET price = ? WHERE name = ?";
    public static final String GET_BASKET_QTY = "SELECT quantity FROM basket WHERE name = ?";
    public static final String SET_USER_ONLINE_STATUS = "UPDATE user SET isOnline = ? WHERE username = ?";
    public static final String SET_ONLINE_USER = "SELECT username FROM user WHERE isOnline =?";
    public static final String CREATE_ORDER = "INSERT INTO sales (order_number, product, quantity, username, purchase_date, price, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String CHECK_PRODUCT_EXISTS_IN_THE_SHOPPING_BASKET = "SELECT quantity FROM basket WHERE name = ?";
    public static final String GET_SHOPPING_CART_SIZE = "SELECT count(basket_id) FROM basket";
    public static final String GET_FOOD_PRODUCTS = "SELECT product_id, product_name, quantity, price, unit, category, image FROM product WHERE category = 'FOOD'";
    public static final String GET_NON_FOOD_PRODUCTS = "SELECT product_id, product_name, quantity, price, unit, category, image FROM product WHERE category = 'NON_FOOD'";
    public static final String GET_DRINK_PRODUCTS = "SELECT product_id, product_name, quantity, price, unit, category, image FROM product WHERE category = 'DRINK'";
    public static final String CLEAR_SHOPPING_BASKET = "DELETE FROM basket";
    public static final String GET_TOTAL_PRICE = "SELECT SUM(quantity*price) FROM basket WHERE name = ?";
    public static final String GET_SUBTOTAL = "SELECT SUM(quantity*price) FROM basket";
    public static final String GET_CUSTOMER_ORDER_SUBTOTAL = "SELECT SUM(quantity*price) FROM sales WHERE order_number =?";
    public static final String CREATE_DELIVERY_DETAILS = "INSERT INTO delivery (first_name, last_name, email, mobile_number, house_number, street, zip_code, city, note, username, order_number)\n" +
            " VALUES (?, ?, ?,?,?,?,?,?,?, ?, ?)";
    public static final String GET_ALL_CUSTOMER_SALES = "SELECT order_number, product, quantity, price, image, username, purchase_date FROM sales";
    public static final String CHECK_PRODUCT_EXISTS_IN_THE_SALES = "SELECT order_number FROM sales WHERE USERNAME = ?";
    public static final String GET_CUSTOMER_SALES = "SELECT order_number, product, quantity, price, image, username, purchase_date FROM sales WHERE username = ?";
    public static final String GET_CUSTOMER_ORDER_SALES = "SELECT DISTINCT *  FROM sales WHERE username = ? GROUP BY order_number";
    public static final String GET_CUSTOMER_ORDER_NUMBER = "SELECT count(distinct order_number) FROM sales WHERE username = ?";
    public static final String GET_CUSTOMER_SALES_PRODUCTS = "SELECT product, quantity, price, image FROM sales WHERE username = ?";
    public static final String GET_CUSTOMER_SALES_PRODUCTS_BY_ORDER_NUMBER = "SELECT product, quantity, price, image FROM sales WHERE username = ? AND order_number = ?";
    public static final String GET_LAST_ORDER_NUMBER = "SELECT order_number FROM sales ORDER BY sales_id desc LIMIT 1";
    public static final String GET_BEST_SELLING_ITEMS = "SELECT product, SUM(quantity) AS TOTAL_QTY FROM sales GROUP BY product ORDER BY TOTAL_QTY DESC LIMIT 3";
    public static final String GET_SALES_COUNT = "SELECT count(distinct order_number) FROM sales";
    public static final String GET_USER_COUNT = "SELECT count(distinct user_id) FROM user";
    public static final String GET_PRODUCT_COUNT = "SELECT count(distinct product_id) FROM product";
    public static final String GET_PRODUCTS_NAME = "SELECT product_name FROM product";
    public static final String GET_ALL_DELIVERY_DETAILS = "SELECT first_name, last_name, email, mobile_number, house_number, street, zip_code, city, note, username, order_number from delivery " +
            "WHERE order_number = ?";
    public static final String GET_SALES_SUBTOTAL = "SELECT SUM(quantity*price) FROM sales WHERE order_number = ?";
    public static final String ORDER_PRODUCTS_BY_HIGHEST_PRICE = "SELECT * FROM product ORDER by price desc";
    public static final String ORDER_PRODUCTS_BY_LOWEST_PRICE = "SELECT * FROM product ORDER by price asc";
    public static final String GET_SALES_TOTAL = "SELECT SUM(quantity*price) FROM sales";
    public static final String GET_ALL_SALES = "SELECT order_number, product, quantity, username, purchase_date, price from sales";
    public static final String GET_PRODUCT_QUANTITY = "select quantity from product where product_name =?";
    public static final String UPDATE_PRODUCT_QUANTITY = "UPDATE product SET quantity = ? WHERE product_name = ?";
    public static final String GET_ORDER_NUMBER = "SELECT order_number FROM sales WHERE username = ? ORDER BY sales_id desc LIMIT 1";

    public static final String GET_SALES_DATE = "SELECT purchase_date FROM sales WHERE order_number = ?";
    public static final String GET_USER_EMAIL = "SELECT email FROM user WHERE username = ?";

    public static final String UPDATE_USER_USERNAME = "UPDATE user SET username = ? WHERE user_id = ?";
    public static final String GET_USER_ID = "SELECT user_id FROM user WHERE username=?";
    public static final String UPDATE_USER_EMAIL = "UPDATE user SET email = ? WHERE user_id = ?";
    public static final String UPDATE_USER_USERNAME_AND_EMAIL = "UPDATE user SET username = ?, email = ? WHERE user_id = ?";
    public static final String UPDATE_USER_PASSWORD = "UPDATE user SET password = ? WHERE username = ?";
    public static final String GET_PASSWORD = "SELECT password FROM user WHERE username=?";
    public static final String UPDATE_SALE_USERNAME = "UPDATE sales SET username = ? WHERE username = ?";
}
