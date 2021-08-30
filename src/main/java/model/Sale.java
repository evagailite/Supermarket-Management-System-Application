package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sale {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private String username;

    public Sale(int id, String name, int quantity, double price, String username) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.username = username;
    }

    public Sale() {
    }

}
