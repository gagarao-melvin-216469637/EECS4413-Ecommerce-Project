package org.o7planning.sbshoppingcart.model;

import org.o7planning.sbshoppingcart.entity.Product;

public class ProductInfo {
	
    private String brand;
    private String name;
    private double price;

    public ProductInfo() {}

    // Create constructor to store code, name and price from product information
    public ProductInfo(Product product) {
        this.brand = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    // Create constructor to store code, name and price
    public ProductInfo(String brand, String name, double price) {
        this.brand = brand;
        this.name = name;
        this.price = price;
    }

    // Returns product code
    public String getCode() {
        return brand;
    }

    // Set product code
    public void setCode(String brand) {
        this.brand = brand;
    }

    // Returns product name
    public String getName() {
        return name;
    }

    // Set product name
    public void setName(String name) {
        this.name = name;
    }

    // Returns product price
    public double getPrice() {
        return price;
    }

    // Set product price
    public void setPrice(double price) {
        this.price = price;
    }
}