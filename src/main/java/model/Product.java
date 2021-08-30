package model;

import types.Category;
import types.ProductUnit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Integer id;
    private String name;
    private double quantity;
    private double pricePerUnit;
    private ProductUnit productUnit;
    private Category category;
    private String image;

    public Product(Integer id, String name, double quantity,
                   double pricePerUnit, ProductUnit productUnit, Category category, String image) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.productUnit = productUnit;
        this.category = category;
        this.image = image;
    }

    public Product(String name, double quantity, double pricePerUnit,
                   ProductUnit productUnit, Category category, String image) {
        this.name = name;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.productUnit = productUnit;
        this.category = category;
        this.image = image;
    }

    public Product(String name, double quantity, double pricePerUnit,
                   String productUnit, String category, String image) {
        this.name = name;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.productUnit = ProductUnit.valueOf(productUnit);
        this.category = Category.valueOf(category);
        this.image = image;
    }

}
