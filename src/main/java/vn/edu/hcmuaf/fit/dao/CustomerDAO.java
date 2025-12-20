package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.db.DBConnect;
import vn.edu.hcmuaf.fit.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for handling Customer operations
 */
public class CustomerDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Retrieve all customers from database
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try {
            conn = DBConnect.get();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("CustomerID"),
                        rs.getInt("AccountID"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Address")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Retrieve customer by specific ID
    public Customer getById(int id) {
        String query = "SELECT * FROM customers WHERE CustomerID = ?";
        try {
            conn = DBConnect.get();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("CustomerID"),
                        rs.getInt("AccountID"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Hàm cập nhật thông tin cá nhân khách hàng
     * Dựa vào AccountID để xác định dòng cần sửa
     */
    public boolean updateCustomerInfo(int accountId, String fullName, String phone, String address) {
        String query = "UPDATE customers SET FullName = ?, PhoneNumber = ?, Address = ? WHERE AccountID = ?";
        try {
            conn = DBConnect.get();
            ps = conn.prepareStatement(query);

            // Set các tham số
            ps.setString(1, fullName);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setInt(4, accountId);

            // Thực thi
            int row = ps.executeUpdate();
            return row > 0; // Trả về true nếu có dòng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Hàm lấy thông tin Customer theo AccountID
     */
    public Customer getCustomerByAccountId(int accountId) {
        String query = "SELECT CustomerID, AccountID, FullName, PhoneNumber, Address FROM customers WHERE AccountID = ?";
        try {
            conn = DBConnect.get();
            ps = conn.prepareStatement(query);
            ps.setInt(1, accountId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("CustomerID"),
                        rs.getInt("AccountID"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
