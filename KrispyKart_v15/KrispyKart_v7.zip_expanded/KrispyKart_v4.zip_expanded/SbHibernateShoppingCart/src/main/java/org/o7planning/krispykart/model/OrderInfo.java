package org.o7planning.krispykart.model;

import java.util.Date;
import java.util.List;

public class OrderInfo {

    private String customerName;
    private String customerAddress;
    private String customerEmail;
    private String customerPhone;
    private String id;
    private List<OrderDetailInfo> details;
    private Date orderDate;
    private int orderNum;
    private double amount;

    // Create constructor
    public OrderInfo() {}

    // Create constructor to store id, orderDate, orderNum, amount, customerName, customerAddress, customerEmail, customerPhone
    public OrderInfo(String id, Date orderDate, int orderNum, double amount, String customerName, String customerAddress, String customerEmail, String customerPhone) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderNum = orderNum;
        this.amount = amount;

        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
    }

    // Return order id
    public String getId() {
        return id;
    }

    // Set the order id
    public void setId(String id) {
        this.id = id;
    }

    // Returns the order date
    public Date getOrderDate() {
        return orderDate;
    }

    // Sets the order date
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    // Returns the order number
    public int getOrderNum() {
        return orderNum;
    }

    // Sets the order number
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    // Returns the order amount
    public double getAmount() {
        return amount;
    }

    // Sets the order amount
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Returns the customer name
    public String getCustomerName() {
        return customerName;
    }

    // Sets the customer name
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Returns the customer address
    public String getCustomerAddress() {
        return customerAddress;
    }

    // Sets the customer address
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    
    // Returns the customer email
    public String getCustomerEmail() {
        return customerEmail;
    }

    // Sets the customer email
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    // Returns the customer phone number
    public String getCustomerPhone() {
        return customerPhone;
    }

    // Sets the customer phone number
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    // Returns the customer order details
    public List<OrderDetailInfo> getDetails() {
        return details;
    }

    // Sets the customer order details
    public void setDetails(List<OrderDetailInfo> details) {
        this.details = details;
    }
}