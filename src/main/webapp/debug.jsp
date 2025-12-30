<%@ page import="java.sql.*" %>
    <%@ page import="vn.edu.hcmuaf.fit.db.DBConnect" %>
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
            <html>

            <head>
                <title>Database Debugger</title>
            </head>

            <body>
                <h1>Database Connection Debugger</h1>

                <h3>Environment Variables:</h3>
                <ul>
                    <li>MYSQLHOST: <%= System.getenv("MYSQLHOST") %>
                    </li>
                    <li>MYSQLPORT: <%= System.getenv("MYSQLPORT") %>
                    </li>
                    <li>MYSQLDATABASE: <%= System.getenv("MYSQLDATABASE") %>
                    </li>
                    <li>MYSQLUSER: <%= System.getenv("MYSQLUSER") %>
                    </li>
                </ul>

                <h3>Connection Test:</h3>
                <% try { Connection conn=DBConnect.get(); if (conn !=null) { out.println("<p style='color:green'>
                    <strong>Connection Successful!</strong></p>");
                    out.println("Database Product: " + conn.getMetaData().getDatabaseProductName() + "<br />");
                    out.println("URL used: " + conn.getMetaData().getURL() + "<br />");

                    // Test Query
                    String sql = "SELECT count(*) FROM categories";
                    try (PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                    out.println("<p>Table 'categories' found. Row count: " + rs.getInt(1) + "</p>");
                    }
                    } catch (Exception e) {
                    out.println("<p style='color:red'>Query 'categories' failed: " + e.getMessage() + "</p>");

                    // Try uppercase
                    out.println("<p>Trying 'Categories'...</p>");
                    try (PreparedStatement ps2 = conn.prepareStatement("SELECT count(*) FROM Categories");
                    ResultSet rs2 = ps2.executeQuery()) {
                    if (rs2.next()) {
                    out.println("<p style='color:green'>Table 'Categories' (Uppercase) found! Row count: " +
                        rs2.getInt(1) + "</p>");
                    }
                    } catch (Exception ex2) {
                    out.println("<p style='color:red'>Query 'Categories' also failed: " + ex2.getMessage() + "</p>");
                    }
                    }
                    } else {
                    out.println("<p style='color:red'><strong>Connection Object is NULL. Check server logs for stack
                            trace.</strong></p>");
                    }
                    } catch (Exception e) {
                    out.println("<p style='color:red'>Exception: " + e.getMessage() + "</p>");
                    e.printStackTrace(new java.io.PrintWriter(out));
                    }
                    %>
            </body>

            </html>