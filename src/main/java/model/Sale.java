package model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

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
    private Date orderDate;

    public Sale() {
    }

    public Sale(Integer id, String orderNumber, String productName,
                int quantity, double price, String image,
                String username, Date orderDate) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.username = username;
        this.orderDate = orderDate;
    }

    public Sale(String orderNumber, String productName,
                int quantity, double price, String image,
                String username, Date orderDate) {
        this.orderNumber = orderNumber;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.username = username;
        this.orderDate = orderDate;
    }
}
