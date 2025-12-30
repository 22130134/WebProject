package vn.edu.hcmuaf.fit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static String url;
    private static String user;
    private static String pass;
    private static Connection connection;

    static {
        // Check if we are in Cloud environment (Railway)
        if (System.getenv("MYSQLHOST") != null) {
            String host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQLPORT");
            String dbName = System.getenv("MYSQLDATABASE");
            String username = System.getenv("MYSQLUSER");
            String password = System.getenv("MYSQLPASSWORD");

            // Build JDBC URL cleanly for Cloud
            url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useUnicode=true&characterEncoding=UTF-8";
            user = username;
            pass = password;
        } else {
            // Local environment defaults (Chạy khi ở máy của bạn)
            url = "jdbc:mysql://localhost:3306/dataweb?useUnicode=true&characterEncoding=UTF-8";
            user = "root";
            pass = "12345";
        }
    }

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
