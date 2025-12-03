package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.db.DBConnect;
import vn.edu.hcmuaf.fit.model.Cart;
import vn.edu.hcmuaf.fit.model.CartItem;
import vn.edu.hcmuaf.fit.model.Order;

import java.sql.*;
import java.util.Map;

public class OrderDAO {
    private static OrderDAO instance;

    private OrderDAO() {
    }

    public static OrderDAO getInstance() {
        if (instance == null) {
            instance = new OrderDAO();
        }
        return instance;
    }

    public int createOrder(Order order, Cart cart) {
        Connection conn = DBConnect.get();
        if (conn == null)
            return -1;

        int orderId = -1;
        try {
            conn.setAutoCommit(false); // Start Transaction

            // 1. Insert Order
            String sqlOrder = "INSERT INTO orders (CustomerID, TotalAmount, Status, PaymentMethod, RecipientName, ShippingAddress) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, order.getCustomerId());
            psOrder.setDouble(2, order.getTotalAmount());
            psOrder.setString(3, order.getStatus());
            psOrder.setString(4, order.getPaymentMethod());
            psOrder.setString(5, order.getRecipientName());
            psOrder.setString(6, order.getShippingAddress());
            psOrder.executeUpdate();

            ResultSet rsOrder = psOrder.getGeneratedKeys();
            if (rsOrder.next()) {
                orderId = rsOrder.getInt(1);
            }

            // 2. Insert Order Items
            String sqlDetail = "INSERT INTO orderitems (OrderID, ProductID, Quantity, PriceAtOrder) VALUES (?, ?, ?, ?)";
            PreparedStatement psDetail = conn.prepareStatement(sqlDetail);

            for (Map.Entry<Integer, CartItem> entry : cart.getData().entrySet()) {
                CartItem item = entry.getValue();
                psDetail.setInt(1, orderId);
                psDetail.setInt(2, item.getProduct().getId());
                psDetail.setInt(3, item.getQuantity());
                psDetail.setDouble(4, item.getProduct().getPrice());
                psDetail.addBatch();
            }
            psDetail.executeBatch();

            conn.commit(); // Commit Transaction
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return -1;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orderId;
    }
}
