package org.o7planning.sbshoppingcart.model;

import org.o7planning.sbshoppingcart.entity.Product;

public class ProductInfo {
	
    private String code;
    private String name;
    private double price;

    public ProductInfo() {}

    // Create constructor to store code, name and price from product information
    public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    // Create constructor to store code, name and price
    public ProductInfo(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    // Returns product code
    public String getCode() {
        return code;
    }

    // Set product code
    public void setCode(String code) {
        this.code = code;
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