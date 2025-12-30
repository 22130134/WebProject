package vn.edu.hcmuaf.fit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    // Use environment variables for Cloud deployment (Railway), fallback to
    // localhost
    private static String url = System.getenv("MYSQL_URL") != null
            ? System.getenv("MYSQL_URL").replace("mysql://", "jdbc:mysql://")
            : "jdbc:mysql://localhost:3306/dataweb?useUnicode=true&characterEncoding=UTF-8";
    private static String user = System.getenv("MYSQLUSER") != null ? System.getenv("MYSQLUSER") : "root";
    private static String pass = System.getenv("MYSQLPASSWORD") != null ? System.getenv("MYSQLPASSWORD") : "12345";
    private static Connection connection;

    public static Connection get() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, pass);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Try to reconnect if failed? No, just let it be for now but print error
            // clearly
            System.err.println("Database Connection Failed!");
            System.err.println("URL: " + url);
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
