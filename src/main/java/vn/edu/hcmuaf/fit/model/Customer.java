package vn.edu.hcmuaf.fit.model;

import java.io.Serializable;

public class Customer implements Serializable {
    private int customerID;
    private int accountID; // Khóa ngoại liên kết với User
    private String fullName;
    private String phoneNumber;
    private String address;

    public Customer() {}

    // Constructor dùng để tạo đối tượng khi đọc từ DB
    public Customer(int customerID, int accountID, String fullName, String phoneNumber, String address) {
        this.customerID = customerID;
        this.accountID = accountID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters...
    public int getCustomerID() { return customerID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    public int getAccountID() { return accountID; }
    public void setAccountID(int accountID) { this.accountID = accountID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}