package vn.edu.hcmuaf.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.model.Cart;
import vn.edu.hcmuaf.fit.service.CartService;
import vn.edu.hcmuaf.fit.service.OrderService;

import java.io.IOException;

@WebServlet(name = "CheckoutServlet", value = "/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // DUMMY USER ID
        int dummyCustomerId = 1;
        Cart cart = CartService.getInstance().getCart(dummyCustomerId);
        if (cart == null || cart.getData().isEmpty()) {
            response.sendRedirect("cart.jsp"); // Redirect to cart if empty
            return;
        }
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/Checkout/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String recipientName = request.getParameter("recipientName");
        String shippingAddress = request.getParameter("shippingAddress");
        String paymentMethod = request.getParameter("paymentMethod");

        // DUMMY USER ID
        int dummyCustomerId = 1;

        boolean success = OrderService.getInstance().placeOrder(dummyCustomerId, recipientName, shippingAddress,
                paymentMethod);

        if (success) {
            // Update session cart
            HttpSession session = request.getSession();
            session.removeAttribute("cart"); // Clear session cart
            response.sendRedirect("Checkout/order_success.jsp");
        } else {
            request.setAttribute("error", "Đặt hàng thất bại. Vui lòng thử lại.");
            doGet(request, response);
        }
    }
}
