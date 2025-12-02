package vn.edu.hcmuaf.fit.model;

import java.sql.*;
import vn.edu.hcmuaf.fit.db.DBConnect;

public class UserDAO {

    public boolean register(User u) {
        String sql = "INSERT INTO users(phone, password, fullname) VALUES (?, ?, ?)";

        try (Connection conn = DBConnect.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getPhone());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullname());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User login(String phone, String pass) {
        String sql = "SELECT * FROM users WHERE phone=? AND password=?";

        try (Connection conn = DBConnect.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phone);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setPhone(rs.getString("phone"));
                u.setFullname(rs.getString("fullname"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
