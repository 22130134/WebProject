package vn.edu.hcmuaf.fit.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.model.Cart;
import vn.edu.hcmuaf.fit.service.CartService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddToCartServlet", value = "/add-to-cart")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        String quantityParam = request.getParameter("quantity");

        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            int id = Integer.parseInt(idParam);
            int quantity = Integer.parseInt(quantityParam);

            // DUMMY USER ID for testing
            int dummyCustomerId = 1;

            HttpSession session = request.getSession();
            // We will now rely on DB as the source of truth, but keep session for display

            String action = request.getParameter("action");
            if (action == null) {
                action = "add";
            }

            // 1. Update DB
            if (action.equals("add")) {
                CartService.getInstance().addToCart(dummyCustomerId, id, quantity);
            } else if (action.equals("update")) {
                CartService.getInstance().updateCart(dummyCustomerId, id, quantity);
            } else if (action.equals("delete")) {
                CartService.getInstance().removeProduct(dummyCustomerId, id);
            }

            // 2. Refresh Session Cart from DB to ensure sync
            Cart cart = CartService.getInstance().getCart(dummyCustomerId);
            session.setAttribute("cart", cart);

            jsonResponse.put("status", "success");
            jsonResponse.put("totalQuantity", cart.getTotalQuantity());
            jsonResponse.put("totalPrice", cart.getTotalPrice());
            jsonResponse.put("cartItems", cart.getData().values());
            jsonResponse.put("message", "Cart updated successfully (DB persisted)");

        } catch (NumberFormatException e) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Invalid product ID or quantity");
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", e.getMessage());
        }

        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
}
