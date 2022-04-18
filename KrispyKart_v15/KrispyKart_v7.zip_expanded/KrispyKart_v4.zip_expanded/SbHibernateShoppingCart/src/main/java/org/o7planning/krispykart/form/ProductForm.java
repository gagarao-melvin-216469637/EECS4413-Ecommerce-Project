package org.o7planning.krispykart.form;

import org.o7planning.krispykart.entity.Product;
import org.springframework.web.multipart.MultipartFile;

public class ProductForm {
    private String code;
    private String name;
    private double price;

    private boolean newProduct = false;

    // Upload the file
    private MultipartFile fileData;

    // Create a constructor
    public ProductForm() {
        this.newProduct= true;
    }

    // Create a constructor to store the code, name and price of the product
    public ProductForm(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
    }
    
    // Returns the code
    public String getCode() {
        return code;
    }

    // Set the code
    public void setCode(String code) {
        this.code = code;
    }

    // Returns the name
    public String getName() {
        return name;
    }

    // Set the name
    public void setName(String name) {
        this.name = name;
    }

    // Returns the price
    public double getPrice() {
        return price;
    }

    // Set the price
    public void setPrice(double price) {
        this.price = price;
    }

    // Returns the file data
    public MultipartFile getFileData() {
        return fileData;
    }

    // Set the file data
    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }

    // Checks if it's a new product
    public boolean isNewProduct() {
        return newProduct;
    }

    // Sets it as a new product
    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }
}