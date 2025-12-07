package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.db.DBConnect;
import vn.edu.hcmuaf.fit.enums.Role;
import vn.edu.hcmuaf.fit.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    // 1. Hàm tìm user (Cập nhật tên cột)
    public Account findByUsername(String username) {

        String query = "SELECT * FROM accounts WHERE Username = ?";
        try {
            Connection conn = DBConnect.get();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("AccountID"),        // Cột AccountID
                        rs.getString("Username"),      // Cột Username
                        rs.getString("PasswordHash"),  // Cột PasswordHash
                        rs.getString("Email"),         // Cột Email
                        rs.getString("Role"),          // Cột Role (Lấy String: "Customer" hoặc "Admin")
                        rs.getString("Status"),        // Cột Status (Lấy String: "Active")
                        rs.getTimestamp("CreatedAt")   // Cột CreatedAt
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 2. Hàm Đăng Ký (Cập nhật tên cột và cách insert Enum)
    public boolean register(String username, String password, String email) {
        // Cập nhật tên cột cho khớp với bảng accounts
        String query = "INSERT INTO accounts (Username, PasswordHash, Email, Role, Status) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnect.get();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password); // Password đã hash
            ps.setString(3, email);

            // QUAN TRỌNG: Truyền chuỗi "Customer" vào DB
            ps.setString(4, Role.CUSTOMER.getValue());

            // Status mặc định là "Active"
            ps.setString(5, "Active");

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Hàm kiểm tra user tồn tại
    public boolean checkUsernameExist(String username) {
        String query = "SELECT AccountID FROM accounts WHERE Username = ?";
        try {
            Connection conn = DBConnect.get();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}