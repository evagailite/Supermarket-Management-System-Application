package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sale {
    private Integer id;
    private String orderNumber;
    private String productName;
    private int quantity;
    private double price;
    private String image;
    private String username;
    private String orderDate;

    public Sale() {
    }

    public Sale(Integer id, String orderNumber, String productName,
                int quantity, double price, String image,
                String username, String orderDate) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.username = username;
        this.orderDate = orderDate;
    }

    public Sale(Integer orderNumber, String productName, int quantity, double price,
                String username, String orderDate) {
        this.orderNumber = String.valueOf(orderNumber);
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.username = username;
        this.orderDate = orderDate;
    }

    public Sale(String orderNumber, String productName,
                int quantity, double price, String image,
                String username, String orderDate) {
        this.orderNumber = orderNumber;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.username = username;
        this.orderDate = orderDate;
    }

    public Sale(String productName, int quantity, double price, String image, String username, String orderDate) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.username = username;
        this.orderDate = orderDate;
    }

    public Sale(String productName, int quantity, double price, String image) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }

    public Sale(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

}
