package vn.edu.hcmuaf.fit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    // Use environment variables for Cloud deployment, fallback to localhost for
    // local dev
    private static String url = System.getenv("MYSQL_URL") != null ? System.getenv("MYSQL_URL")
            : "jdbc:mysql://localhost:3306/dataweb?useUnicode=true&characterEncoding=UTF-8";
    private static String user = System.getenv("MYSQL_USER") != null ? System.getenv("MYSQL_USER") : "root";
    private static String pass = System.getenv("MYSQL_PASSWORD") != null ? System.getenv("MYSQL_PASSWORD") : "12345";
    private static Connection connection;

    public static Connection get() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, pass);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection conn = get();
        if (conn != null) {
            System.out.println("Kết nối thành công!");
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}
