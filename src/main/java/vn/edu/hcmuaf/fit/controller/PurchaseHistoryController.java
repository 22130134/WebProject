package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.dao.OrderDAO;
import vn.edu.hcmuaf.fit.dao.ProductDAO;
import vn.edu.hcmuaf.fit.model.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "PurchaseHistoryController", value = "/purchase-history")
public class PurchaseHistoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");
        Customer customer = (Customer) session.getAttribute("customer");

        if (user == null || customer == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 2. Lấy danh sách Đơn hàng của khách
        OrderDAO orderDAO = OrderDAO.getInstance();
        List<Order> orders = orderDAO.getOrdersByCustomerId(customer.getCustomerID());

        // 3. Chuẩn bị Map để chứa dữ liệu chi tiết (để hiển thị bên JSP)
        // Map 1: Key = OrderID, Value = List<OrderItem> (Danh sách sản phẩm trong đơn đó)
        Map<Integer, List<OrderItem>> orderItemsMap = new HashMap<>();

        // Map 2: Key = ProductID, Value = Product (Để tra cứu Tên và Ảnh sản phẩm)
        Map<Integer, Product> productsMap = new HashMap<>();

        ProductDAO productDAO = ProductDAO.getInstance();

        // 4. Duyệt qua từng đơn hàng để lấy dữ liệu chi tiết
        for (Order order : orders) {
            // Lấy danh sách item của đơn hàng này từ DB
            List<OrderItem> items = orderDAO.getOrderItemsByOrderId(order.getOrderId());

            // Lưu vào Map theo OrderID
            orderItemsMap.put(order.getOrderId(), items);

            // Duyệt qua từng item để lấy thông tin Product (Tên, Ảnh)
            for (OrderItem item : items) {
                // Chỉ query DB nếu sản phẩm này chưa có trong Map (để tối ưu hiệu suất)
                if (!productsMap.containsKey(item.getProductId())) {
                    Product p = productDAO.getProductById(item.getProductId());
                    if (p != null) {
                        productsMap.put(item.getProductId(), p);
                    }
                }
            }
        }

        // 5. Gửi dữ liệu sang JSP
        request.setAttribute("orders", orders);           // Danh sách đơn hàng
        request.setAttribute("orderItemsMap", orderItemsMap); // Map chi tiết item
        request.setAttribute("productsMap", productsMap);     // Map thông tin sản phẩm

        // Chuyển hướng-
        request.getRequestDispatcher("/Customer/Purchase_history/purchase_history.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}