package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.db.DBConnect;
import vn.edu.hcmuaf.fit.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static ProductService instance;

    private ProductService() {
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    public List<Product> getAllProducts() {
        return getProducts(null, null, null);
    }

    public List<Product> getProducts(String[] brands, String priceRange, String sort) {
        List<Product> list = new ArrayList<>();
        Connection conn = DBConnect.get();
        if (conn == null)
            return list;

        StringBuilder sql = new StringBuilder("SELECT p.ProductID, p.ProductName, p.Brand, p.ImageURL, " +
                "p.Rating, p.ReviewCount, p.Badge, p.IsInstallment, p.SoldQuantity, " +
                "d.Price, d.OldPrice " +
                "FROM products p " +
                "JOIN productdetails d ON p.ProductID = d.ProductID " +
                "WHERE 1=1 ");

        // Filter by Brand
        if (brands != null && brands.length > 0) {
            sql.append("AND p.Brand IN (");
            for (int i = 0; i < brands.length; i++) {
                sql.append(i == 0 ? "?" : ", ?");
            }
            sql.append(") ");
        }

        // Filter by Price
        if (priceRange != null) {
            switch (priceRange) {
                case "p1": // < 2tr
                    sql.append("AND d.Price < 2000000 ");
                    break;
                case "p2": // 2tr - 4tr
                    sql.append("AND d.Price >= 2000000 AND d.Price < 4000000 ");
                    break;
                case "p3": // 4tr - 6tr
                    sql.append("AND d.Price >= 4000000 AND d.Price < 6000000 ");
                    break;
                case "p4": // > 6tr
                    sql.append("AND d.Price >= 6000000 ");
                    break;
            }
        }

        // Sort
        if (sort != null) {
            switch (sort) {
                case "priceAsc":
                    sql.append("ORDER BY d.Price ASC ");
                    break;
                case "priceDesc":
                    sql.append("ORDER BY d.Price DESC ");
                    break;
                case "newest":
                    sql.append("ORDER BY p.CreatedAt DESC ");
                    break;
                case "best":
                    sql.append("ORDER BY p.SoldQuantity DESC ");
                    break;
                default:
                    sql.append("ORDER BY p.ProductID DESC ");
            }
        } else {
            sql.append("ORDER BY p.ProductID DESC ");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int index = 1;
            if (brands != null && brands.length > 0) {
                for (String brand : brands) {
                    ps.setString(index++, brand);
                }
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("ProductID"));
                p.setName(rs.getString("ProductName"));
                p.setBrand(rs.getString("Brand"));
                p.setImg(rs.getString("ImageURL"));
                p.setRating(rs.getDouble("Rating"));
                p.setReviews(rs.getInt("ReviewCount"));
                p.setBadge(rs.getString("Badge"));
                p.setInstallment(rs.getBoolean("IsInstallment"));
                p.setSold(rs.getInt("SoldQuantity"));
                p.setPrice(rs.getDouble("Price"));
                p.setOldPrice(rs.getDouble("OldPrice"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Product getProductById(int id) {
        Product p = null;
        Connection conn = DBConnect.get();
        if (conn == null)
            return null;

        String sql = "SELECT p.ProductID, p.ProductName, p.Brand, p.ImageURL, " +
                "p.Rating, p.ReviewCount, p.Badge, p.IsInstallment, p.SoldQuantity, " +
                "d.Price, d.OldPrice, d.DetailDescription, d.StockQuantity " +
                "FROM products p " +
                "JOIN productdetails d ON p.ProductID = d.ProductID " +
                "WHERE p.ProductID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new Product();
                p.setId(rs.getInt("ProductID"));
                p.setName(rs.getString("ProductName"));
                p.setBrand(rs.getString("Brand"));
                p.setImg(rs.getString("ImageURL"));
                p.setRating(rs.getDouble("Rating"));
                p.setReviews(rs.getInt("ReviewCount"));
                p.setBadge(rs.getString("Badge"));
                p.setInstallment(rs.getBoolean("IsInstallment"));
                p.setSold(rs.getInt("SoldQuantity"));
                p.setPrice(rs.getDouble("Price"));
                p.setOldPrice(rs.getDouble("OldPrice"));
                p.setDescription(rs.getString("DetailDescription"));
                p.setStock(rs.getInt("StockQuantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public List<Product> getRelatedProducts(int limit) {
        List<Product> list = new ArrayList<>();
        Connection conn = DBConnect.get();
        if (conn == null)
            return list;

        // Fetch random products or just top products
        String sql = "SELECT p.ProductID, p.ProductName, p.Brand, p.ImageURL, " +
                "p.Rating, p.ReviewCount, p.Badge, p.IsInstallment, p.SoldQuantity, " +
                "d.Price, d.OldPrice " +
                "FROM products p " +
                "JOIN productdetails d ON p.ProductID = d.ProductID " +
                "ORDER BY RAND() LIMIT ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("ProductID"));
                p.setName(rs.getString("ProductName"));
                p.setBrand(rs.getString("Brand"));
                p.setImg(rs.getString("ImageURL"));
                p.setRating(rs.getDouble("Rating"));
                p.setReviews(rs.getInt("ReviewCount"));
                p.setBadge(rs.getString("Badge"));
                p.setInstallment(rs.getBoolean("IsInstallment"));
                p.setSold(rs.getInt("SoldQuantity"));
                p.setPrice(rs.getDouble("Price"));
                p.setOldPrice(rs.getDouble("OldPrice"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
