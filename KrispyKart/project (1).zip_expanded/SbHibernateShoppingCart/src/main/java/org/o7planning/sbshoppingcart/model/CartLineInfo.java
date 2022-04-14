package org.o7planning.sbshoppingcart.model;
 

public class CartLineInfo {
 
	private int quantity;
    private ProductInfo productInfo;

    // Initially the quantity is set to zero
    public CartLineInfo() {
        this.quantity = 0;
    }
 
    // Get the product information
    public ProductInfo getProductInfo() {
        return productInfo;
    }
 
    // Set the product information
    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
 
    // Get the quantity
    public int getQuantity() {
        return quantity;
    }
 
    // Set the quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
 
    // Get the amount of each product (price * quantity)
    public double getAmount() {
        return this.productInfo.getPrice() * this.quantity;
    }
}