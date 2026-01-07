package vn.edu.hcmuaf.fit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static String url;
    private static String user;
    private static String pass;

    static {
        // Check if we are in Cloud environment (Railway)
        // NOTE: If connection fails on Cloud, try DELETING the MYSQLHOST variable in Railway to force using the 'else' block below with Public URL.
        if (System.getenv("MYSQLHOST") != null) {
            String host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQLPORT");
            String dbName = System.getenv("MYSQLDATABASE");
            String username = System.getenv("MYSQLUSER");
            String password = System.getenv("MYSQLPASSWORD");
            
            // Build JDBC URL cleanly for Cloud (Internal Network)
            url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useUnicode=true&characterEncoding=UTF-8";
            user = username;
            pass = password;
            System.out.println("LOG: Loaded Cloud Database Config (Railway Internal)");
        } else {
            // Local environment defaults -> NOW POINTED TO RAILWAY PUBLIC URL FOR BACKUP/TESTING
            // Old Localhost: url = "jdbc:mysql://localhost:3306/dataweb?useUnicode=true&characterEncoding=UTF-8";
            
            // Hardcoded Public Railway Connection
            url = "jdbc:mysql://gondola.proxy.rlwy.net:18468/railway?useUnicode=true&characterEncoding=UTF-8";
            user = "root";
            pass = "StlRplvUDCIGsWPYsboqynpgqlnGCyiH";
            System.out.println("LOG: Loaded Hardcoded Cloud Config (Railway Public)");
        }
    }

    public static Connection get() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Always create a new connection to ensure validity
            return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("LOG: Database Connection Failed!");
            System.err.println("LOG: URL used: " + url);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = get();
        if (conn != null) {
            System.out.println("Kết nối thành công!");
            try { conn.close(); } catch (SQLException e) {}
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}
