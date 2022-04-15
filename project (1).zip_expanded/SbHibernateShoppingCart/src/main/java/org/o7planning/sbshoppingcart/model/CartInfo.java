package org.o7planning.sbshoppingcart.model;

import java.util.ArrayList;
import java.util.List;

public class CartInfo {

	private final List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();
	private CustomerInfo customerInfo;
    private int orderNum;

    public CartInfo() {}

    // Returns order number
    public int getOrderNum() {
        return orderNum;
    }

    // Set the order number
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    // Returns customer info
    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    // Set the customer info
    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    // Get shopping cart information
    public List<CartLineInfo> getCartLines() {
        return this.cartLines;
    }

    // Search for shopping cart information based on product code
    private CartLineInfo findLineByCode(String code) {
        for (CartLineInfo line : this.cartLines) {
            if (line.getProductInfo().getCode().equals(code)) {
                return line;
            }
        }
        return null;
    }

    // Add product information along with its associated quantity
    public void addProduct(ProductInfo productInfo, int quantity) {
        CartLineInfo line = this.findLineByCode(productInfo.getCode());

        if (line == null) {
            line = new CartLineInfo();
            line.setQuantity(0);
            line.setProductInfo(productInfo);
            this.cartLines.add(line);
        }
        int newQuantity = line.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.cartLines.remove(line);
        } else {
            line.setQuantity(newQuantity);
        }
    }

    public void validate() {}

    // Update the product based on new product code and quantity
    public void updateProduct(String code, int quantity) {
        CartLineInfo line = this.findLineByCode(code);

        if (line != null) {
            if (quantity <= 0) {
                this.cartLines.remove(line);
            } else {
                line.setQuantity(quantity);
            }
        }
    }

    // Search for the given product information and remove that corresponding product
    public void removeProduct(ProductInfo productInfo) {
        CartLineInfo line = this.findLineByCode(productInfo.getCode());
        if (line != null) {
            this.cartLines.remove(line);
        }
    }

    // Check if the shopping cart is empty
    public boolean isEmpty() {
        return this.cartLines.isEmpty();
    }

    // Check if the customer information is valid
    public boolean isValidCustomer() {
        return this.customerInfo != null && this.customerInfo.isValid();
    }

    // Get the total quantity for each shopping cart information
    public int getQuantityTotal() {
        int quantity = 0;
        for (CartLineInfo line : this.cartLines) {
            quantity += line.getQuantity();
        }
        return quantity;
    }

    // Get the total amount for each shopping cart information
    public double getAmountTotal() {
        double total = 0;
        for (CartLineInfo line : this.cartLines) {
            total += line.getAmount();
        }
        return total;
    }

    // Update the quantity for each shopping cart information
    public void updateQuantity(CartInfo cartForm) {
        if (cartForm != null) {
            List<CartLineInfo> lines = cartForm.getCartLines();
            for (CartLineInfo line : lines) {
                this.updateProduct(line.getProductInfo().getCode(), line.getQuantity());
            }
        }

    }
}