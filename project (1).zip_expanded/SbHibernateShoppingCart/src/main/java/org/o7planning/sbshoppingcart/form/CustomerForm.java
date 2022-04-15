package org.o7planning.sbshoppingcart.form;

import org.o7planning.sbshoppingcart.model.CustomerInfo;

public class CustomerForm {

    private String name;
    private String address;
    private String email;
    private String phone;

    private boolean valid;

    public CustomerForm() {}

    // Create a constructor to store the name, address, email and password from the customer form
    public CustomerForm(CustomerInfo customerInfo) {
        if (customerInfo != null) {
            this.name = customerInfo.getName();
            this.address = customerInfo.getAddress();
            this.email = customerInfo.getEmail();
            this.phone = customerInfo.getPhone();
        }
    }

    // Returns the name
    public String getName() {
        return name;
    }

    // Set the name
    public void setName(String name) {
        this.name = name;
    }

    // Returns the email
    public String getEmail() {
        return email;
    }

    // Set the email
    public void setEmail(String email) {
        this.email = email;
    }

    // Return the address
    public String getAddress() {
        return address;
    }

    // Set the address
    public void setAddress(String address) {
        this.address = address;
    }

    // Return the phone number
    public String getPhone() {
        return phone;
    }

    // Set the phone number
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Return the validity of the customer info
    public boolean isValid() {
        return valid;
    }

    // Set the validity of the customer info
    public void setValid(boolean valid) {
        this.valid = valid;
    }
}