package vn.edu.hcmuaf.fit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static String url = "jdbc:mysql://localhost:3306/dataweb?useUnicode=true&characterEncoding=UTF-8";
    private static String user = "root";
    private static String pass = "12345";
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
