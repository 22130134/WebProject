package vn.edu.hcmuaf.fit.model;

import java.sql.Timestamp;

public class Account {
    private int id;
    private String username;
    private String password;
    private String email;
    private int role;   // 0 is user, 1 is admin
    private int status; // 0 is blocked, 1 is active
    private Timestamp createdAt;

    public Account() {
    }

    public Account(int id, String username, String password, String email, int role, int status, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    // --- Getter và Setter (Bạn có thể tự generate trong IntelliJ: Alt + Insert) ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getRole() { return role; }
    public void setRole(int role) { this.role = role; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}