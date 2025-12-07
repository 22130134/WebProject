package vn.edu.hcmuaf.fit.model;

import vn.edu.hcmuaf.fit.enums.Role;
import java.sql.Timestamp;

public class Account {
    private int id; // Map với AccountID
    private String username; // Map với Username
    private String password; // Map với PasswordHash
    private String email; // Map với Email
    private Role role; // Map với Role
    private String status; // Map với Status
    private Timestamp createdAt; // Map với CreatedAt

    public Account() {}

    // Constructor cập nhật
    public Account(int id, String username, String password, String email, String roleStr, String status, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;

        // Chuyển chuỗi "Customer"/"Admin" từ DB thành Enum Java
        this.role = Role.fromValue(roleStr);

        this.status = status;
        this.createdAt = createdAt;
    }

    // Các Getter/Setter giữ nguyên, chỉ lưu ý Getter Role
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    // Getter/Setter cho các trường khác (id, username, password...)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}