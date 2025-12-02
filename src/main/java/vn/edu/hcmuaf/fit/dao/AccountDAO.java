package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.db.DBConnect;
import vn.edu.hcmuaf.fit.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    // 1. Hàm Đăng Nhập (Login)
    public Account login(String username, String password) {
        String query = "SELECT * FROM accounts WHERE username = ? AND password = ?";
        try {
            Connection conn = DBConnect.get();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password); // Nếu có mã hóa thì phải mã hóa password trước khi truyền vào đây
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Tạo đối tượng Account từ dữ liệu lấy được
                return new Account(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getInt("role"),
                        rs.getInt("status"),
                        rs.getTimestamp("createdAt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Đăng nhập thất bại
    }

    // 2. Hàm Đăng Ký (Register)
    public boolean register(String username, String password, String email) {
        String query = "INSERT INTO accounts (username, password, email, role, status) VALUES (?, ?, ?, 0, 1)";
        try {
            Connection conn = DBConnect.get();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password); // Nhớ mã hóa nếu cần
            ps.setString(3, email);
            
            // role mặc định là 0 (User), status mặc định là 1 (Active)
            
            int row = ps.executeUpdate();
            return row > 0; // Trả về true nếu insert thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // 3. Hàm kiểm tra user tồn tại (để tránh đăng ký trùng)
    public boolean checkUsernameExist(String username) {
        String query = "SELECT id FROM accounts WHERE username = ?";
        try {
            Connection conn = DBConnect.get();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // True nếu đã tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}